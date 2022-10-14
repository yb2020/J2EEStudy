package com.gebilaoyi.j2ee.lesson9.servcie;

import com.gebilaoyi.j2ee.lesson9.dao.OrderDao;
import com.gebilaoyi.j2ee.lesson9.dao.UserDao;
import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;
import com.gebilaoyi.j2ee.lesson9.entity.GoodsOrderEntity;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.CartGoodsItemModel;
import com.gebilaoyi.j2ee.lesson9.model.GoodsOrderModel;
import com.gebilaoyi.j2ee.lesson9.model.MyAllOrderModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private GoodsService goodsService;

    public boolean order(List<CartGoodsItemModel> goodsList, UserEntity userEntity) {
        String orderId = UUID.randomUUID().toString();
        for(CartGoodsItemModel cartGoodsItemModel : goodsList) {
            GoodsOrderEntity goodsOrderEntity = new GoodsOrderEntity();
            goodsOrderEntity.setOrderId(orderId);
            goodsOrderEntity.setOrderTime(new Date());
            goodsOrderEntity.setOrderUserId(userEntity.getId());
            goodsOrderEntity.setGoodsId(cartGoodsItemModel.getGoodsId());
            goodsOrderEntity.setCount(cartGoodsItemModel.getCount());
            goodsOrderEntity.setGoodsName(cartGoodsItemModel.getGoodName());
            goodsOrderEntity.setPrice(cartGoodsItemModel.getPrice());
            //减库存
            GoodsEntity goodsEntity = goodsService.getById(cartGoodsItemModel.getGoodsId());
            if(goodsEntity.getStorageCount() < goodsOrderEntity.getCount()) {
                continue;
            }
            goodsEntity.setStorageCount(goodsEntity.getStorageCount() - goodsOrderEntity.getCount());
            goodsService.updateStorageCount(goodsEntity) ;
            orderDao.insert(goodsOrderEntity);
        }
        return true;
    }

    public List<MyAllOrderModel> getMyAllOrderModel(UserEntity userEntity) {
        List<MyAllOrderModel> list = orderDao.getMyOrderList(userEntity.getId()) ;
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        for(MyAllOrderModel myAllOrderModel : list) {
            List<GoodsOrderModel> goodsOrderModelList = orderDao.getOrders(myAllOrderModel.getOrderId()) ;
            myAllOrderModel.setGoodsEntityList(goodsOrderModelList);
        }
        return list;
    }
}
