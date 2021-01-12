package com.example.unittesting.payment;

public interface Storage {
    void save(PaymentRequest request);
    PaymentRequest find(Sale sale);
    void remove(PaymentRequest request);
}
