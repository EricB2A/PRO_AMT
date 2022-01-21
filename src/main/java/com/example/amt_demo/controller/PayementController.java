package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.ArticleService;
import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


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

    @PostMapping(path = "/pay")
    public ResponseEntity makePayement(@RequestBody PayementInfo info) {
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


}
