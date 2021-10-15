<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
                    <form name="login" action="/login" method="POST">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="login_email" class="form-label">Email</label>
                                <input id="login_email" type="email"  class="enter_email" placeholder="Entrez votre e-mail" name="email">

                            </div>
                        </div>
                        <div class="row">
                            <div class="offset-3 col-6">
                                <label for="login_password" class="form-label">Mot de passe</label>
                                <input id="login_password" type="password"  class="enter_email" placeholder="Entrez votre mot de passe"
                                       name="password">

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
