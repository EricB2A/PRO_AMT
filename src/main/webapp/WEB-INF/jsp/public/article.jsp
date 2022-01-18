<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: - Article
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>
        <div class="gallery_section">
            <div class="container">
                <a class="text-white" style="font-size: 18px;" href="/catalog">< Revenir dans le catalogue</a>
                <div class="collection_text">${article.name}</div>
                <div class="about_main layout_padding">

                    <div class="collection_section_3">
                        <div class="container">
                                <div class="row">
                                    <div class="col-md-8">
                                        <c:if test="${not empty article.getPhotos()}">
                                            <div style="display:flex;" class="flex-row justify-content-center">
                                                <div style="margin-left: 40px;margin-right: 40px;" id="catalog-carousel" class="carousel slide" >
                                                    <div class="carousel-inner">
                                                        <c:forEach varStatus="idx" items="${article.getPhotos()}" var="photo">
                                                            <div class="carousel-item ${idx.first ? 'active' : ''}">
                                                                <img class="d-block w-100" src="/image/${photo.getPath()}">
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <a class="carousel-control-prev" href="#catalog-carousel" role="button" data-slide="prev">
                                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                        <span class="sr-only">Previous</span>
                                                    </a>
                                                    <a class="carousel-control-next" href="#catalog-carousel" role="button" data-slide="next">
                                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                        <span class="sr-only">Next</span>
                                                    </a>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${empty article.getPhotos()}">
                                            <img class="d-block w-100" src="${pageContext.request.contextPath}/images/placeholder-image.png">
                                        </c:if>
                                    </div>
                                    <div class="col-md-4 p-3">
                                        <h4 class="h4"><strong>${article.name}</strong></h4>
                                        <div class="text-white">${article.description}</div>
                                        <div class="h2 text-white"><strong>CHF <span style="color: #ffffee">${article.price}</span></strong></div>
                                            <c:if test="${article.quantity > 0 && article.price > 0}">
                                                <div>
                                                    <input class="form-control" type="number" id="articleQuantityToCart" name="articles to cart"  value="1" min="0">
                                                    <button class="btn btn-primary" onclick="addArticleToBasket('${article.id}', document.getElementById('articleQuantityToCart').value, '${_csrf.parameterName}', '${_csrf.token}', window.location.href)">Ajout au panier</button>
                                                </div>
                                            </c:if>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
