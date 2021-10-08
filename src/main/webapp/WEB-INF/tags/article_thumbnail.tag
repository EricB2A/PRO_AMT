<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@attribute name="nb_stars" required="false" %>
<%@attribute name="article_name" required="true" %>
<%@attribute name="article_img_path" required="true" %>
<%@attribute name="article_price" required="true" %>
<%@attribute name="article_nb_stars" required="false" %>

<div class="best_shoes">
    <p class="best_text">${article_name}</p>
    <div class="shoes_icon"><img src="${article_img_path}"></div>
    <div class="star_text">
        <div class="left_part">
            <ul>
                <c:forEach var = "i" begin = "1" end ="${article_nb_stars}">
                    <li><a href="#"><img src="images/star-icon.png"></a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="right_part">
            <div class="shoes_price">CHF<span style="color: #ff4e5b;">${article_price}</span></div>
        </div>
    </div>
</div>