package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

public interface ShoppingcartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
