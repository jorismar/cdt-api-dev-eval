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
  cvc varchar(255) NOT NULL,
  portador_cpf varchar(255) NOT NULL,
  senha varchar(255) NOT NULL,
  FOREIGN KEY(portador_cpf) REFERENCES portador(cpf)
);

CREATE TABLE lancamento (
  codigo bigint PRIMARY KEY NOT NULL,
  cartao_numero varchar(255) NOT NULL,
  data_lancamento date NOT NULL,
  estabelecimento varchar(255) NOT NULL,
  valor decimal NOT NULL,
  valor_cobranca decimal NOT NULL,
  FOREIGN KEY(cartao_numero) REFERENCES cartao(numero)
);

CREATE TABLE fatura (
  codigo_barras varchar(255) PRIMARY KEY NOT NULL,
  cartao_numero varchar(255) NOT NULL,
  vencimento date NOT NULL,
  valor decimal NOT NULL,
  periodo_inicio date NOT NULL,
  periodo_termino date NOT NULL,
  pagamento_data date,
  FOREIGN KEY(cartao_numero) REFERENCES cartao(numero)
);
