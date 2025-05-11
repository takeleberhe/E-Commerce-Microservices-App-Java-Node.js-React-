package com.ecommerce.PaymentService.controller;

import com.ecommerce.PaymentService.Dto.PaymentRequest;
import com.ecommerce.PaymentService.exception.PaymentProcessingException;
import com.ecommerce.PaymentService.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Process a payment",
            description = "Processes a payment using the provided payment gateway and returns a client secret.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Payment details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "gateway": "stripe",
                                              "amount": 49.99,
                                              "currency": "USD",
                                              "description": "Payment for order #1234"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"clientSecret\": \"secret_abc123\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"Invalid input: gateway is required\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"Payment processing failed: insufficient funds\"}")))
    })
    @PostMapping
    public ResponseEntity<?> processPayment(
            @Valid @RequestBody PaymentRequest paymentRequest // Only Spring’s annotation here
    ) {
        try {
            String gateway = paymentRequest.getGateway();
            String clientSecret = paymentService.processPayment(gateway, paymentRequest);

            return ResponseEntity.ok(Map.of("clientSecret", clientSecret));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid input: " + ex.getMessage()));

        } catch (PaymentProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment processing failed: " + ex.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred: " + ex.getMessage()));
        }
    }
}
