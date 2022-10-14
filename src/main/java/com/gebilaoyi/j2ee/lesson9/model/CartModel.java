package com.gebilaoyi.j2ee.lesson9.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartModel {
    /**
     * 缓存购物车
     * 结构：key-> 用户id, value->用户购买的商品列表
     */
    private static Map<Integer, List<CartGoodsItemModel>> cartMap = new HashMap<>();

    public static Map<Integer, List<CartGoodsItemModel>> map() {
        return cartMap;
    }
}
