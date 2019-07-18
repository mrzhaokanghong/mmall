package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    //新增商品
    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session , Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //执行添加操作
            return iProductService.saveOrUpdatePorduct(product);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }
    //更新产品销售状态  上架/下架
    @RequestMapping(value = "set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session ,Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.serSaleStatus(productId,status);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }
    //获取商品详情
    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session,Integer productId ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询操作
           return iProductService.manageProductDetail(productId);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }

    //获取商品列表
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse getList (HttpSession session ,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum ,@RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //分页列表  业务操作
            return iProductService.getProductLisr(pageNum,pageSize);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }
    //商品搜索接口
    @RequestMapping(value = "search.do")
    @ResponseBody
    public ServerResponse productSearch (HttpSession session ,String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum ,@RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //分页列表  业务操作
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }

    //后台文件图片上传
    @RequestMapping(value = "upload.do")
    @ResponseBody
    public ServerResponse upload (HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else{
            return  ServerResponse.createByErrorMessage("无权限操作！");
        }
    }
    //富文本文件上传
    @RequestMapping(value = "richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload (HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","用户未登录，请登录管理员！");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求，我们使用的是simditor所以按照simditor要求进行返回
        if (iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败！");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功！");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else{
            resultMap.put("success",false);
            resultMap.put("msg","用户无权限！");
            return resultMap;
        }
    }



}
