<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produtos - Loja Web</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body>
<header class="topo">
    <div class="container topo-conteudo">
        <div><h1>Loja Web</h1></div>
        <nav class="menu">
            <a href="${ctx}/index.jsp">Início</a>
            <a class="ativo" href="${ctx}/produtos">Produtos</a>
            <a href="${ctx}/carrinho">Carrinho <span class="contador">${quantidadeTotal}</span></a>
        </nav>

        <%-- Área do usuário: lida do usuarioLogado na HttpSession. --%>
        <div class="usuario">
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <span class="usuario-nome">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/>!</span>
                    <a class="btn btn-secundario btn-pequeno" href="${ctx}/logout">Sair</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primario btn-pequeno" href="${ctx}/login">Entrar</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<main class="container secao">
    <div class="titulo-pagina">
        <div>
            <h2>Produtos</h2>
            <p>Escolha os equipamentos e adicione ao seu carrinho.</p>
        </div>
    </div>

    <div class="grade-produtos">
        <c:forEach var="produto" items="${produtos}">
            <article class="produto-card">
                <div class="produto-icone">${produto.icone}</div>
                <h3><c:out value="${produto.nome}"/></h3>
                <p class="produto-descricao"><c:out value="${produto.descricao}"/></p>
                <strong class="preco"><fmt:formatNumber value="${produto.preco}" type="currency"/></strong>
                <form action="${ctx}/carrinho/itens" method="post">
                    <input type="hidden" name="id" value="${produto.id}">
                    <button class="btn btn-primario btn-bloco" type="submit">Adicionar ao carrinho</button>
                </form>
            </article>
        </c:forEach>
    </div>
</main>

<footer><div class="container">Programação para Web I</div></footer>
</body>
</html>
