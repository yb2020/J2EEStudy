package com.gebilaoyi.j2ee.lesson9.api;

import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.CartGoodsItemModel;
import com.gebilaoyi.j2ee.lesson9.model.CartModel;
import com.gebilaoyi.j2ee.lesson9.model.MyAllOrderModel;
import com.gebilaoyi.j2ee.lesson9.servcie.OrderService;
import com.gebilaoyi.j2ee.lesson9.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 下单
 */
@RestController
@RequestMapping("/order")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @PostMapping("/myOrder")
    public int myOrder(HttpServletRequest request) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return -2;
        }
        List<CartGoodsItemModel> myCartList = CartModel.map().get(userEntity.getId()) ;
        if(myCartList == null || myCartList.size() == 0) {
            return -1;
        }
        if(!orderService.order(myCartList, userEntity)) {
            return -1 ;
        }

        CartModel.map().remove(userEntity.getId()) ;
        return 100 ;
    }

    @GetMapping("/myOrderList")
    public List<MyAllOrderModel> myOrderList(HttpServletRequest request) {
        UserEntity userEntity = UserUtil.getUser(request) ;
        if(userEntity == null) {
            return null;
        }
        return orderService.getMyAllOrderModel(userEntity) ;
    }
}
