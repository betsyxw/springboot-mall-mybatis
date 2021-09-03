package com.xuwen.javamall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Integer parentId) {
//        this.parentId = parentId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public Integer getSortOrder() {
//        return sortOrder;
//    }
//
//    public void setSortOrder(Integer sortOrder) {
//        this.sortOrder = sortOrder;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + id +
//                ", parentId=" + parentId +
//                ", name='" + name + '\'' +
//                ", status=" + status +
//                ", sortOrder=" + sortOrder +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                '}';
//    }
}
