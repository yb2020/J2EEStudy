package com.gebilaoyi.j2ee.lesson9.model;

import java.util.List;

public class MyAllOrderModel {
    private String orderId;

    private List<GoodsOrderModel> goodsEntityList;

    private int sum ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<GoodsOrderModel> getGoodsEntityList() {
        return goodsEntityList;
    }

    public void setGoodsEntityList(List<GoodsOrderModel> goodsEntityList) {
        this.goodsEntityList = goodsEntityList;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
