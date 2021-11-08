package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.CartInfo;
import com.example.amt_demo.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestMapping(path = "/cart")
@Controller
public class CartController {

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="")
    public String getCart(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {

        List<String> cart = CookieUtils.getArticlesFromCartCookie(request);

        System.out.println(cart);

        List<CartInfo> articles = new ArrayList<>();


       for(String articleAsString: cart) {

            String articleID = articleAsString.split(CookieUtils.SPLIT_CHAR)[0];
            if(Objects.equals(articleID, "")) {
                continue;
            }
            int quantity = 0;
            if(articleAsString.split(CookieUtils.SPLIT_CHAR).length > 1){
                quantity = Integer.parseInt( articleAsString.split(CookieUtils.SPLIT_CHAR)[1] );
            }

            Optional<Carpet> carpet = carpetRepository.findById(Integer.valueOf(articleID));
            if(carpet.isPresent()){
                articles.add(new CartInfo(carpet.get(), quantity));
            }

        }

        mp.addAttribute("articles", articles);
        return "cart";
    }

    @PostMapping(path="/{id}")
    public void addProductToCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody Map<String, Object> payload){

        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            CookieUtils.storeArticleToCartCookie(request, response, id, quantity);
        }
    }

    @DeleteMapping(path="{/id}")
    public void removeProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        CookieUtils.removeArticleFromCartCookie(request, response, id);
    }

    


}
