package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.CartInfo;
import com.example.amt_demo.model.CartInfoRepository;
import com.example.amt_demo.model.UserRepository;
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
import com.example.amt_demo.model.User;

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
    
    private final CarpetRepository carpetRepository;

    private final UserRepository userRepository;

    public CartController(CartInfoRepository cartInfoRepository, CarpetRepository carpetRepository, UserRepository userRepository) {
        this.cartInfoRepository = cartInfoRepository;
        this.carpetRepository = carpetRepository;
        this.userRepository = userRepository;
    }


    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            return userRepository.findByEmail(auth.getName());
        }
        return null;
    }


    @GetMapping(path="")
    public String getCart(HttpServletRequest request, HttpServletResponse response, ModelMap mp) {

        List<String> cartAsString = CookieUtils.getArticlesFromCartCookie(request, response);
        List<CartInfo> cartInfoFromCookies = new ArrayList<>();

        for(String articleAsString: cartAsString) {
            String articleID = articleAsString.split(CookieUtils.SPLIT_CHAR)[0];
            int quantity = Integer.parseInt( articleAsString.split(CookieUtils.SPLIT_CHAR)[1] );

            Optional<Carpet> carpet = carpetRepository.findById(Integer.valueOf(articleID));
            carpet.ifPresent(value -> cartInfoFromCookies.add(new CartInfo(value, quantity)));
        }

        List<CartInfo> merged = new ArrayList<>();
        User user = getLoggedUser();
        if(user != null){
            List<CartInfo> cartInfoFromDatabase = cartInfoRepository.findCartInfosByUserId(user.getId());
            merged = MiscUtils.mergeList(cartInfoFromDatabase, cartInfoFromCookies);

            // On supprime les cookies
            CookieUtils.destroyCookie(response);

            // Et on save tout ça en DB
            for (CartInfo c : merged) {
                Optional<CartInfo> fromDB = cartInfoRepository.findById(c.getId());
                if(fromDB.isPresent()){
                    fromDB.get().setQuantity(c.getQuantity());
                    cartInfoRepository.save(fromDB.get());
                }else {
                    c.setUser(user);
                    cartInfoRepository.save(c);
                }
            }

        }else{
            System.out.println("Not logged, cart from cookies.");
            merged = cartInfoFromCookies;
        }

        mp.addAttribute("articles", merged);
        return "cart";
    }

    @PostMapping(path="/{id}")
    public void addProductToCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody Map<String, Object> payload){

        int quantity = Integer.parseInt((String) payload.get("quantity") );

        if(quantity > 0) {
            User user = getLoggedUser();
            if(user != null){

                List<CartInfo> cartInfos = cartInfoRepository.findCartInfosByCarpetAndByUser(Integer.parseInt(id), user.getId());
                if(cartInfos != null && cartInfos.isEmpty()) {

                    Optional<Carpet> carpet = carpetRepository.findCarpetById(Integer.valueOf(id));
                    carpet.ifPresent(value -> {

                        cartInfoRepository.save(new CartInfo(carpet.get(), quantity, user));

                    });

                }else if(cartInfos != null){

                    Optional<Carpet> carpet = carpetRepository.findCarpetById(Integer.valueOf(id));

                    for (CartInfo ci : cartInfos) {
                        cartInfoRepository.setCartInfoQuantityByCarpetIdAndByUserId(Integer.parseInt(id), user.getId(), quantity + ci.getQuantity());
                    }
                }

                //

            }else{
                CookieUtils.storeArticleToCartCookie(request, response, id, quantity);
            }
        }

    }

    @DeleteMapping(path="/{id}")
    public void removeProductFromCart(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        User user = getLoggedUser();
        if(user != null) {
            cartInfoRepository.deleteCartInfoByCarpetIdAndByUserId(Integer.parseInt(id), user.getId());
        }else{
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
