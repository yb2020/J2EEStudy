package com.gebilaoyi.j2ee.lesson9.api;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.CartGoodsItemModel;
import com.gebilaoyi.j2ee.lesson9.model.CartModel;
import com.gebilaoyi.j2ee.lesson9.servcie.GoodsService;
import com.gebilaoyi.j2ee.lesson9.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 购物车
 */
@RestController
@RequestMapping("/goods")
public class CartApi {


    @Autowired
    private GoodsService goodsService;

    @PutMapping("/cart/add")
    public boolean add(@RequestParam("goodsId") int goodsId, HttpServletRequest request) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return false;
        }
        List<CartGoodsItemModel> myCartList = CartModel.map().getOrDefault(userEntity.getId(), new ArrayList<>()) ;
        boolean isExist = false;
        for(CartGoodsItemModel cartGoodsItem : myCartList) {
            if(cartGoodsItem.getGoodsId() == goodsId) {
                cartGoodsItem.setCount(cartGoodsItem.getCount() + 1);
                isExist = true;
                break;
            }
        }

        if(!isExist) {
            CartGoodsItemModel cartGoodsItemModel = new CartGoodsItemModel();
            cartGoodsItemModel.setCount(1);
            cartGoodsItemModel.setGoodsId(goodsId);
            myCartList.add(cartGoodsItemModel);
            CartModel.map().put(userEntity.getId(), myCartList);
        }

        return true ;
    }

    @GetMapping("/cart/list")
    public List<CartGoodsItemModel> list(HttpServletRequest request) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return null;
        }
        List<CartGoodsItemModel> list = CartModel.map().get(userEntity.getId()) ;
        if(list == null || list.size() == 0) {
            return null;
        }
        for(CartGoodsItemModel cartGoodsItemModel : list) {
            GoodsEntity goodsEntity = goodsService.getById(cartGoodsItemModel.getGoodsId()) ;
            cartGoodsItemModel.setGoodName(goodsEntity.getGoodName());
            cartGoodsItemModel.setPrice(goodsEntity.getPrice());
            cartGoodsItemModel.setMarketPrice(goodsEntity.getMarketPrice());
            cartGoodsItemModel.setImgUrl(goodsEntity.getImgUrl());
            cartGoodsItemModel.setStorageCount(goodsEntity.getStorageCount());
        }
        return list ;
    }

    @PutMapping("/cart/reduce")
    public int reduce(HttpServletRequest request, int goodsId) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return -1;
        }
        List<CartGoodsItemModel> list = CartModel.map().get(userEntity.getId()) ;
        if(list == null || list.size() == 0) {
            return -1;
        }

        for(CartGoodsItemModel cartGoodsItemModel : list) {
            if(cartGoodsItemModel.getGoodsId() == goodsId && cartGoodsItemModel.getCount() > 1) {
                cartGoodsItemModel.setCount(cartGoodsItemModel.getCount() - 1);
                return cartGoodsItemModel.getCount();
            }
        }
        return -1;
    }

    @PutMapping("/cart/plus")
    public int plus(HttpServletRequest request, int goodsId) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return -1;
        }
        List<CartGoodsItemModel> list = CartModel.map().get(userEntity.getId()) ;
        if(list == null || list.size() == 0) {
            return -1;
        }
        for(CartGoodsItemModel cartGoodsItemModel : list) {
            if(cartGoodsItemModel.getGoodsId() == goodsId && cartGoodsItemModel.getCount() < cartGoodsItemModel.getStorageCount()) {
                cartGoodsItemModel.setCount(cartGoodsItemModel.getCount() + 1);
                return cartGoodsItemModel.getCount();
            }
        }

        return -1;
    }

    @PutMapping("/cart/blur")
    public int blur(HttpServletRequest request, int goodsId, int count) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return -1;
        }
        List<CartGoodsItemModel> list = CartModel.map().get(userEntity.getId()) ;
        if(list == null || list.size() == 0) {
            return -1;
        }
        for(CartGoodsItemModel cartGoodsItemModel : list) {
            if(cartGoodsItemModel.getGoodsId() == goodsId) {
                if(count > 1 && count < cartGoodsItemModel.getStorageCount()) {
                    cartGoodsItemModel.setCount(count);
                }
                return cartGoodsItemModel.getCount();
            }
        }

        return -1;
    }

    @DeleteMapping("/cart/delete")
    public boolean delete(HttpServletRequest request, int goodsId) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return false;
        }
        List<CartGoodsItemModel> list = CartModel.map().get(userEntity.getId()) ;
        if(list == null || list.size() == 0) {
            return false;
        }
        for(CartGoodsItemModel cartGoodsItemModel : list) {
            if(cartGoodsItemModel.getGoodsId() == goodsId) {
                list.remove(cartGoodsItemModel);
                return true;
            }
        }

        return false;
    }
}
