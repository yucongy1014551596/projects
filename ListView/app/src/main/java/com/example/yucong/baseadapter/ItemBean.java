package com.example.yucong.baseadapter;

/**
 * 封装实体类
 */
public class ItemBean {
    public int itemImageResId;//图像资源ID
    public String itemTitle;//标题
    public String itemContent;//内容

    public ItemBean(int itemImageResId, String itemTitle, String itemContent) {
        this.itemImageResId = itemImageResId;
        this.itemTitle = itemTitle;
        this.itemContent = itemContent;
    }
}
