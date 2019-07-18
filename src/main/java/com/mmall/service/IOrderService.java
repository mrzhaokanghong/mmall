package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.Map;

public interface IOrderService {
    //支付宝预下单接口
    ServerResponse pay (Long orderNo, Integer userId, String path);
    //支付宝处理回调接口
    ServerResponse aliCallback(Map<String,String> params);
    //查询订单支付状态
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    //创建订单
    ServerResponse createOrder(Integer userId,Integer shippingId);
    //取消订单
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    //查询购物车信息
    public ServerResponse getOrderCartPorduct(Integer userId);
    //订单详情
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    //用户查询自己的订单列表
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);
    //后台查询订单列表
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);
    //后台查询订单详情
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    //后台根据订单号搜索订单接口
    ServerResponse<PageInfo > manageSearch(Long orderNo,int pageNum,int pageSize);
    //发货功能
    ServerResponse<String> manageSendGoods(Long orderNo);
}
