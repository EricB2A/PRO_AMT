<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@attribute name="article_id" required="true" %>
<%@attribute name="nb_stars" required="false" %>
<%@attribute name="article_name" required="true" %>
<%@attribute name="article_img_path" required="true" %>
<%@attribute name="article_price" required="true" %>
<%@attribute name="article_nb_stars" required="false" %>

<a href="/catalog/${article_id}">
    <div class="card shadow-sm" style="width: 22rem;margin: 5px;">
        <img style="min-width: 350px;max-width: 350px;min-height: 250px;max-height: 250px;" class="card-img-top" src="/${article_img_path}"/>
        <div class="card-body">
            <h5 class="card-title">${article_name}</h5>
            <div class="card-text text-black-50">${article_price} CHF</div>
            <button class="btn btn-primary" onclick="addArticleToBasket('${article_id}', '1', '${_csrf.parameterName}', '${_csrf.token}', '')">Ajout au panier</button>
        </div>
    </div>
</a>