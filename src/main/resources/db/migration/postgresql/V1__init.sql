CREATE TABLE portador (
  cpf varchar(255) PRIMARY KEY NOT NULL,
  nome varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  data_nascimento DATE NOT NULL,
  renda decimal NOT NULL
);

CREATE TABLE cartao (
  numero varchar(255) PRIMARY KEY NOT NULL,
  nome_portador varchar(255) NOT NULL,
  validade date NOT NULL,
  cvc varchar(255) NOT NULL,
  senha varchar(255) NOT NULL,
  limite decimal NOT NULL,
  condicao varchar(255) NOT NULL,
  portador_cpf varchar(255) NOT NULL,
  FOREIGN KEY(portador_cpf) REFERENCES portador(cpf)
);

CREATE TABLE lancamento (
  identificador varchar(255) PRIMARY KEY NOT NULL,
  cartao_numero varchar(255) NOT NULL,
  beneficiario varchar(255) NOT NULL,
  valor decimal NOT NULL,
  data_lancamento date NOT NULL,
  FOREIGN KEY(cartao_numero) REFERENCES cartao(numero)
);

CREATE TABLE reembolso (
  identificador varchar(255) PRIMARY KEY NOT NULL,
  lancamento_identificador varchar(255) NOT NULL,
  data_reembolso date NOT NULL,
  FOREIGN KEY(lancamento_identificador) REFERENCES lancamento(identificador)
);

CREATE TABLE fatura (
  codigo_barras varchar(255) PRIMARY KEY NOT NULL,
  cartao_numero varchar(255) NOT NULL,
  valor decimal NOT NULL,
  vencimento date NOT NULL,
  condicao varchar(255) NOT NULL,
  data_pagamento date,
  FOREIGN KEY(cartao_numero) REFERENCES cartao(numero)
);
