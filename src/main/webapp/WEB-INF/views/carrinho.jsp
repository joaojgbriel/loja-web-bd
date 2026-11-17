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
    <title>Carrinho - Loja Web</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body>
<header class="topo">
    <div class="container topo-conteudo">
        <h1>Loja Web</h1>
        <nav class="menu">
            <a href="${ctx}/index.jsp">Início</a>
            <a href="${ctx}/produtos">Produtos</a>
            <a class="ativo" href="${ctx}/carrinho">Carrinho <span class="contador">${quantidadeTotal}</span></a>
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
            <h2>Meu carrinho</h2>
            <p>Os itens ficam guardados na sua sessão enquanto você navega.</p>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty itens}">
            <div class="aviso">
                Seu carrinho está vazio. <a href="${ctx}/produtos">Ver produtos →</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="carrinho-layout">
                <section class="carrinho-produtos">
                    <c:forEach var="item" items="${itens}">
                        <article class="item-carrinho">
                            <div class="item-icone">${item.produto.icone}</div>
                            <div class="item-info">
                                <h3><c:out value="${item.produto.nome}"/></h3>
                                <span><fmt:formatNumber value="${item.produto.preco}" type="currency"/></span>
                            </div>
                            <div class="quantidade"><span>Quantidade</span><strong>${item.quantidade}</strong></div>
                            <div class="subtotal"><span>Subtotal</span><strong><fmt:formatNumber value="${item.subtotal}" type="currency"/></strong></div>
                            <button class="btn-remover" type="button" data-id="${item.produto.id}">Remover</button>
                        </article>
                    </c:forEach>
                </section>

                <aside class="resumo">
                    <h3>Resumo do pedido</h3>
                    <div class="resumo-linha"><span>Itens</span><strong>${quantidadeTotal}</strong></div>
                    <div class="resumo-linha total"><span>Total</span><strong><fmt:formatNumber value="${valorTotal}" type="currency"/></strong></div>
                    <a class="btn btn-secundario btn-bloco" href="${ctx}/produtos">Continuar comprando</a>
                    <button id="btn-limpar" class="btn btn-perigo btn-bloco" type="button">Limpar carrinho</button>
                </aside>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<footer><div class="container">Programação para Web I</div></footer>

<script>
    // Formulário HTML só envia GET ou POST. Para DELETE usamos fetch().
    const ctx = '${ctx}';

    // DELETE /carrinho/itens?id={id}  -> remove um item
    document.querySelectorAll('.btn-remover').forEach(function (botao) {
        botao.addEventListener('click', function () {
            const id = botao.dataset.id;
            fetch(ctx + '/carrinho/itens?id=' + encodeURIComponent(id), { method: 'DELETE' })
                .then(function (resp) {
                    if (resp.ok) {
                        location.reload(); // recarrega com GET /carrinho
                    } else {
                        alert('Erro ao remover item (' + resp.status + ')');
                    }
                });
        });
    });

    // DELETE /carrinho  -> limpa o carrinho
    const btnLimpar = document.getElementById('btn-limpar');
    if (btnLimpar) {
        btnLimpar.addEventListener('click', function () {
            fetch(ctx + '/carrinho', { method: 'DELETE' })
                .then(function (resp) {
                    if (resp.ok) {
                        location.reload();
                    } else {
                        alert('Erro ao limpar carrinho (' + resp.status + ')');
                    }
                });
        });
    }
</script>
</body>
</html>
