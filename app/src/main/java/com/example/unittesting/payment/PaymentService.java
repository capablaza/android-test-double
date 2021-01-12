package com.example.unittesting.payment;

public class PaymentService {
    private final Logger logger;
    private EmailSender emailSender;
    private Storage storage;

    public PaymentService(final Logger logger, final EmailSender emailSender, final Storage storage) {
        this.logger = logger;
        this.emailSender = emailSender;
        this.storage = storage;
    }

    public PaymentRequest createPaymentRequest(Sale sale, CreditCard creditCard) {
        logger.append("Creating payment for sale " + sale.toString());
        return new PaymentRequest(sale, creditCard);
    }

    public PaymentResponse sendPayment(PaymentRequest request, PaymentRestClient restClient){
        logger.append("Sending payment .... ");
        PaymentResponse paymentResponse = restClient.sendPayment(request);
        logger.append("Payment response : " + paymentResponse.code());

        if(paymentResponse.code() == PaymentResponse.ERROR ) {
            emailSender.sendEmail(request);
            storage.save(request);
        }

        return paymentResponse;
    }
}
