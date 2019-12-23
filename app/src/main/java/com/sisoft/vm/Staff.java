package com.sisoft.vm;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Staff implements Parcelable {
    public int id;
    public String emp_name;
    public String dept_name;
    public String gender;
    public String designation;
    public String email;
    public String mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static Creator<Staff> getCREATOR() {
        return CREATOR;
    }

    public Staff(){

    }


    protected Staff(Parcel in) {
        id = in.readInt();
        emp_name = in.readString();
        dept_name = in.readString();
        gender = in.readString();
        designation = in.readString();
        email = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(emp_name);
        dest.writeString(dept_name);
        dest.writeString(gender);
        dest.writeString(designation);
        dest.writeString(email);
        dest.writeString(mobile);
    }
}