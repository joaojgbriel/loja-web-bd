-- Estrutura do banco da Loja Web.
-- Executado toda vez que a aplicação inicia; "IF NOT EXISTS" evita erro se as tabelas já existirem.

CREATE TABLE IF NOT EXISTS usuario (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(100)  NOT NULL,
    descricao VARCHAR(255),
    preco     DECIMAL(10,2) NOT NULL,
    icone     VARCHAR(10)
);
