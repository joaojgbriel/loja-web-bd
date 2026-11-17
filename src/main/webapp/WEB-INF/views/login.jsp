<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Loja Web</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<header class="topo">
    <div class="container topo-conteudo">
        <div>
            <h1>Loja Web</h1>
            <span>Programação para Web I</span>
        </div>

        <nav class="menu">
            <a href="/">Início</a>
            <a href="produtos">Produtos</a>
            <a href="carrinho">Carrinho</a>
        </nav>
    </div>
</header>

<main class="login-container">
    <section class="login-card">
        <div class="login-cabecalho">
            <div class="login-icone">👤</div>
            <h2>Entre na sua conta</h2>
            <p>Informe seu e-mail e senha para continuar.</p>
        </div>

        <%-- Aparece somente quando o LoginServlet coloca "erro" no request (credenciais inválidas). --%>
        <c:if test="${not empty erro}">
            <div class="mensagem erro">
                <c:out value="${erro}"/>
            </div>
        </c:if>

        <form action="login" method="post">
            <div class="campo">
                <label for="email">E-mail</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="Digite seu e-mail"
                    autocomplete="username"
                    required>
            </div>

            <div class="campo">
                <label for="senha">Senha</label>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                    placeholder="Digite sua senha"
                    autocomplete="current-password"
                    required>
            </div>

            <button class="btn btn-primario btn-bloco" type="submit">
                Entrar
            </button>
        </form>

        <div class="login-ajuda">
            <p>Entre com sua conta para acessar os recursos da Loja Web.</p>
        </div>
    </section>
</main>

<footer>
    <div class="container">
        Programação para Web I
    </div>
</footer>

</body>
</html>
