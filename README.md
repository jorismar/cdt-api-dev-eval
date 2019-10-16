# CDT-API: Developer Evaluation

[![Build Status](https://travis-ci.org/jorismar/cdt-api-dev-eval.svg?branch=dev)](https://travis-ci.org/jorismar/cdt-api-dev-eval)

This project is a simple API that simulates credit card transactions.

## Requirements

* Java 1.8
* Spring 2.2.0
* PostgreSQL
* RabbitMQ

## Testing

Run the Spring project using the command:

```
$ mvn spring-boot:run
```

Then, to execute the operations, send a POST requests, with the Header field Content-Type value as application/json, to the following endpoints:

**/cdt/api/register** - Register a new user (Portador):
```
{
	"cpf" : "12345678900",
	"nome" : "Jorismar Barbosa Meira",
	"email" : "jorismar.barbosa@gmail.com",
	"dataNascimento" : "2019-01-01",
	"senha" : "123456",
	"renda" : "4000.0"
}
```

**/cdt/api/request/card** - Request a new credit card (Cartao) to an existent user:
```
{
	"portadorCpf" : "12345678900",
	"senha" : "654321"
}
```

**/cdt/api/payment** - Make a payment (Lancamento) using a existent credit card (Cartao):
```
{
	"cartaoNumero" : "5124707172884078",
	"portadorNome" : "JORISMAR B MEIRA",
	"validade" : "2027-10-31",
	"cvc" : "561",
	"portadorCpf" : "12345678900",
	"beneficiario" : "TEST*123",
	"valor": 200.0
}
```

**/cdt/api/payment** - Cancel an existent payment (Lancamento):
```
{
	"cartaoNumero" : "5124707172884078",
	"portadorNome" : "JORISMAR B MEIRA",
	"validade" : "2027-10-31",
	"cvc" : "561",
	"portadorCpf" : "12345678900",
	"beneficiario" : "TEST*123",
	"identificador": "19f1f57890310ed8e7f19d00290c3b32",
	"valor": 535.66
}
```
**NOTE:** To cancel a payment you only need  to add the "identificador" field received on payment confirmation.

**NOTE:** The invoice (Fatura) generator executes every first day of the month and the data can be found on the database table.