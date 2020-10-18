package com.alan.handsome.base.bean;

import java.util.List;

/**
 * 类说明：
 * 作者：qiujialiu
 * 时间：2019/4/16
 */

public class ListResultData<T> {
    private int totalCount;
    private List<T> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
