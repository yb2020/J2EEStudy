package com.gebilaoyi.j2ee.lesson9.api;

import com.gebilaoyi.j2ee.lesson9.entity.GoodsEntity;
import com.gebilaoyi.j2ee.lesson9.model.GoodsPageModel;
import com.gebilaoyi.j2ee.lesson9.model.UserLoginModel;
import com.gebilaoyi.j2ee.lesson9.servcie.GoodsService;
import com.gebilaoyi.j2ee.lesson9.servcie.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品api
 */
@RestController
@RequestMapping("/goods")
public class GoodsApi {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/list")
    public GoodsPageModel list(@RequestParam("selectTypeId") int selectTypeId, @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        return goodsService.list(selectTypeId, currentPage, pageSize) ;
    }
}
