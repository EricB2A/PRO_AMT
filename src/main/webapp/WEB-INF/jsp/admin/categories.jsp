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

                <c:if test="${not empty success}">
                    <div class="alert alert-success">
                        ${success}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                    ${error}
                    </div>
                </c:if>

                <c:if test="${not empty error_article}">
                    <c:forEach varStatus="idx" items="${error_article}" var="articles">
                        <div class="table-row">
                            <div class="table-data">${articles.name}</div>
                        </div>
                    </c:forEach>
                </c:if>

                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/articles">Articles</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="/admin/categories">Catégories</a>
                    </li>
                </ul>

                <!-- Tab content -->
                <div id="Categories" class="">
                    <div class="p-2">
                        <h3>Catégories</h3>
                        <div class="d-flex justify-content-between">
                            <div>Vous pouvez ajouter, éditer ou supprimer des catégories</div>
                            <button type="button" class="btn btn-danger"><a class="text-white" href="/admin/categories/add">Ajouter une catégorie</a></button>
                        </div>
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
                                            <a href="/admin/categories/edit/${categories.id}">
                                                <img alt="Edit" src="/images/outline_edit_black_24dp.png">
                                            </a>
                                            <a href="/admin/categories/delete/${categories.id}">
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


            </div>
        </div>
    </jsp:body>
</t:base_layout>