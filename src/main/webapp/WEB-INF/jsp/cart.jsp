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
            <div class="container bg-transparent text-white">

                <div id="Catalogue" >
                    <h2 class="text-white">Articles</h2>

                    <c:if test="${empty articles}">
                        <p>Aucun article disponible</p>
                    </c:if>

                    <c:if test="${not empty articles}">

                        <div class="d-flex flex-wrap">
                            <c:forEach varStatus="idx" items="${articles}" var="element">
                                <div class="card shadow-sm" style="width: 22rem;margin: 5px;">
                                    <img style="min-width: 350px;max-width: 350px;min-height: 250px;max-height: 250px;" class="card-img-top" src="${element.article.firstPhotoPath}"/>
                                    <div class="card-body">
                                        <h5 class="card-title">${element.article.name}</h5>
                                        <h6 class="card-title">${element.article.description}</h6>
                                        <div class="card-text text-black-50">${element.article.price} CHF</div>

                                        <input type="number" value="${element.quantity}" min="0">

                                        <button class="btn btn-danger" onclick="removeArticleFromBasket('${element.article.id}', '${_csrf.parameterName}', '${_csrf.token}', window.location.origin + '/cart')">Retirer du panier</button>

                                        <!--<button class="btn btn-primary" onclick="addArticleToBasket('${article_id}', '1', '${_csrf.parameterName}', '${_csrf.token}', '')">Ajout au panier</button>-->

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
