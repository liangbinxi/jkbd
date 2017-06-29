package com.example.lbx.myapplication.bean;

import java.util.List;

/**
 * Created by lbx on 2017/6/29.
 */

public class result {

    /**
     * error_code : 0
     * reason : ok
     * result : []
     */

    private int error_code;
    private String reason;
    private List<?> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
