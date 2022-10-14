package com.gebilaoyi.j2ee.lesson9.dao;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsTypeDao {

    @Select("SELECT *\n" +
            "        FROM goods_type order by id ")
    List<GoodsTypeEntity> findList();

}
