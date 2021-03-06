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

                            <a class="nav-item nav-link" href="/cart">Panier</a>

                            <sec:authorize access="isAuthenticated()">
                                <a class="nav-item nav-link" href="/purchase">Mes commandes</a>
                            </sec:authorize>

                            <sec:authorize access="!isAuthenticated()">
                                <a class="nav-item nav-link" href="/login">Login</a>
                            </sec:authorize>

                            <sec:authorize access="isAuthenticated() && hasRole('admin')">
                                <a class="nav-item nav-link" href="/admin/articles">Administration</a>
                            </sec:authorize>

                            <sec:authorize access="isAuthenticated()">
                                <form action="/signout" method=POST id="logout-form">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <a class="nav-item nav-link clickable" onclick="document.getElementById('logout-form').submit()" >D??connexion de <sec:authentication property="principal.username" /></a>
                                </form>
                            </sec:authorize>

                            <a class="nav-item nav-link last" href="#"><img src="/images/search_icon.png"></a>
                            <!-- <a class="nav-item nav-link last" href="/cart"><img src="/images/shop_icon.png"></a> -->
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</div>
<!-- header section end -->