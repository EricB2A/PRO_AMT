<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@attribute name="metatags" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@attribute name="withbanner" required="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- mobile metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <!-- site metas -->
    <title>${title}</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">

    <jsp:invoke fragment="metatags"/>

    <!-- bootstrap css -->
    <link rel="stylesheet" href="/css/bootstrap.min.css">

    <!-- style css -->
    <link rel="stylesheet" href="/css/style.css">
    <!-- Responsive-->
    <link rel="stylesheet" href="/css/responsive.css">
    <link rel="stylesheet" href="/css/catalog.css">
    <!-- fevicon -->
    <link rel="icon" type="image/png" href="/images/fav.png"/>
    <!-- Scrollbar Custom CSS -->
    <link rel="stylesheet" href="/css/jquery.mCustomScrollbar.min.css">
    <!-- Tweaks for older IEs-->
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <!-- owl stylesheets -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css"
          media="screen">

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
    <!--
    <script crossorigin src="https://unpkg.com/react@17/umd/react.development.js"></script>
    <script crossorigin src="https://unpkg.com/react-dom@17/umd/react-dom.development.js"></script>
    -->
    <script src="https://js.stripe.com/v3/"></script>
<%--    <script src="https://unpkg.com/@stripe/react-stripe-js@latest/dist/react-stripe.umd.js"></script>--%>

    <!--
    <script src="/js/embeddedCheckout.js"></script>
    -->
    <script src="/js/cartCheckout.js" defer></script>
</head>
<!-- body -->
<body class="main-layout">
<!-- header section start -->

<jsp:include page="/WEB-INF/jsp/common/header.jsp"  />

<c:if test="${withbanner.equals(\"true\")}" >
    <jsp:include page="/WEB-INF/jsp/common/banner.jsp" />
</c:if>

<jsp:doBody/>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />

<!-- Javascript files-->
<script src="/js/jquery.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="/js/jquery-3.0.0.min.js"></script>
<script src="/js/plugin.js"></script>
<!-- sidebar -->
<script src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="/js/custom.js"></script>
<!-- javascript -->
<script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
<script src="/js/signup.js"></script>
<script>
    $(document).ready(function () {
        $(".fancybox").fancybox({
            openEffect: "none",
            closeEffect: "none"
        });


        $('#myCarousel').carousel({
            interval: false
        });

        //scroll slides on swipe for touch enabled devices

        $("#myCarousel").on("touchstart", function (event) {

            var yClick = event.originalEvent.touches[0].pageY;
            $(this).one("touchmove", function (event) {

                var yMove = event.originalEvent.touches[0].pageY;
                if (Math.floor(yClick - yMove) > 1) {
                    $(".carousel").carousel('next');
                } else if (Math.floor(yClick - yMove) < -1) {
                    $(".carousel").carousel('prev');
                }
            });
            $(".carousel").on("touchend", function () {
                $(this).off("touchmove");
            });
        });

    });

    /*

    document.getElementById("category").click();

    function openTab(evt, tabName) {
        // Declare all variables
        var i, tabcontent, tablinks;

        // Get all elements with class="tabcontent" and hide them
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        // Get all elements with class="tablinks" and remove the class "active"
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        // Show the current tab, and add an "active" class to the button that opened the tab
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
    }

    */

    // Non à jQuery.
    function deleteArticle(tokenName, csrfToken) {
        if (confirm("Supprimer ?")){

            const headers = new Headers({
                'Content-Type': 'x-www-form-urlencoded',
                'X-CSRF-TOKEN': csrfToken
            });

            fetch(window.location.href, { method: 'DELETE', headers })
                .then((res)=>{
                    if(res.ok){
                        window.location.href = window.location.origin + "/accueil"
                    }else{
                        //TODO: Rediriger je ne sais pas où.
                    }
                });
        }
    }

    function addArticleToBasket(articleID, quantity,  tokenName, csrfToken, redirect) {
        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        });

     
        if(quantity <= 0){
            alert("Quantité invalide");
            return;
        }

        if(redirect === ""){
            redirect = window.location.origin
        }

        fetch(window.location.origin+"/cart/" + articleID, { method: 'POST', headers, body: JSON.stringify({quantity: quantity}) })
            .then((res)=>{
                if(res.ok){
                    window.location.href = redirect
                }else{
                    //TODO: Rediriger je ne sais pas où.
                }
            });
    }

    function updateArticleToBasket(articleID, quantity,  tokenName, csrfToken, redirect) {
        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        });

        if(quantity < 0){
            alert("Quantité invalide");
            return;
        }else if(quantity == 0){ // L'égalité non-stricte est volontaire ici
            removeArticleFromBasket(articleID, tokenName, csrfToken, redirect);
            return;
        }

        if(redirect === ""){
            redirect = window.location.origin
        }

        fetch(window.location.origin+"/cart/" + articleID, { method: 'PUT', headers, body: JSON.stringify({quantity: quantity}) })
            .then((res)=>{
                if(res.ok){
                    window.location.href = redirect
                }else{
                    //TODO: Rediriger je ne sais pas où.
                }
            });
    }

    function removeArticleFromBasket(articleID, tokenName, csrfToken, redirect){
        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        });

        if(!confirm("Retirer l'article du panier?")){
            return;
        }

        fetch(window.location.origin+"/cart/" + articleID, { method: 'DELETE', headers })
            .then((res)=>{
                if(res.ok){
                    window.location.href = redirect
                }else{
                    //TODO: Rediriger je ne sais pas où.
                }
            });

    }
    function payCart(){
        document.getElementById("payment").style.display = "block";
        document.getElementById("cart").style.display = "none";

    }
    function removeAllArticlesFromBasket(tokenName, csrfToken, redirect) {
        // '${_csrf.parameterName}', '${_csrf.token}', window.location.origin + '/cart'

        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        });

        if(!confirm("Vider le panier ?")){
            return;
        }

        fetch(window.location.origin+"/cart/" , { method: 'DELETE', headers })
            .then((res)=>{
                if(res.ok){
                    window.location.href = redirect
                }else{
                    //TODO: Rediriger je ne sais pas où.
                }
            });

    }

</script>
</body>
</html>