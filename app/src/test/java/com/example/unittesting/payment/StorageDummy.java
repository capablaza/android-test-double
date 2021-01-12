package com.example.unittesting.payment;

public class StorageDummy implements Storage{
    @Override
    public void save(PaymentRequest request) {

    }

    @Override
    public PaymentRequest find(Sale sale) {
        return null;
    }

    @Override
    public void remove(PaymentRequest request) {

    }
}
