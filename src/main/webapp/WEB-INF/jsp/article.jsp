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
    <div class="layout_padding gallery_section">
        <div class="container">
            <div class="collection_text">${article.get().name}</div>
            <div class="about_main layout_padding">
                <div class="collection_section">
                    <div class="container">
                        <h1 class="new_text"><strong>${article.get().name}</strong></h1>
                        <p class="consectetur_text">${article.get().description}</p>

                    </div>
                </div>
                <div class="collection_section_3 layout_padding">
                    <div class="container">
                        <div class="racing_shoes">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="shoes-img3"><img src="${article.get().photosImagePath}"></div>
                                </div>
                                <div class="col-md-4">
                                    <div class="number_text"><strong>CHF <span style="color: #0a0506">${article.get().price}</span></strong>
                                    </div>

                                    <input id="cartQuantity" type="number" value="1">
                                    <button class="seemore" onclick="addArticleToBasket('${article.get().id}', document.getElementById('cartQuantity').value, '${_csrf.parameterName}', '${_csrf.token}')">Ajout au papa</button>
                                  
                                        <button class="seemore_bt" onclick="deleteArticle('${_csrf.parameterName}', '${_csrf.token}')">Supprimer</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </jsp:body>
</t:base_layout>
