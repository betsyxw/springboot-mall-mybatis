package com.xuwen.javamall.service;


import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.form.ShippingForm;
import com.xuwen.javamall.vo.ResponseVo;

import java.util.Map;

public interface IShippingService {

    /**
     * 返回值类型Map<String,Integer>
     *     入参：uid，表单
     * */
    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm form);

    ResponseVo delete(Integer uid,Integer shippingId);

    ResponseVo update(Integer uid,Integer shippingId,ShippingForm form);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum,Integer pageSize);
}
