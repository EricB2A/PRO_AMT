package com.example.amt_demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CookieUtils {
    public static final String COOKIE_NAME = "UserCart";
    public static final String SPLIT_CHAR = "#";
    public static final String ARTICLE_SEPARATOR = ":";

    public static void storeArticleToCartCookie(HttpServletRequest request, HttpServletResponse response, String productID, int quantity) {

        List<String> articlesAsString = getArticlesFromCartCookie(request);

        //TODO: Je suis fatigé, j'expliquerai demain ce que je fais ici.
        int f = -1;
        for (int i = 0; i < articlesAsString.size(); i++) {

            String el = articlesAsString.get(i);
            String pro = el.split(SPLIT_CHAR)[0];

            int qtity = 0;
            if(el.split(SPLIT_CHAR).length > 1){
                qtity = Integer.parseInt(el.split(SPLIT_CHAR)[1]);
            }
            
            if(productID.equals(pro)) {
                quantity += qtity;
                f = i;
                break;
            }
        }

        if(f != -1) {
            articlesAsString.remove(f);
        }

        articlesAsString.add(productID+"#"+quantity);


        // https://datatracker.ietf.org/doc/html/rfc7230#section-3.2.6 :(
        String n = articlesAsString.toString().replaceAll("[\\[\\](){} ]","").replace(",", ARTICLE_SEPARATOR);


        Cookie cookieUpdated = new Cookie(COOKIE_NAME, n);
        //TODO: pramètres setDomain, setSecure, svp aled


        response.addCookie(cookieUpdated);
    }

    public static List<String> getArticlesFromCartCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        Optional<String> maybeCookieValue = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .map(Cookie::getValue)
                .findAny();


        String cookieValue = maybeCookieValue.orElse(null);
        if (cookieValue == null) {
            return new ArrayList<>();
        }


        List<String> articlesAsString = new ArrayList<>(Arrays.asList(cookieValue.split(ARTICLE_SEPARATOR)));

        return articlesAsString;
    }

    /*
    private static String listAsString(List<Integer> list) {
        list.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("-", "{", "}"));

    }
    
     */
}
