package com.example.qcl.demo.xuexi.baoguang;

import java.io.Serializable;

/**
 * 2019/4/2 10:47
 * author: qcl
 * imgUrl: recylerview列表的模拟数据
 * wechat:2501902696
 */
public class ItemData implements Serializable {
    private String title;
    private String imgUrl;

    public ItemData() {
    }

    public ItemData(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
