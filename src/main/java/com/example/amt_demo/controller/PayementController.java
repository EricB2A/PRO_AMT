package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.ArticleService;
import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import javax.servlet.http.HttpServletResponse;


@AllArgsConstructor
class PayementInfo {
    private String amount;
    private String token;

    public String getAmount() {
        return amount;
    }

    public String getToken() {
        return token;
    }
}
@AllArgsConstructor
@Getter
class Token {
    private String clientSecret;
}

@RequestMapping(path = "/payement")
@RestController
@AllArgsConstructor
public class PayementController {
    private final CartInfoRepository cartInfoRepository;
    private final ArticleService articleService;
    private final CustomUserService userDetails;
    private final PurchaseRepository purchaseRepository;

    @Value("${PAYEMENT_SERVICE_IP}")
    private final String url = "";


    @GetMapping(path = "/command")
    public RedirectView makeCommand(HttpServletResponse httpServletResponse, final RedirectAttributes redirectAttributes) {
        CustomUserDetails user = userDetails.getUser();
        Purchase purchase = new Purchase((long)user.getId());
        //Update quantities :
        List<Cart> cart = cartInfoRepository.findCartInfosByUserId(user.getId());
        for(Cart item : cart) {
            Article article = item.getArticle();
            articleService.handleQuantity(article.getId(), -item.getQuantity());
            ArticlePurchased articlePurchased = new ArticlePurchased(article.getName(), item.getQuantity(), article.getPrice());
            purchase.addArticle(articlePurchased);
            cartInfoRepository.delete(item);
        }
        purchaseRepository.save(purchase);
        // "/cart?succeeded=true"
        //httpServletResponse.setHeader("Location", );
        //RedirectView rv = new RedirectView("cart")
        //return "forward:/cart?succeeded=true";
        redirectAttributes.addAttribute("succeeded", "true");
        return new RedirectView("/cart");

        //return "redirect:/cart?succeeded=true";



/*        redirectAttributes.addAttribute("param1", request.getAttribute("param1"));
        redirectAttributes.addAttribute("param2", request.getAttribute("param2"));

        redirectAttributes.addAttribute("attribute", "forwardedWithParams");
        return new RedirectView("redirectedUrl");*/
        //return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/pay")
    public ResponseEntity makePayementTesMorts(@RequestBody PayementInfo info) {
        CustomUserDetails user = userDetails.getUser();
        List<Cart> cart = cartInfoRepository.findCartInfosByUserId(user.getId());
        for(Cart item : cart) {
            int number = item.getQuantity();
            int stock = item.getArticle().getQuantity();
            if (number > stock) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        WebClient webclient = WebClient.create(url);

        JSONObject json = new JSONObject();
        try {
            json = json.put("amount", info.getAmount());
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<String> serverResponse = webclient
                .post()
                .uri("/api/charge")
                .header("token", info.getToken())
                .body(BodyInserters.fromValue(json.toString()))
                .retrieve()
                //Prevents error throwing
                .onStatus(
                        status -> status.value() == 400,
                        clientResponse -> Mono.empty()
                )
                .toEntity(String.class)
                .block();

        if (serverResponse.getStatusCodeValue() == 400) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {

            Purchase purchase = new Purchase((long)user.getId());
            //Update quantities :
            for(Cart item : cart) {
                Article article = item.getArticle();
                articleService.handleQuantity(article.getId(), -item.getQuantity());
                purchase.addArticle(new ArticlePurchased(article.getName(), item.getQuantity(), article.getPrice()));
            }
            purchaseRepository.save(purchase);
            return new ResponseEntity(HttpStatus.OK);
        }
    }



    @PostMapping(path = "/create-payment-intent")
    public ResponseEntity createPaymentIntent(@RequestBody PayementInfo info) throws StripeException {
        // TODO var env
        Stripe.apiKey = "TODO";
        System.out.println("uUUuU" + getCartAmount());
        CustomUserDetails user = userDetails.getUser();
        if(user != null){
            List<Cart> cart = cartInfoRepository.findCartInfosByUserId(user.getId());
            for(Cart item : cart) {
                int number = item.getQuantity();
                int stock = item.getArticle().getQuantity();
                if (number > stock) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        }

        // CreatePayment postBody = gson.fromJson(, CreatePayment.class);
      PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
        /*
            "A positive integer representing how much to charge in the smallest currency unit
            (e.g., 100 cents to charge $1.00 or 100 to charge ¥100, a zero-decimal currency)."
            https://stripe.com/docs/api/charges et https://stripe.com/docs/currencies#zero-decimal
         */
          .setAmount((long) (getCartAmount() * 20))
          .setCurrency("chf")
          .setAutomaticPaymentMethods(
            PaymentIntentCreateParams.AutomaticPaymentMethods
              .builder()
              .setEnabled(true)
              .build()
          )
          .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return ResponseEntity.status(HttpStatus.OK).body(new Token(paymentIntent.getClientSecret()));
    }

    private Double getCartAmount(){
        CustomUserDetails user = userDetails.getUser();
        if(user != null){
            List<Cart> cartFromDatabase = cartInfoRepository.findCartInfosByUserId(userDetails.getUser().getId());
            return cartFromDatabase.stream().mapToDouble(c -> (c.getArticle().getPrice() * c.getQuantity())).sum();
        }

        return 0.;
    }
}
