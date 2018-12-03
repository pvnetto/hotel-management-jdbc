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
	descricao VARCHAR(45) NOT NULL UNIQUE,
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

CREATE TABLE Conta (
	idConta SERIAL NOT NULL,
	valorTotal FLOAT NOT NULL,
	PRIMARY KEY(idConta)
);

CREATE TABLE Reserva (
	idReserva SERIAL NOT NULL,
	idCliente INTEGER NOT NULL REFERENCES Cliente,
	idQuarto INTEGER NOT NULL REFERENCES Quarto,
	idConta INTEGER NULL REFERENCES Conta,
	dataInicio DATE NOT NULL,
	dataFim DATE NOT NULL,
	PRIMARY KEY(idReserva)
);

CREATE TABLE Avaliacao (
	idAvaliacao SERIAL NOT NULL,
	idReserva INTEGER NOT NULL REFERENCES Reserva,
	nota INTEGER NOT NULL,
	comentario VARCHAR(255),
	dataAvaliacao DATE NOT NULL,
	PRIMARY KEY(idAvaliacao)
);

CREATE TABLE Consumo(
	idConsumo SERIAL NOT NULL,
	idConta INTEGER NOT NULL REFERENCES Conta,
	valor FLOAT NOT NULL,
	descricao VARCHAR(45) NOT NULL,
	PRIMARY KEY(idConsumo)
);

-- Criando índices
CREATE INDEX data_indice ON Reserva USING BTREE(dataInicio, dataFim);
CREATE INDEX cpf_indice ON Fisica USING HASH (cpf);


-- Criando views
CREATE VIEW vwServicosPendentes AS
SELECT s.descricao as Descrição, s.statusServico as Status, q.numero as NumeroQuarto
FROM Servico s, Quarto q
WHERE s.idQuarto = q.idQuarto and s.statusServico = 'PENDENTE';

CREATE VIEW vwAvaliacoesPositivas AS
SELECT p.nome as Nome, q.numero as NumeroQuarto, a.nota as Nota, a.comentario as Comentario
FROM Pessoa p, Cliente c, Quarto q, Avaliacao a, Reserva r
WHERE a.idReserva = r.idReserva and r.idCliente = c.idCliente and r.idQuarto = q.idQuarto and c.idPessoa = p.idPessoa and a.nota >= 4;


-- Implementando regras de negócio como Triggers

-- Checa se a reserva é válida
CREATE FUNCTION reserva_trigger() RETURNS trigger AS $reserva_trigger$
DECLARE
	duracao_reserva integer;
	valor_reserva float;
	conta_inserida_id integer;
BEGIN
	-- Checa se os atributos possuem valores válidos
	IF NEW.idCliente IS NULL THEN
		RAISE EXCEPTION 'A reserva deve ter um cliente.';
	END IF;
	IF NEW.idQuarto IS NULL THEN
      RAISE EXCEPTION 'A reserva deve ter um quarto.';
	END IF;
	IF NEW.dataInicio >= NEW.dataFim THEN
   		RAISE EXCEPTION 'A data de início da reserva deve ser menor que a de fim.';
	END IF;
	IF EXISTS (SELECT 1 FROM Reserva WHERE idQuarto = new.idQuarto AND (new.dataInicio BETWEEN dataInicio AND dataFim OR new.dataFim BETWEEN dataInicio AND dataFim)) THEN
	  	RAISE EXCEPTION 'Este quarto já possui uma reserva para esta data!';
	END IF;
	
	-- TODO Mover para After
	-- Gera uma conta para a reserva, com valor inicial igual ao valor_reserva * duracao_reserva.
	SELECT t.valorDiaria INTO valor_reserva FROM TipoQuarto t, Quarto q WHERE q.idQuarto = NEW.idQuarto AND t.idTipoQuarto = q.idTipoQuarto;
	duracao_reserva := (NEW.dataFim - NEW.dataInicio);
   	INSERT INTO Conta(valorTotal) VALUES (valor_reserva * duracao_reserva) RETURNING idConta INTO conta_inserida_id;
	NEW.idConta := conta_inserida_id;
RETURN NEW;
END;
$reserva_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER reserva_Insert BEFORE INSERT
ON Reserva
FOR EACH ROW EXECUTE
PROCEDURE reserva_trigger();
																		
																		
-- Gera uma Conta para a Reserva adicionada													
CREATE FUNCTION reserva_controle() RETURNS trigger AS $reserva_controle$
DECLARE
	duracao_reserva integer;
	valor_reserva float;
	conta_inserida_id integer;
