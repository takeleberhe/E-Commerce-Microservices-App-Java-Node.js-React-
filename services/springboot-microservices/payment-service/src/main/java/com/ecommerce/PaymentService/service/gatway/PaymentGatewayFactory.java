package com.ecommerce.PaymentService.service.gatway;

import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Factory class to manage and provide instances of payment gateways.
 * This ensures a single point of access for all supported payment gateways
 * and abstracts the creation logic from other parts of the application.
 */
@Component
public class PaymentGatewayFactory {

    /**
     * Enum to define the supported payment gateways.
     * This makes it easier to manage and validate gateway names.
     */
    public enum Gateway {
        STRIPE, PAYPAL
    }

    // Map to hold available payment gateways, keyed by the Gateway enum.
    private final Map<Gateway, PaymentGateway> gateways;

    /**
     * Constructor to initialize the map of payment gateways.
     * The constructor uses dependency injection to receive the Stripe and PayPal gateway beans.
     *
     * @param stripeGateway Instance of StripePaymentGateway.
     * @param paypalGateway Instance of PayPalPaymentGateway.
     */
    public PaymentGatewayFactory(StripePaymentGateway stripeGateway, PayPalPaymentGateway paypalGateway) {
        this.gateways = Map.ofEntries(
                Map.entry(Gateway.STRIPE, stripeGateway), // Add Stripe gateway to the map
                Map.entry(Gateway.PAYPAL, paypalGateway)  // Add PayPal gateway to the map
        );
    }

    /**
     * Retrieves the appropriate PaymentGateway implementation based on the provided gateway name.
     *
     * @param gatewayName The name of the payment gateway (e.g., "stripe", "paypal").
     * @return The corresponding PaymentGateway implementation.
     * @throws IllegalArgumentException       If the gateway name is null or empty.
     * @throws UnsupportedGatewayException    If the gateway name is not supported.
     */
    public PaymentGateway getPaymentGateway(String gatewayName) {
        // Validate that the gateway name is not null or empty
        if (gatewayName == null || gatewayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Gateway name must not be null or empty.");
        }

        try {
            // Convert the input string to the Gateway enum (case-insensitive)
            Gateway gatewayEnum = Gateway.valueOf(gatewayName.toUpperCase());

            // Retrieve the corresponding PaymentGateway from the map
            return gateways.get(gatewayEnum);
        } catch (IllegalArgumentException e) {
            // Throw a custom exception if the gateway is not supported
            throw new UnsupportedGatewayException("Unsupported payment gateway: " + gatewayName);
        }
    }

    /**
     * Custom exception class to handle unsupported payment gateways.
     * This provides a clear error message when an invalid gateway is requested.
     */
    public static class UnsupportedGatewayException extends RuntimeException {
        public UnsupportedGatewayException(String message) {
            super(message);
        }
    }
}
