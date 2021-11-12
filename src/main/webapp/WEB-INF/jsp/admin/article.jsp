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

                    </div>
                </div>
                <div class="collection_section_3 layout_padding">
                    <div class="container">
                        <div class="container bg-white">
                            <form name="addCategoryForm" action="/admin/addCategory/${article.get().id}" enctype="multipart/form-data"  method="POST" class="m-2">
                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <div>
                                    <c:forEach varStatus="idx" items="${categories}" var="categories">
                                        <div>
                                            <input type="checkbox" id="scales" name="categories" value="${categories.id}" >
                                            <label for="${categories.name}">${categories.name}</label>
                                        </div>
                                    </c:forEach>

                                </div>
                                <div>
                                    <button type="submit">S'abonner</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </jsp:body>
</t:base_layout>
