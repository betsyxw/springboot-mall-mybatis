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
import java.util.stream.Collectors;
//导入常量包
import static com.xuwen.javamall.consts.MallConst.ROOT_PARENT_ID;

/**
 * author:xuwen
 * Created on 2021/9/7
 */

//数据库，接口的实现类

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        //前端显示,返回数据新集合
        //List<CategoryVo> categoryVoList = new ArrayList<>();
        //数据库中信息,查出来,数据源！！！
        //categories是数据源，是数据库内所有数据
        List<Category> categories = categoryMapper.selectAll();
        //先查出parent_id=0
        //for循环的方式，方式一
//        for (Category category : categories) {
//            if(category.getParentId().equals(ROOT_PARENT_ID)){
//                CategoryVo categoryVo = new CategoryVo();
//                //copy category（source），信息copy去categoryVo（target）
//                BeanUtils.copyProperties(category,categoryVo);
//                categoryVoList.add(categoryVo);
//            }
//        }

        //方式二：lambda+stream
        //e-target,e.我只需要getParentId=0的数据
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .collect(Collectors.toList());

        //查询子目录
        findSubCategory(categoryVoList,categories);


        return ResponseVo.success(categoryVoList);
    }

    //方法
    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categories){
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();

            for (Category category : categories) {
                //如果查到了，说明子目录有信息，设置子目录subCategory,+继续往下查
                if(categoryVo.getId().equals(category.getParentId())){
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                categoryVo.setSubCategories(subCategoryVoList);
            }
        }
    }

    //方法,对象转换
    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }


}