package com.sisoft.vm;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class VisitDetails implements Parcelable {


    protected VisitDetails(Parcel in) {
        id = in.readInt();
        visitor_id = in.readInt();
        visitor_name = in.readString();
        visitor_mob = in.readString();
        visit_date = in.readString();
        meet_to = in.readString();
        purpose = in.readString();
        item_carried = in.readString();
        entry_time = in.readString();
        exit_time = in.readString();
        status = in.readString();


    }

    public static final Creator<VisitDetails> CREATOR = new Creator<VisitDetails>() {
        @Override
        public VisitDetails createFromParcel(Parcel in) {
            return new VisitDetails(in);
        }

        @Override
        public VisitDetails[] newArray(int size) {
            return new VisitDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(int visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }
    public String getVisitor_mob() {
        return visitor_mob;
    }

    public void setVisitor_mob(String visitor_mob) {
        this.visitor_mob = visitor_mob;
    }


    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getMeet_to() {
        return meet_to;
    }

    public void setMeet_to(String meet_to) {
        this.meet_to = meet_to;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getItem_carried() {
        return item_carried;
    }

    public void setItem_carried(String item_carried) {
        this.item_carried = item_carried;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String[] getVisit_status() {
        return visit_status;
    }

    public static void setVisit_status(String[] visit_status) {
        VisitDetails.visit_status = visit_status;
    }

    public int id;
    public int visitor_id;
    public String visitor_name;
    public String visit_date;
    public String meet_to;
    public String purpose;
    public String item_carried;
    public String entry_time;
    public String exit_time;
    public String status;
    public String visitor_mob;
    public static String[] visit_status = {"New", "Inside", "Completed", "Cancelled"};






    public VisitDetails(int id, int visitor_id, String visitor_name, String visitor_mob ,String visit_date, String meet_to, String purpose, String item_carried, String entry_time, String exit_time, String status) {
        this.id = id;
        this.visitor_id = visitor_id;
        this.visitor_name = visitor_name;
        this.visitor_mob = visitor_mob;
        this.visit_date = visit_date;
        this.meet_to = meet_to;
        this.purpose = purpose;
        this.item_carried = item_carried;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
        this.status = status;
    }

    public VisitDetails() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(visitor_id);
        dest.writeString(visitor_name);
        dest.writeString(visitor_mob);
        dest.writeString(visit_date);
        dest.writeString(meet_to);
        dest.writeString(purpose);
        dest.writeString(item_carried);
        dest.writeString(entry_time);
        dest.writeString(exit_time);
        dest.writeString(status);
    }
}

