CREATE TABLE portador (
  cpf varchar(255) PRIMARY KEY NOT NULL,
  nome varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  data_nascimento DATE NOT NULL
);

CREATE TABLE cartao (
  numero varchar(255) PRIMARY KEY NOT NULL,
  nome_portador varchar(255) NOT NULL,
  validade date NOT NULL,
  cvc int NOT NULL,
  cpf varchar(255) NOT NULL,
  FOREIGN KEY(cpf) REFERENCES portador(cpf)
);

CREATE TABLE lancamento (
  codigo bigint PRIMARY KEY NOT NULL,
  numero_cartao varchar(255) NOT NULL,
  data_lancamento date NOT NULL,
  estabelecimento varchar(255) NOT NULL,
  valor decimal NOT NULL,
  FOREIGN KEY(numero_cartao) REFERENCES cartao(numero)
);
