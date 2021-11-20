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
    <div class="vh-100">
      <div class="gallery_section">
        <div class="container">
          <div class="collection_text">
            <c:if test="${not empty adding && adding}">
              Ajouter une nouvelle catégorie
            </c:if>
            <c:if test="${not empty editing && editing}">
              Modifier la catégorie
            </c:if>
          </div>
          <div class="about_main">
            <div class="container">
              <a class="text-white" style="font-size: 18px;" href="/admin/categories">< Revenir</a>
              <div class="card p-4">
                <form name="newCategoryForm" action="${post_url}" enctype="multipart/form-data"  method="POST" class="m-2"> <!-- TODO: changer lien hardcodé, mais trop fatigué pour le faire -->
                  <input type="hidden"
                         name="${_csrf.parameterName}"
                         value="${_csrf.token}"/>
                  <c:if test="${not empty category}">
                    <input type="hidden" name="id" value="${category.id}"/>
                    <input type="hidden" name="id" value="${category.checked}"/>
                  </c:if>
                  <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Nom *</label>
                    <div class="col-sm-10">
                      <input class="form-control" type="text" name="name" value="<c:if test="${not empty category}">${category.name}</c:if>" />
                    </div>
                  </div>
                  <div class="row ">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                      <button type="submit" class="subscribr_bt">Soumettre</button>
                    </div>
                    <div class="col-sm-4"></div>
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
