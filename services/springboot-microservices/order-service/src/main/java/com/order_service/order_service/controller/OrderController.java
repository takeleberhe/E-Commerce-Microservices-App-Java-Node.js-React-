package com.order_service.order_service.controller;

import com.order_service.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for handling order-related operations.
 * Exposes endpoints for placing orders in the Order Service.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor // Lombok: generates constructor for final fields
@Slf4j // Lombok: for logging
public class OrderController {

    private final OrderService orderService;

    /**
     * Endpoint to place an order with a specific product ID and quantity.
     *
     * @param productId ID of the product to be ordered
     * @param quantity  Quantity of the product to be ordered
     * @return ResponseEntity with result message
     */
    @Operation(
            summary = "Place a new order",
            description = "Places an order for the given product ID and quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or order placement failed")
    })
    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(
            @Parameter(description = "ID of the product to be ordered", required = true)
            @RequestParam Long productId,

            @Parameter(description = "Quantity of the product to be ordered", required = true)
            @RequestParam int quantity) {

        log.info("Placing order for productId: {}, quantity: {}", productId, quantity);

        String responseMessage = orderService.placeOrder(productId, quantity).toString();

        return responseMessage.contains("successfully")
                ? new ResponseEntity<>(responseMessage, HttpStatus.CREATED)
                : new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
