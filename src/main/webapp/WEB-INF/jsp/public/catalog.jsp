<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: - Catalog
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>

        <div class="layout_padding gallery_section">
            <div class="container bg-transparent text-white">

                <div id="Catalogue" >
                    <h2 class="text-white">Articles</h2>

                    <c:if test="${empty carpets}">
                        <p>Aucun article disponible</p>
                    </c:if>

                    <c:if test="${not empty carpets}">
                        <div class="d-flex flex-wrap">
                           <c:forEach varStatus="idx" items="${carpets}" var="carpet">
                               <a href="/catalog/${carpet.getId()}">
                               <div class="card shadow-sm" style="width: 18rem;margin: 5px;">
                                   <c:if test="${not empty carpet.photos}">
                                       <img style="min-width: 286px;max-width: 286px;min-height: 225px;max-height: 225px;" class="card-img-top" src="${carpet.getFirstPhotoPath()}"/>
                                   </c:if>
                                   <div class="card-body">
                                       <h5 class="card-title">${carpet.name}</h5>
                                       <div class="card-text text-black-50">${carpet.price} CHF</div>
                                   </div>
                               </div>
                               </a>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_layout>
