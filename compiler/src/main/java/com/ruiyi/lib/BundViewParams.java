package com.ruiyi.lib;

/**
 * Created by liupei on 2018/3/27.
 */

public class BundViewParams {
    private String resId;
    private int rid;
    private String bundViewName;

    public String getResId() {
        return resId;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getBundViewName() {
        return bundViewName;
    }

    public void setBundViewName(String bundViewName) {
        this.bundViewName = bundViewName;
    }
}
