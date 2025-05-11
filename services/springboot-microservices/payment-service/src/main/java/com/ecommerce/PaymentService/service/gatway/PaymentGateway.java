package com.ecommerce.PaymentService.service.gatway;

import com.ecommerce.PaymentService.Dto.PaymentRequest;
import com.ecommerce.PaymentService.exception.PaymentProcessingException;

public interface PaymentGateway {
    String processPayment(PaymentRequest paymentRequest)
            throws PaymentProcessingException;
}