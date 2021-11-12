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

                <c:if test="${not empty msg_article_deleted}">
                <div class="alert alert-success">
                    L'article a bien été supprimé
                </div>
                </c:if>

                <c:if test="${not empty msg_article_edited && msg_article_edited}">
                <div class="alert alert-success">
                    L'article a bien été modifié
                </div>
                </c:if>

                <c:if test="${not empty msg_article_added && msg_article_added}">
                <div class="alert alert-success">
                    L'article a bien été ajouté
                </div>
                </c:if>

                <div class="tab">
                    <button id="category" class="tablinks active" onclick="openTab(event, 'Categories')"><b>Catégories</b></button>
                    <button class="tablinks" onclick="openTab(event, 'Articles')"><b>Articles</b></button>
                </div>

                <!-- Tab content -->
                <div id="Categories" class="tabcontent">
                    <h3>Catégories</h3>
                    <div style="display: table-row">
                        <div style="width: 600px; display: table-cell;">Vous pouvez ajouter, éditer ou supprimer des catégories</div>
                        <div style="display: table-cell;"> <a href="/admin/category/new">+ Ajouter une catégorie</a> </div>
                    </div>

                    <c:if test="${not empty categories}">

                        <div class="table">
                            <div class="table-header">
                                <div class="header__item"><a id="name_cat" class="filter__link">Nom de la catégorie</a></div>
                                <div class="header__item"><a id="action_cat" class="filter__link">Actions</a></div>
                            </div>
                            <div class="table-content">

                                <c:forEach varStatus="idx" items="${categories}" var="categories">
                                    <div class="table-row">
                                        <div class="table-data">${categories.name}</div>
                                        <div class="table-data">
                                            <a href="/admin/category/edit/${categories.id}">
                                                <img alt="Edit" src="/images/outline_edit_black_24dp.png">
                                            </a>
                                            <a href="/admin/category/delete/${categories.id}">
                                                <img alt="Delete" src="/images/outline_delete_black_24dp.png">
                                            </a>

                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${empty categories}">
                        <p>Aucune catégorie disponible</p>
                    </c:if>
                </div>

                <div id="Articles" class="tabcontent">
                    <h3>Articles</h3>
                    <div style="width: 600px; display: table-cell;">Vous pouvez ajouter, éditer ou supprimer des articles</div>
                    <div style="display: table-cell;"> <a href="${pageContext.request.contextPath}/admin/carpets/add">+ Ajouter un article</a> </div>

                    <c:if test="${empty articles}">
                        <p>Aucun article disponible</p>
                    </c:if>

                    <c:if test="${not empty articles}">
                        <div class="table">
                            <div class="table-header">
                                <div class="header__item"><a id="name" class="filter__link">Nom de l'article</a></div>
                                <div class="header__item"><a id="desc" class="filter__link">Description</a></div>
                                <div class="header__item"><a id="price" class="filter__link filter__link--number">Prix</a></div>
                                <div class="header__item"><a id="action" class="filter__link filter__link--number">Actions</a></div>
                            </div>
                            <div class="table-content">

                                <c:forEach varStatus="idx" items="${articles}" var="carpets">
                                    <div class="table-row">
                                        <div class="table-data">${carpets.name}</div>
                                        <div class="table-data">${carpets.description}</div>
                                        <div class="table-data">${carpets.price}</div>
                                        <div class="table-data">
                                            <a href="/admin/carpets/edit/${carpets.id}">
                                                <img alt="Edit" src="/images/outline_edit_black_24dp.png">
                                            </a>
                                            <a href="/admin/carpets/delete/${carpets.id}">
                                                <img alt="Delete" src="/images/outline_delete_black_24dp.png">
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_layout>