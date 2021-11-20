/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CookieUtlis.java
 *
 * @brief TODO
 */

package com.example.amt_demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CookieUtils {
    public static final String COOKIE_NAME = "UserCart";
    public static final String SPLIT_CHAR = "#";
    public static final String ARTICLE_SEPARATOR = ":";

    private static final int COOKIE_AGE = 7 * 24 * 60 * 60; // 7 jours

    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     *
     * @param request
     * @param response
     * @param productID
     * @param quantity
     * @param replaceQuantity
     */
    public static void storeArticleToCartCookie(HttpServletRequest request, HttpServletResponse response, String productID, int quantity, boolean replaceQuantity) {
        /*
          Si l'utilisateur s'amuse à modifier ses cookies, on aura à coup sûr des problèmes au parsing.
          Donc si le cookie est illisible -> on catch l'erreur -> suppression du cookie -> plus de panier, mais pas d'erreur !
         */
        try {

            List<String> articlesAsString = getArticlesFromCartCookie(request, response);

            /*
            //TODO: explication
             */
            int f = -1;
            for (int i = 0; i < articlesAsString.size(); i++) {

                String el = articlesAsString.get(i);
                String pro = el.split(SPLIT_CHAR)[0];

                int qtity = 0;
                if(el.split(SPLIT_CHAR).length > 1){
                    qtity = Integer.parseInt(el.split(SPLIT_CHAR)[1]);
                }

                if(productID.equals(pro)) {
                    if(!replaceQuantity) {
                        quantity += qtity;
                    }

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
            cookieUpdated.setPath("/cart");
            //TODO: pramètres setDomain, setSecure, svp aled
            cookieUpdated.setMaxAge(COOKIE_AGE);

            response.addCookie(cookieUpdated);

        }catch(Exception exception){
            logger.error(exception.getMessage());
            destroyCookie(response);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param productID
     * @param quantity
     */
    public static void storeArticleToCartCookie(HttpServletRequest request, HttpServletResponse response, String productID, int quantity) {
        storeArticleToCartCookie(request, response, productID, quantity, false);
    }

    /**
     *
     * @param list
     * @return
     */
    private static String serialize(List<String> list) {
        // https://curl.se/rfc/cookie_spec.html
        return list.toString().replaceAll("[\\[\\](){} ]","").replace(",", ARTICLE_SEPARATOR);
    }

    /**
     *
     * @param request
     * @param response
     * @param productIDToRemove
     */
    public static void removeArticleFromCartCookie(HttpServletRequest request, HttpServletResponse response, String productIDToRemove) {

        List<String> articlesAsString = getArticlesFromCartCookie(request, response);

        List<String> articlesAsStringUpdated =  articlesAsString.stream()
                .filter((el) -> {
                    String pro = el.split(SPLIT_CHAR)[0];
                    return !(pro.equals(productIDToRemove));
                })
                .collect(Collectors.toList());

        Cookie cookieUpdated = new Cookie(COOKIE_NAME, serialize(articlesAsStringUpdated));
        cookieUpdated.setPath("/cart");
        cookieUpdated.setMaxAge(COOKIE_AGE);
        response.addCookie(cookieUpdated);
    }

    public static List<String> getArticlesFromCartCookie(HttpServletRequest request, HttpServletResponse response) {
         /*
            cf. storeArticleToCartCookie
         */
        try {

            Cookie[] cookies = request.getCookies();

            Optional<String> maybeCookieValue = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                    .map(Cookie::getValue)
                    .findAny();


            String cookieValue = maybeCookieValue.orElse(null);
            if (cookieValue == null) {
                return new ArrayList<>();
            }

            /*
                - Dis donc Jamy, pourquoi tu utilises un constructeur en lui passant une liste et tu ne fais pas une bête assignation ?
                - Mais parce que asList(...) renvoie une liste dont la taille ne peut être changée et nous fera à 100% de chance
                 une erreur un peu plus tard ! Énorme !
             */
            List<String> cookieValueAsString = new ArrayList<String>(Arrays.asList(cookieValue.split(ARTICLE_SEPARATOR)));

            for(String el : cookieValueAsString){
                String articleID = el.split(SPLIT_CHAR)[0];
    
                if(el.split(CookieUtils.SPLIT_CHAR).length != 2 || articleID == null || articleID.isEmpty()){
                    logger.error("Wrong cookie format");
                    destroyCookie(response);
                    return new ArrayList<>();
                }
            }

            return new ArrayList<>(Arrays.asList(cookieValue.split(ARTICLE_SEPARATOR)));

        } catch(Exception exception) {
            logger.error(exception.getMessage());
            destroyCookie(response);
        }

        return new ArrayList<>();

    }

    /**
     *
     * @param response
     */
    public static void destroyCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        // Le cookie à détruire doit être identique.
        cookie.setPath("/cart");
        response.addCookie(cookie);
    }
}