# Test Doubles en Android

## Contexto
En el siguiente ejemplo veremos como utilizar los diferentes objetos disponibles para crear nuestras 
pruebas unitarias. Los tipos de objetos utilizados son: Mock, Stub, Spies, Dummy, Fake.

## El problema
Dado un cliente de pago se requiere implementar en aplicación la posibilidad de realizar las siguientes
operaciones:
```
    1.- Enviar un pago utilizando api para registrarlo de manera correcta.
    2.- En caso de error enviar correo electronico indicando el error.
    3.- En caso de error registrar en dispositivo el error para futuras consultas en vista de errores.
```
## Nota

Todas las implementaciones fueron realizadas sin utilizar alguna librería para estos fines, 
el objetivo es comprender como generar los objetos y luego poder utilizar alguna libreria a gusto.


## Entidades
```
CreditCard : representa la tarjeta de credito
Sale : representa una orden de venta
PaymentRequest : representa una solicitud de pago hacia api.
PaymentResponse : representa una respuesta de pago desde api.
EmailSender : representa el cliente de envio de email.
Storage : representa almacenamiento local en dispositivo.
```

## Licencia
[MIT](https://choosealicense.com/licenses/mit/)
