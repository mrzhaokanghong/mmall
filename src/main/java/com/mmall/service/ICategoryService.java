package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    //添加商品分类
    ServerResponse addCategory(String categoryName, Integer parentId);
    //更新商品分类名称
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    //查询子节点的商品分类 信息，并且不递归 保持平级
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    //查询商品本节点的ID及子节点的id
    ServerResponse<List<Integer>>selectCategoryAndChildrenById(Integer categoryId);

}
