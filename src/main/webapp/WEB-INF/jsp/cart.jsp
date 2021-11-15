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
                    <h2 class="text-white">Articles<c:if test="${not empty articles}">, total : ${cartPrice} CHF</c:if></h2>

                    <c:if test="${empty articles}">
                        <h3>Le panier est vide 🥲</h3>
                    </c:if>

                    <c:if test="${not empty articles}">

                        <div class="d-flex flex-wrap">
                            <c:forEach varStatus="idx" items="${articles}" var="element">
                                <div class="card shadow-sm" style="width: 22rem;margin: 5px;">
                                    <img style="min-width: 350px;max-width: 350px;min-height: 250px;max-height: 250px;" class="card-img-top" src="${element.carpet.firstPhotoPath}"/>
                                    <div class="card-body">
                                        <h5 class="card-title">${element.carpet.name}</h5>
                                        <h6 class="card-title">${element.carpet.description}</h6>
                                        <div class="card-text text-black-50">${element.carpet.price} CHF</div>

                                        <input type="number" id="productQuantity_${element.carpet.id}" value="${element.quantity}" min="0">
                                        <button class="btn btn-success" onclick="updateArticleToBasket('${element.carpet.id}', document.getElementById('productQuantity_${element.carpet.id}').value ,'${_csrf.parameterName}', '${_csrf.token}', window.location.origin + '/cart')">Changer</button>

                                        <button class="btn btn-danger" onclick="removeArticleFromBasket('${element.carpet.id}', '${_csrf.parameterName}', '${_csrf.token}', window.location.origin + '/cart')">Retirer</button>

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
