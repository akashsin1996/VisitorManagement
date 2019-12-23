package com.sisoft.vm.Database;

public class DBContract {

    public static final String TABLE_ADMIN_USER="admin_user";
    public static final String ADMIN_USER_ID="id";
    public static final String ADMIN_USERNAME="username";
    public static final String ADMIN_ROLE="role";
    public static final String ADMIN_FIRSTNAME="firstname";
    public static final String ADMIN_LASTNAME="lastname";
    public static final String ADMIN_EMAIL="email";
    public static final String ADMIN_CITY="city";
    public static final String ADMIN_GENDER="gender";
    public static final String ADMIN_MOBILE="mobile";
    public static final String ADMIN_PASSWORD="password";
    public static final String ADMIN_DATE="date";
    public static final String ADMIN_IMAGE="image";



    public static final String TABLE_VISITOR_USER="visitor_user";
    public static final String VISITOR_USER_ID="visitor_id";
    public static final String VISITOR_VFIRSTNAME="vfirstname";
    public static final String VISITOR_VLASTNAME="vlastname";
    public static final String VISITOR_VEMAIL="vemail";
    public static final String VISITOR_VCITY="vcity";
    public static final String VISITOR_VMOBILE="vmobile";
    public static final String VISITOR_GENDER="gender";
    public static final String VISITOR_ENTRY_TIME ="entry_time";
    public static final String VISITOR_IS_DENY="is_deny";
    public static final String VISITOR_ADMIN_ID="user_id";
    public static final String VISITOR_IMAGE="image";


//    created_dtm
// otp ??
// otp_validation status

    public static final String TABLE_VISIT_DETAILS="visit_details";
    public static final String VISIT_DETAILS_ID="visit_id";
    public static final String VISIT_DETAILS_MEET_TO="meet_to";
    public static final String VISIT_DETAILS_VISIT_DATE="visit_date";
    public static final String VISIT_DETAILS_ENTRY_TIME ="entry_time";
    public static final String VISIT_DETAILS_EXIT_DATE_TIME ="exit_time";
    public static final String VISIT_DETAILS_PURPOSE="purpose";
    public static final String VISIT_DETAILS_ITEM_CARRIED="item_carried";
    public static final String VISIT_DETAILS_STATUS="status";                //Complete, Requested, Refused
    public static final String VISIT_DETAILS_VISITOR_USER_ID="visitorID";


    public static final String TABLE_STAFF_TABLE="staff_table";
    public static final String STAFF_TABLE_USER_ID="id";
    public static final String STAFF_TABLE_EMP_NAME="emp_name";
    public static final String STAFF_TABLE_DEPT_NAME="dept_name";
    public static final String STAFF_TABLE_GENDER="gender";
    public static final String STAFF_TABLE_DESIGNATION="designation";
    public static final String STAFF_TABLE_MOBILE="mobile";
    public static final String STAFF_TABLE_EMAIL="email";



    public static final String TABLE_VISITOR_OTP="visitor_otp";
    public static final String VISITOR_OTP_ID="id";
    public static final String VISITOR_OTP_OTP="otp";
    public static final String VISITOR_OTP_MOBILE="mobile";
    public static final String VISITOR_OTP_GENERATED_DTM="generated_dtm";



}
