package com.example.unittesting.payment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmailSenderMock implements EmailSender{

    private final List<PaymentRequest> paymentRequests = new ArrayList<>();
    private final List<PaymentRequest> expectedPaymentRequests = new ArrayList<>();


    @Override
    public void sendEmail(PaymentRequest paymentRequest) {
        this.paymentRequests.add(paymentRequest);
    }

    public void expected(PaymentRequest paymentRequest){
        this.expectedPaymentRequests.add(paymentRequest);
    }

    public void verify(){
        assertEquals(paymentRequests, expectedPaymentRequests);
    }
}
