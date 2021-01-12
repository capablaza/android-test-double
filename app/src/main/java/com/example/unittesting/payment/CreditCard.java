package com.example.unittesting.payment;

public class CreditCard {

    private final String name;
    private final int serialNumber;


    public CreditCard(final String name, final int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public int serialNumber(){ return this.serialNumber;}
}
