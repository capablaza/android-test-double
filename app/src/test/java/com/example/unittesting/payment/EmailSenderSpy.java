package com.example.unittesting.payment;

import java.util.ArrayList;
import java.util.List;

public class EmailSenderSpy implements EmailSender{

    private List<PaymentRequest> paymentRequests = new ArrayList<>();

    @Override
    public void sendEmail(PaymentRequest paymentRequest) {
        this.paymentRequests.add(paymentRequest);
    }

    public int timesCalled(){
        return this.paymentRequests.size();
    }
}
