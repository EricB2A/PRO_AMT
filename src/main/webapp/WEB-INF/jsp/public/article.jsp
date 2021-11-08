<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: ${article}
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>
        <div class="gallery_section">
            <div class="container">
                <a class="text-white" style="font-size: 18px;" href="/catalog">< Revenir dans le catalogue</a>
                <div class="collection_text">${carpet.get().name}</div>
                <div class="about_main layout_padding">

                    <div class="collection_section_3">
                        <div class="container">
                                <div class="row">
                                    <div class="col-md-8">
                                        <c:if test="${not empty carpet.get().getPhotos()}">
                                            <div style="display:flex;" class="flex-row justify-content-center">
                                                <div style="margin-left: 40px;margin-right: 40px;" id="catalog-carousel" class="carousel slide" >
                                                    <div class="carousel-inner">
                                                        <c:forEach varStatus="idx" items="${carpet.get().getPhotos()}" var="photo">
                                                            <div class="carousel-item ${idx.first ? 'active' : ''}">
                                                                <img class="d-block w-100" src="${photo.path}">
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
                                    </div>
                                    <div class="col-md-4 p-3">
                                        <h4 class="h4"><strong>${carpet.get().name}</strong></h4>
                                        <div class="text-white">${carpet.get().description}</div>
                                        <div class="h2 text-white"><strong>CHF <span style="color: #ffffee">${carpet.get().price}</span></strong></div>
                                        <button class="btn btn-primary" onclick="addArticleToBasket('${carpet.get().id}', '1', '${_csrf.parameterName}', '${_csrf.token}')">Ajout au panier</button>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
