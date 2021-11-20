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
            <div class="container">
                <div class="collection_text">${category.get().name}</div>
                <div class="about_main layout_padding">
                    <div class="collection_section">
                        <div class="container">
                            <h1 class="new_text"><strong>${category.get().name}</strong></h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
