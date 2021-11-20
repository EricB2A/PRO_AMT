/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartController.java
 *
 * @brief
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import com.example.amt_demo.model.Article;
import com.example.amt_demo.utils.CookieUtils;
import com.example.amt_demo.utils.MiscUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping(path = "/cart")
@Controller
public class CartController {

    private final CartInfoRepository cartInfoRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param cartInfoRepository
     * @param articleRepository
     * @param userRepository
     */
    public CartController(CartInfoRepository cartInfoRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        // On écrit un constructeur de 5 lignes pour retirer 3 lignes d'@autowired.. les calculs sont pas bons.
        this.cartInfoRepository = cartInfoRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    /**
     *
     * @return
     */
    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            return userRepository.findByUsername(auth.getName());
        }
        return null;
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
        List<CartInfo> cartInfoFromCookies = new ArrayList<>();

        for(String articleAsString: cartAsString) {
            String articleID = articleAsString.split(CookieUtils.SPLIT_CHAR)[0];
            int quantity = Integer.parseInt( articleAsString.split(CookieUtils.SPLIT_CHAR)[1] );

            Optional<Article> carpet = articleRepository.findById(Integer.valueOf(articleID));
            carpet.ifPresent(value -> cartInfoFromCookies.add(new CartInfo(value, quantity)));
        }

        List<CartInfo> merged;
        User user = getLoggedUser();

        if(user != null){ // Si l'utilisateur est connecté

            // On récupère ce qui est en DB
            List<CartInfo> cartInfoFromDatabase = cartInfoRepository.findCartInfosByUserId(user.getId());

            // On merge ce qui est en DB avec ce qui est dans les cookies
            merged = MiscUtils.mergeList(cartInfoFromDatabase, cartInfoFromCookies);

            // Puis, on supprime les cookies
            CookieUtils.destroyCookie(response);

            // Et pour finir on save tout ça en DB
            for (CartInfo c : merged) {
                cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(c.getArticle().getId(), user.getId(), c.getQuantity());
            }
        } else {
            merged = cartInfoFromCookies;
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
    public void addProductToCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody Map<String, Object> payload){
        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            User user = getLoggedUser();
            if(user != null){

                List<CartInfo> cartInfos = cartInfoRepository.findCartInfosByCarpetAndByUser(Integer.parseInt(id), user.getId());
                if(cartInfos != null && cartInfos.isEmpty()) {
                    Optional<Article> carpet = articleRepository.findCarpetById(Integer.valueOf(id));
                    carpet.ifPresent(value -> cartInfoRepository.save(new CartInfo(carpet.get(), quantity, user)));

                } else if(cartInfos != null) {
                    Optional<Article> carpet = articleRepository.findCarpetById(Integer.valueOf(id));

                    for (CartInfo ci : cartInfos) {
                        cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(Integer.parseInt(id), user.getId(), quantity + ci.getQuantity());
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
    public void removeProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        User user = getLoggedUser();
        if(user != null) {
            cartInfoRepository.deleteCartInfoByCarpetIdAndByUserId(Integer.parseInt(id), user.getId());
        } else {
            CookieUtils.removeArticleFromCartCookie(request, response, id);
        }
    }

    @PutMapping(path="/{id}")
    public void editProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody Map<String, Object> payload) {
        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            User user = getLoggedUser();
            if(user != null) {
                cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(Integer.parseInt(id), user.getId(), quantity);

            } else {
                CookieUtils.storeArticleToCartCookie(request, response, id, quantity, true);
            }
        }

    }

}
