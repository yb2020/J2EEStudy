package com.gebilaoyi.j2ee.lesson9.model;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;

import java.util.List;

public class GoodsPageModel {
    private int pages;
    private int pageSize;
    private int currentPage;

    private List<GoodsEntity> goods;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }
}
