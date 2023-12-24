package com.xxxx.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.seckill.pojo.Goods;
import com.xxxx.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    //获取商品列表
    List<GoodsVo> findGoodsVo();
    //获取商品详情
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
