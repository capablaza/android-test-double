package com.example.unittesting.payment;

public class PaymentRestClientStub implements PaymentRestClient{

    private int code;

    public PaymentRestClientStub(int code) {
        this.code = code;
    }

    @Override
    public PaymentResponse sendPayment(PaymentRequest request) {
        return new PaymentResponse(this.code);
    }
}
