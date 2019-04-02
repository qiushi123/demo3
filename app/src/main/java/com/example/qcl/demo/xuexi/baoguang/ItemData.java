package com.example.qcl.demo.xuexi.baoguang;

import java.io.Serializable;

/**
 * 2019/4/2 10:47
 * author: qcl
 * desc: recylerview列表的模拟数据
 * wechat:2501902696
 */
public class ItemData implements Serializable {
    private String title;
    private String desc;

    public ItemData() {
    }

    public ItemData(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
