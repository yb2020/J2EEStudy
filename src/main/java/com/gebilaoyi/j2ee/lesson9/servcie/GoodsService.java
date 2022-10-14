package com.gebilaoyi.j2ee.lesson9.servcie;

import com.gebilaoyi.j2ee.lesson9.dao.GoodsDao;
import com.gebilaoyi.j2ee.lesson9.dao.UserDao;
import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.GoodsPageModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {
    @Resource
    private GoodsDao goodsDao;

    public GoodsPageModel list(int selectTypeId, int currentPage, int pageSize) {
        GoodsPageModel retModel = new GoodsPageModel();
        int offset = (currentPage - 1) * pageSize ;
        List<GoodsEntity> goodsEntityList = goodsDao.findList(selectTypeId, offset, pageSize);
        int count = goodsDao.count(selectTypeId);
        int pages = 0;
        if(count % pageSize == 0) {
            pages = count / pageSize ;
        }else {
            pages = count / pageSize + 1 ;
        }
        retModel.setPages(pages);
        retModel.setGoods(goodsEntityList);
        retModel.setCurrentPage(currentPage);
        retModel.setPageSize(pageSize);
        return retModel;
    }

    public GoodsEntity getById(Integer goodsId) {
        return this.goodsDao.findById(goodsId) ;
    }

    public void updateStorageCount(GoodsEntity goodsEntity) {
        this.goodsDao.updateStorageCount(goodsEntity) ;
    }
}
