package com.example.unittesting.payment;

public class PaymentRequest {

    private Sale sale;
    private CreditCard creditCard;

    public PaymentRequest(Sale sale, CreditCard creditCard) {
        this.sale = sale;
        this.creditCard = creditCard;
    }

    public Sale sale(){ return this.sale;}

    public boolean isFill(){
        return (this.sale != null && this.creditCard != null);
    }

}
