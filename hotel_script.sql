﻿DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE PESSOA (
	idPessoa SERIAL NOT NULL,
	nome VARCHAR(40) NOT NULL,
	email VARCHAR(50) NOT NULL,
	--tipo CHAR NOT NULL,
	PRIMARY KEY(idPessoa)
);

CREATE TABLE ENDERECO(
	idEndereco SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	rua VARCHAR(45) NOT NULL,
	bairro VARCHAR(45) NOT NULL,
	cep VARCHAR(20) NOT NULL,
	numero INTEGER NOT NULL,
	complemento VARCHAR(45),
	PRIMARY KEY(idEndereco)
);

CREATE TABLE FONE (
	idFone SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	numeroFone VARCHAR(20) NOT NULL,
	PRIMARY KEY(idFone)
);

CREATE TABLE FISICA (
	idFisica SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	cpf VARCHAR(20) NOT NULL UNIQUE,
	rg VARCHAR(20) NOT NULL,
	dataNascimento DATE NOT NULL,
	--tipoPF CHAR NOT NULL,
	PRIMARY KEY(idFisica)
);

CREATE TABLE CLIENTE (
	idCliente SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idFisica INTEGER NOT NULL REFERENCES Fisica,
	PRIMARY KEY(idCliente)
);

CREATE TABLE EMPREGADO (
	idEmpregado SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idFisica INTEGER NOT NULL REFERENCES Fisica,
	PRIMARY KEY(idEmpregado)
);

CREATE TABLE JURIDICA (
	idJuridica SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	razaoSocial VARCHAR(45) NOT NULL,
	cnpj VARCHAR(20) NOT NULL,
	PRIMARY KEY(idJuridica)
);

CREATE TABLE ADM (
	idAdm SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idJuridica INTEGER NOT NULL REFERENCES Juridica,
	PRIMARY KEY(idAdm)
);

CREATE TABLE QUARTO (
	idQuarto SERIAL NOT NULL,
	numero INTEGER NOT NULL UNIQUE,
	PRIMARY KEY(idQuarto)
);

CREATE TABLE SERVICO (
	idServico SERIAL NOT NULL,
	idQuarto INTEGER NOT NULL REFERENCES Quarto,
	idAdm INTEGER NOT NULL REFERENCES Adm,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idFisica INTEGER NOT NULL REFERENCES Fisica,
	idEmpregado INTEGER NOT NULL REFERENCES Empregado,
	descricao VARCHAR(200),
	PRIMARY KEY(idServico)
);


