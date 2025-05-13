package com.ecommerce.PaymentService.service;

import com.ecommerce.PaymentService.Dto.PaymentRequest;
import com.ecommerce.PaymentService.service.gatway.PaymentGatewayFactory;
import com.ecommerce.PaymentService.exception.PaymentProcessingException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGatewayFactory paymentGatewayFactory;

    public PaymentService(PaymentGatewayFactory paymentGatewayFactory) {
        this.paymentGatewayFactory = paymentGatewayFactory;
    }

    /**
     * Processes a payment using the selected payment gateway.
     * @param gateway The name of the payment gateway (e.g., "stripe").
     * @param paymentRequest The payment request containing amount, currency, and description.
     * @return The client secret to confirm the payment on the front-end.
     * @throws PaymentProcessingException if the payment fails.
     */
    public String processPayment(String gateway, PaymentRequest paymentRequest) throws PaymentProcessingException {
        // Fetch the appropriate payment gateway from the factory
        var paymentGateway = paymentGatewayFactory.getPaymentGateway(gateway);

        if (paymentGateway == null) {
            throw new IllegalArgumentException("Unsupported payment gateway: " + gateway);
        }

        // Process the payment using the selected gateway stripe or other payment gateway
        return paymentGateway.processPayment(paymentRequest);
    }
}