BEGIN	
	-- Gera uma conta para a reserva, com valor inicial igual ao valor_reserva * duracao_reserva.
	SELECT t.valorDiaria INTO valor_reserva FROM TipoQuarto t, Quarto q WHERE q.idQuarto = NEW.idQuarto AND t.idTipoQuarto = q.idTipoQuarto;
	duracao_reserva := (NEW.dataFim - NEW.dataInicio);
   	INSERT INTO Conta(valorTotal) VALUES (valor_reserva * duracao_reserva) RETURNING idConta INTO conta_inserida_id;
	NEW.idConta := conta_inserida_id;
RETURN NULL;
END;
$reserva_controle$ LANGUAGE plpgsql;

CREATE TRIGGER reserva_adiciona_conta AFTER INSERT
ON Reserva
FOR EACH ROW EXECUTE 
PROCEDURE reserva_controle();
																		
																		
-- 	Checa se os atributos de Consumo possuem valores válidos																	
CREATE FUNCTION consumo_trigger() RETURNS trigger AS $consumo_trigger$
BEGIN
	IF NEW.idConta IS NULL THEN
		RAISE EXCEPTION 'O consumo deve estar associado a uma conta.';
	END IF;
	IF NEW.valor <= 0 THEN
      	RAISE EXCEPTION 'O consumo deve ter valor maior que 0.';
	END IF;
RETURN NEW;
END;
$consumo_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER consumo_Insert BEFORE INSERT
ON Consumo
FOR EACH ROW EXECUTE 
PROCEDURE consumo_trigger();


-- Atualiza o valor total da Conta associada ao Consumo
CREATE FUNCTION controle_consumo() RETURNS trigger AS $controle_consumo$
BEGIN
	IF (TG_OP = 'DELETE') THEN
		UPDATE Conta SET valorTotal = valorTotal - old.valor WHERE idConta = new.idConta;
	ELSIF (TG_OP = 'UPDATE') THEN
		UPDATE Conta SET valorTotal = valorTotal + (new.valor - old.valor) WHERE idConta = NEW.idConta;
	ELSIF (TG_OP = 'INSERT') THEN
		UPDATE Conta SET valorTotal = valorTotal + new.valor WHERE idConta = new.idConta;
	END IF;
	RETURN NULL;
END;
$controle_consumo$ LANGUAGE plpgsql;

CREATE TRIGGER consumo_adiciona_conta AFTER INSERT OR UPDATE OR DELETE
ON Consumo
FOR EACH ROW EXECUTE 
PROCEDURE controle_consumo();
																		
																		
-- 	Checa se os atributos de Avaliacao possuem valores válidos																	
CREATE FUNCTION avaliacao_trigger() RETURNS trigger AS $avaliacao_trigger$
DECLARE
	data_inicio_reserva date;
	data_fim_reserva date;
BEGIN
	IF NEW.idReserva IS NULL THEN
		RAISE EXCEPTION 'A avaliação deve estar associada a uma reserva.';
	END IF;
	IF new.nota < 0 OR new.nota > 5 THEN
		RAISE EXCEPTION 'A avaliação deve ter uma nota de 0 a 5.';
	END IF;														
	IF NOT EXISTS (SELECT 1 FROM Reserva WHERE idReserva = new.idReserva) THEN
		RAISE EXCEPTION 'Não existe uma Reserva com o ID %.', NEW.idReserva;
	END IF;
	IF EXISTS (SELECT 1 FROM Avaliacao WHERE idReserva = new.idReserva) THEN
		RAISE EXCEPTION 'Já existe uma Avaliação para essa Reserva.';
	END IF;
	
	-- Checando se a Avaliacao está sendo inserida após o fim da Reserva
	SELECT dataInicio FROM Reserva WHERE idReserva = NEW.idReserva INTO data_inicio_reserva;
	SELECT dataFim FROM Reserva WHERE idReserva = NEW.idReserva INTO data_fim_reserva;
	
	IF new.dataAvaliacao BETWEEN data_inicio_reserva AND data_fim_reserva THEN
		RAISE EXCEPTION 'A avaliação deve ser feita apenas após o fim da reserva.';
	END IF;
RETURN NEW;
END;
$avaliacao_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER avaliacao_Insert BEFORE INSERT
ON Avaliacao
FOR EACH ROW EXECUTE 
PROCEDURE avaliacao_trigger();


