package com.magician.jdbc.helper.templete.model;

import com.magician.jdbc.core.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;

public class PageParamModel {

    private Map<String,Object> param;

    private int currentPage;

    private int pageSize;

    /**
     * 创建一个分页参数对象
     * @param page
     * @param pageSize
     * @return
     */
    public static PageParamModel getPageParamModel(Integer page,Integer pageSize){
        PageParamModel pageParamModel = new PageParamModel();
        pageParamModel.setCurrentPage(page);
        pageParamModel.setPageSize(pageSize);
        return pageParamModel;
    }

    public Map<String,Object> getParam() {
        if(param == null){
            param = new HashMap<>();
        }
        return param;
    }

    public PageParamModel setParam(Object param) {
        if(param == null){
            return this;
        }
        this.param = JSONUtil.toMap(param);
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public PageParamModel setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageParamModel setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
