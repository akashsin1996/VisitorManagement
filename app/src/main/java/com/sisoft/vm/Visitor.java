package com.sisoft.vm;

import android.os.Parcel;
import android.os.Parcelable;

public class Visitor implements Parcelable  {

    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String city;
    public String mob;
    public String reason;
    public String meet_to;
    public String gender;
    public byte[] imageInByte;
    public String entry_date_time;
    public String exit_date_time;
    public int admin_id;
    public String Deny;
    public String done;


    protected Visitor(Parcel in) {
        id = in.readInt();
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        city = in.readString();
        mob = in.readString();
        reason = in.readString();
        meet_to = in.readString();
        gender = in.readString();
        imageInByte = in.createByteArray();
        entry_date_time = in.readString();
        exit_date_time = in.readString();
        admin_id = in.readInt();
        Deny = in.readString();
        done = in.readString();
    }

    public static final Creator<Visitor> CREATOR = new Creator<Visitor>() {
        @Override
        public Visitor createFromParcel(Parcel in) {
            return new Visitor(in);
        }

        @Override
        public Visitor[] newArray(int size) {
            return new Visitor[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getMob() {
        return mob;
    }

    public String getReason() {
        return reason;
    }

    public String getMeet_to() {
        return meet_to;
    }

    public String getGender() {
        return gender;
    }

    public byte[] getImageInByte() {
        return imageInByte;
    }

    public String getEntry_date_time() {
        return entry_date_time;
    }

    public String getExit_date_time() {
        return exit_date_time;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getDeny() {
        return Deny;
    }

    public String getDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setMeet_to(String meet_to) {
        this.meet_to = meet_to;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImageInByte(byte[] imageInByte) {
        this.imageInByte = imageInByte;
    }

    public void setEntry_date_time(String entry_date_time) {
        this.entry_date_time = entry_date_time;
    }

    public void setExit_date_time(String exit_date_time) {
        this.exit_date_time = exit_date_time;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setDeny(String deny) {
        Deny = deny;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public Visitor(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(city);
        dest.writeString(mob);
        dest.writeString(reason);
        dest.writeString(meet_to);
        dest.writeString(gender);
        dest.writeByteArray(imageInByte);
        dest.writeString(entry_date_time);
        dest.writeString(exit_date_time);
        dest.writeInt(admin_id);
        dest.writeString(Deny);
        dest.writeString(done);
    }
}