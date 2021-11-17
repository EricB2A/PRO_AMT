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
        <script>
            (function() {


            })();
        </script>
        <div class="layout_padding gallery_section">
            <div class="container bg-transparent text-white">

                <div id="Catalogue" >
                    <h2 class="text-white">Articles</h2>

                    <c:if test="${empty carpets}">
                        <p>Aucun article disponible</p>
                    </c:if>

                    <c:if test="${not empty carpets}">
                        <c:if test="${not empty categories}">
                            <div class="category-filters btn-group btn-group-toggle mb-4">
                                <a href="/catalog" class="btn btn-primary ${empty filter ? 'active' : '' }">
                                    <input type="radio" name="options" id="option1" autocomplete="off"> Tous
                                </a>
                                <c:forEach varStatus="idx" items="${categories}" var="category">
                                    <a href="/catalog/filter/${category.name}" class="btn btn-primary ${ filter == category.name ? 'active' : '' }">
                                        <input type="radio" name="options" id="option2" autocomplete="off"> ${category.name}
                                    </a>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="d-flex flex-wrap">
                           <c:forEach varStatus="idx" items="${carpets}" var="carpet">
                               <a href="/carpets/${carpet.id}">
                                   <t:article_thumbnail>
                                       <jsp:attribute name="article_id">${carpet.id}</jsp:attribute>
                                       <jsp:attribute name="article_name">${carpet.name}</jsp:attribute>
                                       <jsp:attribute name="article_price">${carpet.price}</jsp:attribute>
                                       <jsp:attribute name="article_quantity">${carpet.quantity}</jsp:attribute>
                                       <jsp:attribute name="article_img_path">${carpet.getFirstPhotoPath()}</jsp:attribute>
                                       <jsp:attribute name="article_nb_stars">${3}</jsp:attribute>
                                   </t:article_thumbnail>
                               </a>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_layout>
