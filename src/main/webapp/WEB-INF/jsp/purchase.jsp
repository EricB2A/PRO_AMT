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
      <div class="container bg-transparent text-white">

        <div id="Articles" >
          <h2 class="text-white">Mes commandes</h2>

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
                    <div class="table-data">${purchases.purchaseDate}</div>
                    <div class="table-data">CHF ${purchases.totalPrice}</div>
                    <div class="table-data">CHF ${purchases.totalPrice}</div>

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
