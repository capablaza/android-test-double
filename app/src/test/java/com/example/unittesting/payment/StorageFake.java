package com.example.unittesting.payment;

import java.util.ArrayList;
import java.util.List;

public class StorageFake implements Storage{

    private List<PaymentRequest> paymentRequests = new ArrayList<>();

    @Override
    public void save(PaymentRequest request) {
        paymentRequests.add(request);
    }

    @Override
    public PaymentRequest find(Sale sale) {
        for(PaymentRequest request : paymentRequests){
            if(request.sale().equals(sale)) return request;
        }
        return new PaymentRequest(new Sale(), new CreditCard("", 0));
    }

    @Override
    public void remove(PaymentRequest request) {

    }
}
