package com.gebilaoyi.j2ee.lesson9.dao;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("<script>" +
            "SELECT count(*) FROM goods" +
            "<if test='selectTypeId != -100 and selectTypeId > 0'>" +
            "WHERE goods_type_id=#{selectTypeId}" +
            "</if>" +
            "</script>")
    int count(@Param("selectTypeId") int selectTypeId);

    List<GoodsEntity> findList(@Param("selectTypeId") int selectTypeId, @Param("offset")int offset, @Param("pageSize") int pageSize);

    @Select("SELECT * from goods where id=#{id}")
    GoodsEntity findById(@Param("id") int id);

    @Update("update goods t set t.storage_count = #{goodsEntity.storageCount} where id = #{goodsEntity.id}")
    void updateStorageCount(@Param("goodsEntity") GoodsEntity goodsEntity);
}
