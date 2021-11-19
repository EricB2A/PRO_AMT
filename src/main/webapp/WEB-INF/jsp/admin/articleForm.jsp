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
        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", function(event) {
                let categories = document.querySelectorAll(".category-cb");
                for(let category of categories){
                    category.addEventListener("click", (event) => {

                        let checked = event.target.classList.contains('bg-primary');
                        let id = event.target.getAttribute('id');
                        let checkbox = document.querySelector("input[type='checkbox']#"+id);

                        if(checked){
                            checkbox.removeAttribute('checked');
                            event.target.classList.remove('bg-primary');
                            event.target.classList.add('bg-secondary');
                        }else{
                            checkbox.setAttribute('checked', 'checked');
                            event.target.classList.add('bg-primary');
                            event.target.classList.remove('bg-secondary');
                        }
                    });
                }
            });

        </script>
        <c:if test="${not empty article}">
            <c:set var="carpet" value="${article.get()}" />
        </c:if>
        <div class="vh-100">
            <div class="gallery_section">
                <div class="container">

                    <div class="collection_text">
                        <c:if test="${not empty adding && adding}">
                            Ajouter un nouveau tapis
                        </c:if>
                        <c:if test="${not empty editing && editing}">
                            Modifier le tapis
                        </c:if>
                    </div>
                    <div class="about_main">
                            <div class="container">
                                <a class="text-white" style="font-size: 18px;" href="/admin/articles">< Revenir</a>
                                <div class="card p-4">
                                    <c:if test="${not empty msg_photo_deleted && msg_photo_deleted}">
                                        <div class="alert alert-success">
                                            La photo a été supprimé
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty msg_article_edited && msg_article_edited}">
                                        <div class="alert alert-success">
                                            L'article a bien été modifié
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty msg_missing_name && msg_missing_name}">
                                        <div class="alert alert-danger">
                                            Veillez renseigner le champ "Nom"
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty msg_already_existing_article && msg_already_existing_article}">
                                        <div class="alert alert-danger">
                                            Un article avec ce nom existe deja!
                                        </div>
                                    </c:if>



                                    <form name="newCarpetForm" action="${post_url}" enctype="multipart/form-data"  method="POST" class="m-2"> <!-- TODO: changer lien hardcodé, mais trop fatigué pour le faire -->
                                        <input type="hidden"
                                               name="${_csrf.parameterName}"
                                               value="${_csrf.token}"/>
                                                <c:if test="${not empty carpet}">
                                                    <input type="hidden" name="id" value="${carpet.id}"/>
                                                </c:if>

                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Catégories</label>
                                            <div class="col-sm-10">
                                                <c:forEach varStatus="idx" items="${categories_checked}" var="category">
                                                    <div class="d-inline-flex">
                                                        <span class="btn category-cb badge p-2 rounded-pill bg-primary text-white" id="category_${category.id}">${category.name}</span>
                                                        <input class="invisible" type="checkbox" id="category_${category.id}" name="categories" value="${category.id}" checked />
                                                    </div>
                                                </c:forEach>
                                                <c:forEach varStatus="idx" items="${categories_not_checked}" var="category">
                                                    <div class="d-inline-flex">
                                                        <span class="btn category-cb badge p-2 rounded-pill bg-secondary text-white" id="category_${category.id}">${category.name}</span>
                                                        <input class="invisible" type="checkbox" id="category_${category.id}" name="categories" value="${category.id}" />
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Nom*</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" name="name" value="<c:if test="${not empty carpet}">${carpet.name}</c:if>" />
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Description</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" name="description"><c:if test="${not empty carpet}">${carpet.description}</c:if></textarea>
                                                <small class="form-text text-muted">Décrivez en quelques phrases votre produit.</small>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Prix</label>
                                            <div class="col-sm-10">
                                                <input type="number" name="price" value="<c:if test="${not empty carpet}">${carpet.price}</c:if>" />
                                            </div>
                                        </div>


                                        <c:if test="${not empty carpet.photos}">
                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Photos</label>
                                            <div class="col-sm-10 d-flex flex-wrap">
                                                <c:forEach varStatus="idx" items="${carpet.photos}" var="photo">
                                                    <div class="d-flex align-self-start m-2">
                                                        <img src="/${photo.path}" width="220" class="img-thumbnail" />
                                                        <a href="/admin/articles/${carpet.id}/photo/delete/${photo.id}"><button type="button" class="btn btn-danger btn-sm">X</button></a>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        </c:if>
                                        <div class="form-group row">
                                            <label class="col-sm-2 col-form-label">Upload new</label>
                                            <div class="col-sm-10">
                                                <input type="file" name="images" accept="image/png, image/jpeg" multiple />
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
