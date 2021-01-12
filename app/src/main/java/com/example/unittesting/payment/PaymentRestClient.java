package com.example.unittesting.payment;

public interface PaymentRestClient {

    PaymentResponse sendPayment(PaymentRequest request);
}
