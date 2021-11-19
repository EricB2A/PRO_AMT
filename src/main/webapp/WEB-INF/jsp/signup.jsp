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
                <h2>Inscription</h2>
                <div class="container ">
                    <c:if test="${userAlreadyExists}">
                        <div class="alert alert-warning" role="alert">
                            Ce nom d'utilisateur est déjà utilisé <span style='font-size:20px;'>&#128546;</span>
                        </div>
                    </c:if>
                    <c:if test="${error}">
                        <div class="alert alert-warning" role="alert">
                            Il semblerait que nous ayons un problème, veuillez réessayer plus tard
                        </div>
                        <div class="row">
                            <div class="offset-4 col-4">
                                <img src="/images/nothingToSeeHere.gif"/>
                            </div>
                        </div>
                    </c:if>
                    <form id="signup_form" name="signup" action="/inscription" method="POST">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="signup_username" class="form-label">Nom d'utilisateur</label>
                                <input id="signup_username" type="text" class="enter_email form-control signup-input"
                                       placeholder="Entrez votre nom d'utilisateur" name="username">



                                <c:if test="${errorform && not empty username}">
                                    <div class="custom-feedback">
                                            ${username}
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="signup_password" class="form-label">Mot de passe</label>
                                <input id="signup_password" type="password"
                                       class="enter_email form-control signup-input"
                                       placeholder="Entrez votre mot de passe" name="password">
                                <c:if test="${errorform && not empty password}">
                                    <div class="custom-feedback">
                                            ${password}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="offset-3">
                                <a href="/login">Déjà un compte ?</a>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="offset-3 col-6">
                                <button id="signupBtn" class="btn btn-primary" type="submit">S'incrire</button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_layout>
