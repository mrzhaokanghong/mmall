package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {
    //新增商品和修改商品
    ServerResponse saveOrUpdatePorduct(Product product);
    //修改商品销售状态
    ServerResponse <String> serSaleStatus(Integer productId,Integer status);
    //查询单个商品信息
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    //查询商品信息并分页显示
    ServerResponse<PageInfo> getProductLisr(int pageNum, int pageSize);
    //商品搜索，显示信息并分页显示
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);
    //前端显示商品信息
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    //前端显示商品信息列表
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);

}
