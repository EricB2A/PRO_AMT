/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartController.java
 *
 * @brief TODO
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import com.example.amt_demo.model.Article;
import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserService;
import com.example.amt_demo.utils.CookieUtils;
import com.example.amt_demo.utils.MiscUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping(path = "/cart")
@Controller
@AllArgsConstructor
public class CartController {

    private final CartInfoRepository cartInfoRepository;
    private final ArticleRepository articleRepository;
    private final CustomUserService userDetails;


    /**
     *
     * @return
     */
    private Principal getLoggedUser() {

        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     *
     * @param request
     * @param response
     * @param mp
     * @return
     */
    @GetMapping(path="")
    public String getCart(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {

        List<String> cartAsString = CookieUtils.getArticlesFromCartCookie(request, response);
        List<Cart> cartFromCookies = new ArrayList<>();

        for(String articleAsString: cartAsString) {
            String articleID = articleAsString.split(CookieUtils.SPLIT_CHAR)[0];
            int quantity = Integer.parseInt( articleAsString.split(CookieUtils.SPLIT_CHAR)[1] );

            Optional<Article> carpet = articleRepository.findById(Long.valueOf(articleID));
            carpet.ifPresent(value -> cartFromCookies.add(new Cart(value, quantity)));
        }

        List<Cart> merged;
        CustomUserDetails user = userDetails.getUser();

        if(user != null){ // Si l'utilisateur est connecté

            // On récupère ce qui est en DB
            List<Cart> cartFromDatabase = cartInfoRepository.findCartInfosByUserId(userDetails.getUser().getId());

            // On merge ce qui est en DB avec ce qui est dans les cookies
            merged = MiscUtils.mergeList(cartFromDatabase, cartFromCookies);

            // Puis, on supprime les cookies
            CookieUtils.destroyCookie(response);

            // Et pour finir on save tout ça en DB
            for (Cart c : merged) {
                cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(c.getArticle().getId(), userDetails.getUser().getId(), c.getQuantity());
            }
        } else {
            merged = cartFromCookies;
        }

        Double sumPrice = merged.stream().mapToDouble(c -> (c.getArticle().getPrice() * c.getQuantity())).sum();

        mp.addAttribute("articles", merged);
        mp.addAttribute("cartPrice", sumPrice);
        return "cart";
    }

    /**
     *
     * @param request
     * @param response
     * @param id
     * @param payload
     */
    @PostMapping(path="/{id}")
    public void addProductToCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestBody Map<String, Object> payload){
        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            CustomUserDetails user = userDetails.getUser();
            if(user != null){

                List<Cart> carts = cartInfoRepository.findCartInfosByCarpetAndByUser(id, user.getId());
                if(carts != null && carts.isEmpty()) {
                    Optional<Article> carpet = articleRepository.findById(id);
                    carpet.ifPresent(value -> cartInfoRepository.save(new Cart(carpet.get(), quantity, user.getId())));

                } else if(carts != null) {
                    Optional<Article> carpet = articleRepository.findById(id);


                    for (Cart ci : carts) {
                        cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(id, user.getId(), quantity + ci.getQuantity());
                    }
                }
            } else {
                CookieUtils.storeArticleToCartCookie(request, response, id, quantity);
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param id
     */
    @DeleteMapping(path="/{id}")
    public void removeProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        CustomUserDetails user = userDetails.getUser();
        if(user != null) {
            cartInfoRepository.deleteCartInfoByCarpetIdAndByUserId(id, user.getId());
        } else {
            CookieUtils.removeArticleFromCartCookie(request, response, id);
        }
    }

    @DeleteMapping
    public void removeAllProductsFromCart(HttpServletRequest request, HttpServletResponse response) {
        CustomUserDetails user = userDetails.getUser();
        if(user != null) {
            cartInfoRepository.deleteAllCartInfoByUser(user.getId());
        } else {
            CookieUtils.destroyCookie(response);
        }

    }

    @PutMapping(path="/{id}")
    public void editProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestBody Map<String, Object> payload) {
        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            CustomUserDetails user = userDetails.getUser();
            if(user != null) {
                cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(id, user.getId(), quantity);

            } else {
                CookieUtils.storeArticleToCartCookie(request, response, id, quantity, true);
            }
        }
    }


}