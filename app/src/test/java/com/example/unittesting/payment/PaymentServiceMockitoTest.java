package com.example.unittesting.payment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceMockitoTest {

    private CreditCard creditCard;
    private Logger logger;
    private Storage storage;

    @Before
    public void setUp(){
        creditCard = new CreditCard("money_card", 123435543);
        logger = mock(Logger.class);
        storage = mock(Storage.class);
    }

    @Test
    public void givenSaleAndCreditCardWhenBothAreCorrectThenPaymentRequestIsFill() {
        //prepare
        EmailSender emailSender = mock(EmailSender.class);
        Sale sale = new Sale();
        PaymentService paymentService = new PaymentService(logger, emailSender, storage);

        //act
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        //asserts
        Assert.assertTrue(paymentRequest.isFill());
    }

    @Test
    public void givenPaymentRequestWhenIsValidThenResponseCodeIs200() {
        //prepare
        EmailSender emailSender = mock(EmailSender.class);
        Sale sale = new Sale();

        PaymentRestClient restClient = mock(PaymentRestClient.class);

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        when(restClient.sendPayment(paymentRequest)).thenReturn(new PaymentResponse(200));

        //act
        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        //asserts
        Assert.assertEquals(paymentResponse.code(), PaymentResponse.OK);
    }

    @Test
    public void givenPaymentRequestWhenResponseIsErrorThenSendEmail() {

        //prepare
        EmailSender emailSender = mock(EmailSender.class);
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender,storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = mock(PaymentRestClient.class);

        when(restClient.sendPayment(paymentRequest)).thenReturn(new PaymentResponse(500));

        //act
        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        //asserts
        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);

        verify(emailSender).sendEmail(paymentRequest);

    }

    @Test
    public void givenPaymentRequestWhenResponseIsErrorThenSendEmailOnce() {

        //prepare
        EmailSender emailSender = mock(EmailSender.class);
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = mock(PaymentRestClient.class);

        when(restClient.sendPayment(paymentRequest)).thenReturn(new PaymentResponse(500));

        //act
        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        //asserts
        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);
        //verify max invocation
        verify(emailSender, atMostOnce()).sendEmail(paymentRequest);
        //verify min invocation
        verify(emailSender, atLeastOnce()).sendEmail(paymentRequest);

    }

    @Test
    public void givenPaymentRequestWhenIsWrongThenStorageHaveRequestSaved() {
        //prepare
        EmailSender emailSender = mock(EmailSender.class);
        Storage storage = mock(Storage.class);
        Sale sale = new Sale();

        PaymentService paymentService = new PaymentService(logger, emailSender, storage);
        PaymentRequest paymentRequest = paymentService.createPaymentRequest(sale, creditCard);

        PaymentRestClient restClient = mock(PaymentRestClient.class);

        when(restClient.sendPayment(paymentRequest)).thenReturn(new PaymentResponse(500));

        when(storage.find(sale)).thenReturn(paymentRequest);

        //act
        PaymentResponse paymentResponse = paymentService.sendPayment(paymentRequest, restClient);

        //asserts
        Assert.assertEquals(paymentResponse.code(), PaymentResponse.ERROR);

        PaymentRequest requestFromStorage = storage.find(sale);
        verify(storage).save(paymentRequest);
        verify(storage).find(sale);
        Assert.assertEquals(requestFromStorage, paymentRequest);
    }


}
