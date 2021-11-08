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
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CookieUtils {
    public static final String COOKIE_NAME = "UserCart";
    public static final String SPLIT_CHAR = "#";
    public static final String ARTICLE_SEPARATOR = ":";

    public static void storeArticleToCartCookie(HttpServletRequest request, HttpServletResponse response, String productID, int quantity) {

        List<String> articlesAsString = getArticlesFromCartCookie(request);

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

        // String n = articlesAsString.toString().replaceAll("[\\[\\](){} ]","").replace(",", ARTICLE_SEPARATOR);

        Cookie cookieUpdated = new Cookie(COOKIE_NAME, serialize(articlesAsString));
        //TODO: pramètres setDomain, setSecure, svp aled

        response.addCookie(cookieUpdated);
    }

    private static String serialize(List<String> list) {
        // https://datatracker.ietf.org/doc/html/rfc7230#section-3.2.6 :(
        return list.toString().replaceAll("[\\[\\](){} ]","").replace(",", ARTICLE_SEPARATOR);

    }
    public static void removeArticleFromCartCookie(HttpServletRequest request, HttpServletResponse response, String productIDToRemove) {
        List<String> articlesAsString = getArticlesFromCartCookie(request);

        List<String> articlesAsStringUpdated =  articlesAsString.stream()
                .filter((el) -> {
                    String pro = el.split(SPLIT_CHAR)[0];
                    return !(pro.equals(productIDToRemove));
                })
                .collect(Collectors.toList());

        Cookie cookieUpdated = new Cookie(COOKIE_NAME, serialize(articlesAsStringUpdated));
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

        return new ArrayList<>(Arrays.asList(cookieValue.split(ARTICLE_SEPARATOR)));
    }

    public static void destroyCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

   
}
