package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

public interface ICartService {
    //添加购物车功能
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    //修改商品功能
    ServerResponse<CartVo> update (Integer userId,Integer count,Integer productId);
    //删除购物车功能
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);
    //查询购物车功能
    ServerResponse<CartVo> list(Integer userId);
    //全选功能
    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer checked,Integer productId);
    //查询当前用户购物车商品数量
    ServerResponse<Integer>getCartProductCount(Integer userId);

}
