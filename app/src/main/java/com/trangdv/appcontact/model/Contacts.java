package com.trangdv.appcontact.model;

public class Contacts {
    private String mName;
    private String mPhoneNumber;
    private int mId;

    public Contacts() {

    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmId() {
        return mId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmName() {
        return mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }
}
