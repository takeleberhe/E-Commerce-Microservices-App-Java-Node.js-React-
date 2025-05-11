package com.ecommerce.PaymentService.service.gatway;

import com.ecommerce.PaymentService.Dto.PaymentRequest;
import com.ecommerce.PaymentService.exception.PaymentProcessingException;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentGateway implements PaymentGateway {

    private static final Logger logger = LoggerFactory.getLogger(StripePaymentGateway.class);

    private final String stripeSecretKey; // Stripe secret key to authenticate API requests

    // Constructor-based injection to initialize the secret key
    public StripePaymentGateway(@Value("${stripe.secret-key}") String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
        Stripe.apiKey = stripeSecretKey; // Set the Stripe API key for authentication
        logger.info("Stripe API key successfully initialized.");
    }

    /**
     * Processes the payment using the provided PaymentRequest object.
     * @param paymentRequest The payment request containing amount, currency, and description.
     * @return The client secret of the created PaymentIntent, which is used to confirm the payment on the front-end.
     */
    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        logger.info("Processing payment request: {}", paymentRequest);

        // Prepare the parameters for the payment intent
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue()); // Convert amount to cents
        params.put("currency", paymentRequest.getCurrency()); // Currency (e.g., USD)
        params.put("description", paymentRequest.getDescription()); // Description of the payment

        try {
            // Create the PaymentIntent using Stripe's API
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            logger.info("Payment processed successfully with Stripe: {}", paymentIntent.getId());

            // Return the client secret, which will be used to confirm the payment on the front-end
            return paymentIntent.getClientSecret();
        } catch (StripeException e) {
            logger.error("Stripe payment processing failed", e);
            throw new PaymentProcessingException("Failed to process payment with Stripe", e);
        }
    }
}
