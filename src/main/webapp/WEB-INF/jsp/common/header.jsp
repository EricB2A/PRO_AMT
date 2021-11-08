<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="header_section">
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="logo"><a href="/accueil"><img src="/images/logo.png"></a></div>
            </div>
            <div class="col-sm-9">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-item nav-link" href="/accueil">Accueil</a>
                            <a class="nav-item nav-link" href="/catalog">Tous les Articles</a>

                            <sec:authorize access="isAuthenticated() && hasRole('admin')">
                                <a class="nav-item nav-link" href="/admin/all">Administration</a>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <form action="/logout" method=post id="logout-form">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                     <%--Afin de ne pas s'embeter avec le css, utilisation d'un lien pour valider le formulaire...
                                     Formulaire nécessaire car CSRF pas/mal     géré par l'implémenation de base fourni par spring security (pour le logout).
                                     Il faut donc envoyer le formulaire soit même avec le token CRSF--%>
                                    <a class="nav-item nav-link clickable" onclick="document.getElementById('logout-form').submit()" >Déconnexion de <sec:authentication property="principal.username" /></a>
                                </form>

                            </sec:authorize>

                            <sec:authorize access="!isAuthenticated()">
                                <a class="nav-item nav-link" href="/login">Login</a>
                            </sec:authorize>

                            <a class="nav-item nav-link last" href="#"><img src="/images/search_icon.png"></a>
                            <a class="nav-item nav-link last" href="contact.html"><img src="/images/shop_icon.png"></a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</div>
<!-- header section end -->