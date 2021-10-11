package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CarpetTests {

    @Test
    public void Carpet_GetSetName() {
        Carpet carpet = new Carpet();
        String name = "Alibaba";
        carpet.setName(name);
        Assertions.assertEquals(name, carpet.getName());
    }

    @Test
    public void Carpet_GetSetDescription() {
        Carpet carpet = new Carpet();
        String desc = "An orient carpet";
        carpet.setDescription(desc);
        Assertions.assertEquals(desc, carpet.getDescription());
    }

    @Test
    public void Carpet_GetSetPrice() {
        Carpet carpet = new Carpet();
        double price = 10000.00;
        carpet.setPrice(price);
        Assertions.assertEquals(price, carpet.getPrice());
    }

    @Test
    public void Carpet_GetSetId() {
        Carpet carpet = new Carpet();
        int id = 10000;
        carpet.setId(id);
        Assertions.assertEquals(id, carpet.getId());
    }

    @Test
    public void Carpet_GetSetImagePath() {
        Carpet carpet = new Carpet();
        String imagePath = "./dummyPath";
        carpet.setImagePath(imagePath);
        Assertions.assertEquals(imagePath, carpet.getImagePath());
    }
}
