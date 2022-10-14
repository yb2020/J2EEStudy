package com.gebilaoyi.j2ee.lesson9.servcie;

import com.gebilaoyi.j2ee.lesson9.dao.GoodsTypeDao;
import com.gebilaoyi.j2ee.lesson9.entity.GoodsTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    public List<GoodsTypeEntity> getGoodsTypeList() {
        return this.goodsTypeDao.findList();
    }
}
