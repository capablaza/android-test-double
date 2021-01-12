package com.example.unittesting.payment;

public class PaymentResponse {

    public static int OK = 200;
    public static int ERROR = 500;


    private int code;

    public PaymentResponse(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
