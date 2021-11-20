<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: - Administration
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>

        <div class="layout_padding gallery_section">

            <div class="container bg-white">

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                    ${error}
                    </div>
                </c:if>

                <c:if test="${empty error_article}">
                    <c:forEach varStatus="idx" items="${error_article}" var="categories">
                        <div class="table-row">
                            <div class="table-data">${error_article.name}</div>
                        </div>
                    </c:forEach>
                </c:if>

                <c:if test="${not empty msg_article_deleted}">
                <div class="alert alert-success">
                    L'article a bien été supprimé
                </div>
                </c:if>

                <c:if test="${not empty msg_article_added && msg_article_added}">
                <div class="alert alert-success">
                    L'article a bien été ajouté
                </div>
                </c:if>

                <c:if test="${not empty msg_article_quantity_increase}">
                    <div class="alert alert-success">
                        La quantité a bien été augmentée
                    </div>
                </c:if>

                <c:if test="${not empty msg_article_quantity_decrease}">
                    <div class="alert alert-success">
                        La quantité a bien été diminuée
                    </div>
                </c:if>

                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="/admin/articles">Articles</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/categories">Catégories</a>
                    </li>
                </ul>


                <div id="Articles" >
                    <div class="p-2">
                    <h3>Articles</h3>

                    <div class="d-flex justify-content-between">
                        <div>Vous pouvez ajouter, éditer ou supprimer des articles</div>
                        <button type="button" class="btn btn-danger"><a class="text-white" href="/admin/articles/add">Ajouter un article</a></button>
                    </div>

                    <c:if test="${empty articles}">
                        <p>Aucun article disponible</p>
                    </c:if>
                    </div>
                    <c:if test="${not empty articles}">
                        <div class="table">
                            <div class="table-header">
                                <div class="header__item"><a id="name" class="filter__link">Nom de l'article</a></div>
                                <div class="header__item"><a id="desc" class="filter__link">Description</a></div>
                                <div class="header__item"><a id="price" class="filter__link filter__link--number">Prix</a></div>
                                <div class="header__item"><a id="quantity" class="filter__link filter__link--number">Quantité</a></div>
                                <div class="header__item"><a id="action" class="filter__link filter__link--number">Actions</a></div>
                            </div>
                            <div class="table-content">

                                <c:forEach varStatus="idx" items="${articles}" var="articles">
                                    <div class="table-row">
                                        <div class="table-data">${articles.name}</div>
                                        <div class="table-data">${articles.description}</div>
                                        <div class="table-data">${articles.price}</div>
                                        <div class="table-data">
                                            <a class="btn btn-danger btn-sm" href="/admin/articles/quantity/decrease/${articles.id}">-</a>
                                                ${articles.quantity}
                                            <a class="btn btn-success btn-sm" href="/admin/articles/quantity/increase/${articles.id}">+</a>
                                        </div>
                                        <div class="table-data">
                                            <a href="/admin/articles/edit/${articles.id}">
                                                <img alt="Edit" src="/images/outline_edit_black_24dp.png">
                                            </a>
                                            <a href="/admin/articles/delete/${articles.id}">
                                                <img alt="Delete" src="/images/outline_delete_black_24dp.png">
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>

                <!-- Tab content -->



            </div>
        </div>
    </jsp:body>
</t:base_layout>