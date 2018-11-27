DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE Pessoa (
	idPessoa SERIAL NOT NULL,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	PRIMARY KEY(idPessoa)
);

CREATE TABLE Endereco (
	idEndereco SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	rua VARCHAR(45) NOT NULL,
	bairro VARCHAR(45) NOT NULL,
	cep VARCHAR(20) NOT NULL,
	numero INTEGER NOT NULL,
	complemento VARCHAR(45),
	PRIMARY KEY(idEndereco)
);

CREATE TABLE Fone (
	idFone SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	numeroFone VARCHAR(20) NOT NULL,
	PRIMARY KEY(idFone)
);

CREATE TABLE Fisica (
	idFisica SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	cpf VARCHAR(15) NOT NULL UNIQUE,
	rg VARCHAR(20) NOT NULL,
	dataNascimento DATE NOT NULL,
	PRIMARY KEY(idFisica)
);

CREATE TABLE Cliente (
	idCliente SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idFisica INTEGER NOT NULL REFERENCES Fisica,
	PRIMARY KEY(idCliente)
);

CREATE TABLE Funcionario (
	idFuncionario SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	idFisica INTEGER NOT NULL REFERENCES Fisica,
	PRIMARY KEY(idFuncionario)
);

CREATE TABLE Juridica (
	idJuridica SERIAL NOT NULL,
	idPessoa INTEGER NOT NULL REFERENCES Pessoa,
	razaoSocial VARCHAR(45) NOT NULL,
	cnpj VARCHAR(20) NOT NULL UNIQUE,
	PRIMARY KEY(idJuridica)
);

CREATE TABLE TipoQuarto (
	idTipoQuarto SERIAL NOT NULL,
	valorDiaria FLOAT NOT NULL,
	PRIMARY KEY(idTipoQuarto)
);

CREATE TABLE Quarto (
	idQuarto SERIAL NOT NULL,
	idTipoQuarto INTEGER NOT NULL REFERENCES TipoQuarto,
	numero INTEGER NOT NULL UNIQUE,
	PRIMARY KEY(idQuarto)
);

CREATE TABLE Servico (
	idServico SERIAL NOT NULL,
	idQuarto INTEGER NOT NULL REFERENCES Quarto,
	idFuncionario INTEGER NOT NULL REFERENCES Funcionario,
	descricao VARCHAR(200),
	statusServico VARCHAR(45) NOT NULL,
	PRIMARY KEY(idServico)
);

CREATE TABLE Avaliacao (
	idAvaliacao SERIAL NOT NULL,
	idQuarto INTEGER NOT NULL REFERENCES Quarto,
	idCliente INTEGER NOT NULL REFERENCES Cliente,
	nota INTEGER NOT NULL,
	comentario VARCHAR(255),
	PRIMARY KEY(idAvaliacao)
);

CREATE TABLE Reserva (
	idReserva SERIAL NOT NULL,
	idCliente INTEGER NOT NULL REFERENCES Cliente,
	idQuarto INTEGER NOT NULL REFERENCES Quarto,
	dataInicio DATE NOT NULL,
	dataFim DATE NOT NULL,
	PRIMARY KEY(idReserva)
);

CREATE TABLE Conta (
	idConta SERIAL NOT NULL,
	idReserva INTEGER NOT NULL REFERENCES Reserva,
	valorTotal FLOAT NOT NULL,
	PRIMARY KEY(idConta)
);

CREATE TABLE Consumo(
	idConsumo SERIAL NOT NULL,
	idConta INTEGER NOT NULL REFERENCES Conta,
	valor FLOAT NOT NULL,
	descricao VARCHAR(45) NOT NULL,
	PRIMARY KEY(idConsumo)
);

CREATE INDEX data_indice ON Reserva USING BTREE(dataInicio, dataFim);
CREATE INDEX cpf_indice ON Fisica USING HASH (cpf);


CREATE VIEW vwServicosPendentes AS
SELECT s.descricao as Descrição, s.statusServico as Status, q.numero as NumeroQuarto
FROM Servico s, Quarto q
WHERE s.idQuarto = q.idQuarto and s.statusServico = 'PENDENTE';

CREATE VIEW vwAvaliacoesPositivas AS
SELECT p.nome as Nome, q.numero as NumeroQuarto, a.nota as Nota, a.comentario as Comentario
FROM Pessoa p, Cliente c, Quarto q, Avaliacao a
WHERE a.idCliente = c.idCliente and a.idQuarto = q.idQuarto and c.idPessoa = p.idPessoa and a.nota >= 4;


