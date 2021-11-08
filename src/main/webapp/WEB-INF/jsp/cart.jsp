<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:base_layout>
    <jsp:attribute name="title">
        Panier
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>
    <div class="layout_padding gallery_section">
        <div class="container">
            <div class="collection_text">Panier</div>
            <h2>Panier</h2>

            <c:forEach varStatus="idx" items="${articles}" var="article">
                <div class="row">
                    Article: ${article.article.name}
                    , ${article.article.description}
                    , ${article.article.price}
                    , Quantity : ${article.quantity}
                </div>

            </c:forEach>

            
        </div>
    </div>

    </jsp:body>
</t:base_layout>
