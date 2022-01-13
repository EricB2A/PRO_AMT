package com.example.amt_demo.service;

import com.example.amt_demo.model.Purchase;
import com.example.amt_demo.model.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {
    private PurchaseRepository purchaseRepository;

    public List<Purchase> findByUser(CustomUserDetails user) {
        return purchaseRepository.findByUser(user.getId());
    }
}
