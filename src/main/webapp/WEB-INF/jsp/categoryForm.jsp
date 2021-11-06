<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:base_layout>

    <jsp:attribute name="title">
        Silky Road: ${articleForm}
    </jsp:attribute>
  <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
  <jsp:body>
    <div class="vh-100">
      <div class="gallery_section">
        <div class="container">
          <div class="collection_text">Nouvelle catégorie</div>
          <div class="about_main layout_padding">
            <div class="container">
              <div class="racing_shoes ">

                <form name="newCategoryForm" action="/admin" enctype="multipart/form-data"  method="POST" class="m-2"> <!-- TODO: changer lien hardcodé, mais trop fatigué pour le faire -->
                  <input type="hidden"
                         name="${_csrf.parameterName}"
                         value="${_csrf.token}"/>
                  <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                      <input class="form-control" type="text" name="name" />
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
