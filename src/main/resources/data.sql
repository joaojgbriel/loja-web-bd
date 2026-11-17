-- Dados iniciais da Loja Web.
-- Só são inseridos quando as tabelas estão vazias (primeira execução).

INSERT INTO usuario (nome, email, senha) VALUES ('Carlos Lima', 'carlos@email.com', '123456');
INSERT INTO usuario (nome, email, senha) VALUES ('Ana Beatriz Santos', 'ana@email.com', '123456');

INSERT INTO produto (nome, descricao, preco, icone) VALUES ('Notebook', 'Notebook para trabalho e estudos.', 3500.00, '💻');
INSERT INTO produto (nome, descricao, preco, icone) VALUES ('Mouse', 'Mouse óptico USB.', 80.00, '🖱️');
INSERT INTO produto (nome, descricao, preco, icone) VALUES ('Teclado Mecânico', 'Teclado mecânico para produtividade.', 250.00, '⌨️');
INSERT INTO produto (nome, descricao, preco, icone) VALUES ('Monitor', 'Monitor para estação de trabalho.', 1200.00, '🖥️');
INSERT INTO produto (nome, descricao, preco, icone) VALUES ('Headset', 'Headset para aulas e reuniões.', 180.00, '🎧');
