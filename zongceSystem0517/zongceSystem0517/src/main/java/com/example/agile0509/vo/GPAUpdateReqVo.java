package com.example.agile0509.vo;

import lombok.Data;

@Data
public class GPAUpdateReqVo {
    private Integer id;
    private String gpa;
    private String newgpa;
    public Integer getId() {
        return id;
    }
    public String getGpa() {
        return gpa;
    }
    public String getNewgpa() {
        return newgpa;
    }
}
