package com.gebilaoyi.j2ee.lesson9.dao;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsOrderEntity;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.CartGoodsItemModel;
import com.gebilaoyi.j2ee.lesson9.model.GoodsOrderModel;
import com.gebilaoyi.j2ee.lesson9.model.MyAllOrderModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDao {

    @Insert("insert into goods_order(order_id, price, goods_name, goods_id, order_user_id, order_time, count) " +
            "values(#{goodsOrderEntity.orderId},#{goodsOrderEntity.price}, #{goodsOrderEntity.goodsName}, #{goodsOrderEntity.goodsId},#{goodsOrderEntity.orderUserId},#{goodsOrderEntity.orderTime},#{goodsOrderEntity.count})")
    int insert(@Param("goodsOrderEntity") GoodsOrderEntity goodsOrderEntity);


    @Select("select order_id, sum(count) sum " +
            "from goods_order t where order_user_id = #{userId} group by order_id")
    List<MyAllOrderModel> getMyOrderList(@Param("userId") int userId);

    @Select("select * from goods_order t where order_id=#{orderId}")
    List<GoodsOrderModel> getOrders(@Param("orderId") String orderId) ;
}
