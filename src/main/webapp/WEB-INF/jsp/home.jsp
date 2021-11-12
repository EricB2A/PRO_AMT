<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:base_layout>
    <jsp:attribute name="title">
        SilkyRoadASDASDASDASDASD
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        true
    </jsp:attribute>
    <jsp:body>
        <div class="layout_padding gallery_section">

            <div class="container">
                <sec:authorize access="hasRole('admin')">
                    <a class="btn add_bt" href="/carpets/new">Nouvel article</a>
                </sec:authorize>
                <h2>
                    Boutique
                </h2>
                <c:forEach varStatus="idx" items="${articles}" var="article">
                <c:if test="${ idx.index % 3 == 0 && idx.index != 0}">
            </div>
            </c:if>
            <c:if test="${idx.index % 3 == 0}">
            <div class="row">
                </c:if>
                <div class="col-sm-4">
                    <a href="/carpets/${article.id}">
                        <t:article_thumbnail>
                            <jsp:attribute name="article_id">${article.id}</jsp:attribute>
                            <jsp:attribute name="article_name">${article.name}</jsp:attribute>
                            <jsp:attribute name="article_price">${article.price}</jsp:attribute>
                            <jsp:attribute name="article_img_path">${article.getFirstPhotoPath()}</jsp:attribute>
                            <jsp:attribute name="article_nb_stars">${3}</jsp:attribute>
                        </t:article_thumbnail>
                    </a>
                </div>

                </c:forEach>
            </div>
        </div>

    </jsp:body>
</t:base_layout>