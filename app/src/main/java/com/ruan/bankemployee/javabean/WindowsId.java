package com.ruan.bankemployee.javabean;

/**
 * @author by ruan on 2018/1/7.
 * 窗口号
 */

public class WindowsId {
    private int id;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WindowsId(int id ,int img){
        this.id = id;
        this.img = img;
    }
}
