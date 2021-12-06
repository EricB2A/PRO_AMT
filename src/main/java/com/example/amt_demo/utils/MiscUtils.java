/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file MiscUtils.java
 *
 * @brief TODO
 */

package com.example.amt_demo.utils;

import com.example.amt_demo.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {

    /**
     *
     * @param listA
     * @param listB
     * @return
     */
    public static List<Cart> mergeList(List<Cart> listA, List<Cart> listB) {

        List<Cart> listC = new ArrayList<>();
        for (Cart a : listA) {
            if (listB.contains(a)) {
                listC.add(new Cart(a.getArticle(), a.getQuantity() + findCartInfoQuantityOrZero(listB, a), a.getUser()));
                listB.remove(a);
            } else {
                System.out.println("wtf doesnt contains");
                listC.add(a);
            }

        }
        listC.addAll(listB);
        return listC;
    }

    /**
     *
     * @param list
     * @param cart
     * @return
     */
    private static int findCartInfoQuantityOrZero(List<Cart> list, Cart cart) {

        for (Cart c : list) {
            if(cart.equals(c)) {
                return c.getQuantity();
            }
        }
        return 0;
    }
}