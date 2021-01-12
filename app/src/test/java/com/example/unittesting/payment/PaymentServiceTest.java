package com.example.unittesting.payment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentServiceTest {

    private CreditCard creditCard;
    private Logger logger;
    private Storage storage;

    @Before
    public void setUp() {
        creditCard = new CreditCard("money_card", 123435543);
        logger = new LoggerDummy();
        storage = new StorageDummy();
    }

    @Test
    public void givenSaleAndCreditCardWhenBothAreCorrectThenPaymentRequestIsFill() {
        EmailSender emailSender = new EmailSenderDummy();
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        Assert.assertTrue(paymentRequest.isFill());
    }

    @Test
    public void givenPaymentRequestWhenIsValidThenResponseCodeIs200() {
        EmailSender emailSender = new EmailSenderDummy();
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = new PaymentRestClientStub(200);

        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        Assert.assertEquals(paymentResponse.code(), PaymentResponse.OK);
    }

    @Test
    public void givenPaymentRequestWhenResponseIsErrorThenSendEmail() {
        EmailSenderMock emailSender = new EmailSenderMock();
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender,storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = new PaymentRestClientStub(500);

        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);
        emailSender.expected(paymentRequest);
        emailSender.verify();

    }

    @Test
    public void givenPaymentRequestWhenResponseIsErrorThenSendEmailOnce() {
        EmailSenderSpy emailSender = new EmailSenderSpy();
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = new PaymentRestClientStub(500);

        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);

        Assert.assertEquals(1, emailSender.timesCalled());

    }

    @Test
    public void givenPaymentRequestWhenIsWrongThenStorageHaveRequestSaved() {
        EmailSender emailSender = new EmailSenderDummy();
        Storage storage = new StorageFake();
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = new PaymentRestClientStub(500);

        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);
        PaymentRequest requestFromStorage = storage.find(sale);

        Assert.assertEquals(requestFromStorage, paymentRequest);
    }
}
