package com.magician.jdbc.helper.templete.model;

import java.io.Serializable;
import java.util.List;

/**
 * Paging data return format
 * @param <T>
 */
public class PageModel<T> implements Serializable {

    private static final long serialVersionUID = -5979851513918558698L;

    /**
     * current page
     */
    private Integer currentPage;

    /**
     * page size
     */
    private Integer pageSize;

    /**
     * page count
     */
    private Integer pageCount;

    /**
     * page total
     */
    private Integer pageTotal;

    /**
     * current page data
     */
    private List<T> dataList;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
