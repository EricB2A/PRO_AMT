<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: - Login
    </jsp:attribute>
    <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
    <jsp:body>
        <div class="vh-100">
            <div class="layout_padding gallery_section">
                <div class="container ">
                    <c:if test="${badcredential}">
                        <div class="alert alert-warning" role="alert">
                            Les informations fournies sont incorrectes <span style='font-size:20px;'>&#128546;</span>
                        </div>
                    </c:if>
                    <c:if test="${error}">
                        <t:internal_error>
                        </t:internal_error>
                    </c:if>
                    <c:if test="${param.signup.equals(\"success\")}">
                        <div class="alert alert-primary" role="alert">
                           Votre inscription a bien été effectuée, vous pouvez des à présent vous connecter pour acheter de la
                            <del>dro</del> des tapis, des tapis exotiques.
                        </div>
                    </c:if>
                    <form name="login" action="/login" method="POST">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="login_username" class="form-label">Nom d'utilisateur</label>
                                <input id="login_username" type="text" class="enter_email"
                                       placeholder="Entrez votre nom d'utilisateur" name="username">

                            </div>
                        </div>
                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="login_password" class="form-label">Mot de passe</label>
                                <input id="login_password" type="password" class="enter_email"
                                       placeholder="Entrez votre mot de passe"
                                       name="password">

                            </div>

                        </div>
                        <div class="row">
                            <div class="offset-3">
                                <a href="/inscription">Pas encore de compte ?</a>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="offset-3 col-6">
                                <button class="btn btn-primary" type="submit">Connexion</button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
