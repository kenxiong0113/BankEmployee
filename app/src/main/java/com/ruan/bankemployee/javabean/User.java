package com.ruan.bankemployee.javabean;

import cn.bmob.v3.BmobUser;

/**
 * @author by ruan on 2017/12/15.
 */

public class User extends BmobUser{
    private String name;
    private String sex;
    private String bankName;
    private boolean commit;
    private boolean pendingTrial;
    private boolean identityVerified;
    private String age;
    private String officePhone;
    private String number;

    public boolean isPendingTrial() {
        return pendingTrial;
    }

    public void setPendingTrial(boolean pendingTrial) {
        this.pendingTrial = pendingTrial;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isIdentityVerified() {
        return identityVerified;
    }

    public void setIdentityVerified(boolean identityVerified) {
        this.identityVerified = identityVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
