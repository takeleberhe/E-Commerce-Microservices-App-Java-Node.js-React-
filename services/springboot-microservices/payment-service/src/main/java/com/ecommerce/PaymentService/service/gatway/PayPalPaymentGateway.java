package com.ecommerce.PaymentService.service.gatway;

import com.ecommerce.PaymentService.Dto.PaymentRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayPalPaymentGateway implements PaymentGateway {

    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        // Logic for processing payment via PayPal
        return "Payment processed with PayPal: " + paymentRequest.getAmount();
    }

}