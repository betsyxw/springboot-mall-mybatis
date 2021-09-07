package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.CategoryMapper;
import com.xuwen.javamall.pojo.Category;
import com.xuwen.javamall.service.ICategoryService;
import com.xuwen.javamall.vo.CategoryVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//导入常量包
import static com.xuwen.javamall.consts.MallConst.ROOT_PARENT_ID;

/**
 * author:xuwen
 * Created on 2021/9/7
 */

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        //注入数据库dao包mapper内容
        List<Category> categories = categoryMapper.selectAll();
        //先查出parent_id=0
        for (Category category : categories) {
            if(category.getParentId().equals(ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                categoryVoList.add(categoryVo);

            }
        }
        return ResponseVo.success(categoryVoList);
    }



}
