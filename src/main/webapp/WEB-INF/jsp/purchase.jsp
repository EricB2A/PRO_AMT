<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:base_layout>
    <jsp:attribute name="title">
        Silky Road: - Mes commandes
    </jsp:attribute>
  <jsp:attribute name="withbanner">
        false
    </jsp:attribute>
  <jsp:body>
    <div class="layout_padding gallery_section">
      <div class="container bg-white">

        <div id="Commandes" >
          <div class="p-2">
            <h3>Mes commandes</h3>
            <div class="d-flex justify-content-between">
              <div>Vous trouverez la liste de toutes les commandes réalisées sur ce compte</div>
            </div>
          </div>

          <c:if test="${empty purchases}">
            <h3>La liste de commandes est vide</h3>
          </c:if>

          <c:if test="${not empty purchases}">
            <div class="table">
              <div class="table-header">
                <div class="header__item"><a id="name" class="filter__link">Date</a></div>
                <div class="header__item"><a id="desc" class="filter__link">Article(s)</a></div>
                <div class="header__item"><a id="price" class="filter__link filter__link--number">Total</a></div>
              </div>
              <div class="table-content">

                <c:forEach varStatus="idx" items="${purchases}" var="purchases">
                  <div class="table-row">
                    <div class="table-data">
                        ${purchases.purchaseDate}
                    </div>
                    <div class="table-data">

                      <c:forEach varStatus="idx" items="${purchases.article}" var="articles">
                        <c:set var="totalPrice" value="${totalPrice + articles.price}" />
                        ${articles.name}</br>
                      </c:forEach>

                    </div>
                    <div class="table-data">
                      CHF ${totalPrice}
                    </div>
                    <c:set var="totalPrice" value="0" />
                  </div>
                </c:forEach>

              </div>
            </div>
          </c:if>

        </div>
      </div>
    </div>
  </jsp:body>
</t:base_layout>
