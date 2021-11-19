package com.example.amt_demo.utils;

import com.example.amt_demo.model.CartInfo;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {
    
    public static List<CartInfo> mergeList(List<CartInfo> listA, List<CartInfo> listB) {

        List<CartInfo> listC = new ArrayList<>();
        for (CartInfo a : listA) {
            if (listB.contains(a)) {
                listC.add(new CartInfo(a.getCarpet(), a.getQuantity() + findCartInfoQuantityOrZero(listB, a), a.getUser()));
                listB.remove(a);
            }else {
                System.out.println("wtf doesnt contains");
                listC.add(a);
            }

        }
        listC.addAll(listB);
        return listC;
    }

    private static int findCartInfoQuantityOrZero(List<CartInfo> list, CartInfo cartInfo) {
        for (CartInfo c : list) {
            if(cartInfo.equals(c)) {
                return c.getQuantity();
            }

        }
        return 0;

    }

}
