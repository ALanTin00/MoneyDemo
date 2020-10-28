package com.alan.handsome.module.loans.bean;

public class ReqBase {


    /**
     * gender : 性别（数据字典）
     * marital : 婚姻信息（数据字典）
     * education : 教育信息（数据字典）
     * email : email
     * name : 姓名
     * birthday : 生日
     */

    private int gender;
    private int marital;
    private int education;
    private String email;
    private String name;
    private String birthday;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMarital() {
        return marital;
    }

    public void setMarital(int marital) {
        this.marital = marital;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
