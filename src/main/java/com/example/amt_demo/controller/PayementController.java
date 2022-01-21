package com.example.amt_demo.controller;

import com.example.amt_demo.model.ArticleRepository;
import com.example.amt_demo.model.Cart;
import com.example.amt_demo.model.CartInfoRepository;
import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
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

@RequestMapping(path = "/payement")
@RestController
@AllArgsConstructor
public class PayementController {

    private final CartInfoRepository cartInfoRepository;
    private final ArticleRepository articleRepository;
    private final CustomUserService userDetails;

    private static final String payementServiceUrl = "http://localhost:8083";

    @PostMapping(path = "/pay")
    public ResponseEntity makePayement(HttpServletRequest request, HttpServletResponse response, @RequestParam String token, @RequestParam String id, @RequestParam String amount) {
        float total = Float.parseFloat(amount);
        int cartId = Integer.parseInt(id);

        CustomUserDetails user = userDetails.getUser();

        List<Cart> cart = cartInfoRepository.findCartInfosByUserId(user.getId());
        for(Cart item : cart) {
            int number = item.getQuantity();
            int stock = item.getArticle().getQuantity();
            if (number > stock) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        WebClient webclient = WebClient.create(payementServiceUrl);

        JSONObject json = new JSONObject();
        try {
            json = json.put("amount", amount);
        } catch (Exception e) {

        }

        ResponseEntity<String> serverResponse = webclient
                .post()
                .uri("/api/charge")
                .header("token", token)
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
            return new ResponseEntity(HttpStatus.OK);
        }
    }


}
