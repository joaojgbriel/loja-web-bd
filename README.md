# Loja Web — projeto inicial

Projeto-base da atividade prática de Programação para Web I.

## Objetivo

Implementar uma pequena loja Web usando Java EE 8, Servlets, HttpSession, JSP, EL/JSTL e endpoints orientados a recursos.

## Importante

Os arquivos Java fornecidos são apenas esqueletos e não possuem a implementação da atividade.
As páginas JSP possuem conteúdo estático propositalmente. O aluno deverá substituir os dados fixos por dados enviados pelos Servlets e acessados com Expression Language/JSTL.

## Endpoints esperados

- GET `/produtos`
- GET `/carrinho`
- POST `/carrinho/itens`
- DELETE `/carrinho/itens?id={id}`
- DELETE `/carrinho`

## Restrições

- O carrinho deve ser mantido em `HttpSession`.
- O atributo da sessão deve se chamar `carrinho`.
- Não usar Scriptlets nas JSPs.
- Não usar variável `static` para armazenar carrinhos.
- Identificadores específicos devem ser enviados por query string quando aplicável.

## Respostas dos testes obrigatórios

**Atualizar a página / ir para Produtos e voltar:** os itens continuam porque o carrinho está guardado
na `HttpSession` (no servidor). O navegador envia o cookie `JSESSIONID` em toda requisição, e o servidor
usa esse id para encontrar a mesma sessão e o mesmo atributo `carrinho`.

**Janela anônima:** a janela anônima não compartilha cookies com a janela normal. Ela não envia o
`JSESSIONID` da outra janela, então o servidor cria (ou não encontra) uma sessão diferente, e por isso
o carrinho aparece vazio/diferente.

**Outro navegador:** cada navegador guarda seus próprios cookies. Cada um recebe um `JSESSIONID`
diferente, logo cada um tem a sua `HttpSession` e o seu próprio atributo `carrinho`. Como nenhum
carrinho fica em variável `static`, um usuário nunca enxerga o carrinho do outro.

**Remover com DELETE e query string:** o botão "Remover" faz `fetch('/carrinho/itens?id=3', {method:'DELETE'})`.
O Servlet lê o id com `request.getParameter("id")` e remove a entrada do mapa. Resposta: `204 No Content`.

**Limpar:** `DELETE /carrinho` executa `session.removeAttribute("carrinho")`. Só esse atributo é apagado;
a sessão continua viva (não usamos `session.invalidate()`), e qualquer outro atributo dela permanece.

---

# Atividade 2 — Login com HttpSession

## Endpoints novos

- GET `/login` → apresenta o formulário (forward para `/WEB-INF/views/login.jsp`)
- POST `/login` → processa e-mail e senha
- GET `/logout` → encerra a sessão

## Usuários de teste

| E-mail | Senha | Nome |
|---|---|---|
| carlos@email.com | 123456 | Carlos Lima |
| ana@email.com | 123456 | Ana Beatriz Santos |

## Como funciona

**GET /login:** o `LoginServlet` faz *forward* para `login.jsp`. A JSP fica dentro de `WEB-INF`,
então não pode ser acessada diretamente por `/login.jsp`, só através do Servlet.

**POST /login com dados inválidos:** o Servlet coloca o atributo `erro` no escopo de **requisição**
e faz *forward* de volta para `login.jsp`. Como é a mesma requisição, a JSP lê `${erro}` e exibe a
mensagem com `<c:if test="${not empty erro}">`. Sem erro, a mensagem não aparece.

**POST /login com dados válidos:** o Servlet guarda o objeto `Usuario` na **sessão**, no atributo
`usuarioLogado`, e faz *redirect* para `/` (padrão Post/Redirect/Get). O usuário precisa ficar na
sessão, e não no request, porque o request acaba ao fim de cada resposta, enquanto a sessão é
reencontrada em toda requisição pelo cookie `JSESSIONID`.

**Página principal:** o nome é exibido com `${sessionScope.usuarioLogado.nome}` (EL, sem Scriptlets).
A mesma área aparece em Produtos e Carrinho, mostrando que o usuário continua identificado durante a navegação.

**Logout:** o `LogoutServlet` recupera a sessão com `getSession(false)`, chama `invalidate()` e
redireciona para `/`. Diferente do "Limpar carrinho" (que usa `removeAttribute` e mantém a sessão),
o logout destrói a sessão inteira: somem o `usuarioLogado` e o carrinho.

---

# Atividade 3 — Loja Web com banco de dados

Os produtos e os usuários deixaram de ficar em listas na memória e passaram a ser lidos de um
**banco de dados** com **JDBC**. Os endpoints e as páginas continuam os mesmos.

## O que mudou

| Antes (memória) | Agora (banco de dados) |
|---|---|
| `ProdutoMemoriaRepository` (lista em Java) | `ProdutoDAO` (tabela `produto`) |
| `UsuarioMemoriaRepository` (lista em Java) | `UsuarioDAO` (tabela `usuario`) |
| — | `ConnectionFactory` (abre as conexões, lê `db.properties`) |
| — | `BancoDadosListener` (cria as tabelas e os dados iniciais ao iniciar) |

O **carrinho continua na `HttpSession`** e o **usuário logado continua na sessão** (`usuarioLogado`):
o banco guarda os dados permanentes da loja; a sessão guarda o que é de cada visitante.

## Banco de dados

- Usamos o **H2** em modo arquivo: ele roda dentro da aplicação, sem instalar nada.
- O arquivo do banco é criado em `~/lojaweb-db/` (pasta do usuário) na primeira execução.
- `src/main/resources/schema.sql` cria as tabelas; `data.sql` insere os produtos e usuários iniciais
  (só quando as tabelas estão vazias).
- Para começar com o banco do zero, apague a pasta `~/lojaweb-db/`.
- Para trocar por MySQL, basta editar `src/main/resources/db.properties` (instruções no próprio arquivo).

Todas as consultas usam `PreparedStatement` com `?`, o que evita **SQL Injection**, e
*try-with-resources*, que fecha conexão, statement e resultado automaticamente.

## Tabelas

```
usuario(id, nome, email, senha)
produto(id, nome, descricao, preco, icone)
```

## Usuários de teste

| E-mail | Senha |
|---|---|
| carlos@email.com | 123456 |
| ana@email.com | 123456 |

## Como rodar

Precisa de Java 17+ e Maven:

```
mvn jetty:run
```

Acesse http://localhost:8080/

## GitHub Codespaces

O projeto tem a pasta `.devcontainer`, então abre pronto no Codespaces (Java 17 + Maven já instalados).

1. Crie um repositório **público** no GitHub e envie os arquivos do projeto.
2. No repositório: **Code → Codespaces → Create codespace on main**.
3. No terminal do Codespace: `mvn jetty:run`
4. Na aba **PORTS**, clique com o botão direito na porta **8080** → **Port Visibility → Public**.
5. Copie o endereço da porta 8080 (termina em `.app.github.dev`) para compartilhar.
