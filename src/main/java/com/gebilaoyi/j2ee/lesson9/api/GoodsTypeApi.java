package com.gebilaoyi.j2ee.lesson9.api;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsTypeEntity;
import com.gebilaoyi.j2ee.lesson9.model.GoodsPageModel;
import com.gebilaoyi.j2ee.lesson9.servcie.GoodsService;
import com.gebilaoyi.j2ee.lesson9.servcie.GoodsTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类型api
 */
@RestController
@RequestMapping("/goods/type")
public class GoodsTypeApi {

    @Resource
    private GoodsTypeService goodsTypeService;

    @GetMapping("/list")
    public List<GoodsTypeEntity> list() {
        return goodsTypeService.getGoodsTypeList() ;
    }
}
