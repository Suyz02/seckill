package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.SeckillGoods;

import java.util.List;

public interface ISeckillGoodsService extends IService<SeckillGoods> {
    List<SeckillGoods> findSeckillGoods();
}
