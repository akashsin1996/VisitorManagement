package com.sisoft.vm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.sisoft.vm.Admin;
import com.sisoft.vm.MySharedPrefrenced;
import com.sisoft.vm.Staff;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;
import java.util.ArrayList;
import java.util.List;

import static com.sisoft.vm.Database.DBContract.TABLE_STAFF_TABLE;
import static com.sisoft.vm.Database.DBContract.TABLE_VISIT_DETAILS;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME="visitor_management";
    private static int DB_VERSION=1;
    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //user admin
        String TABLE_ADMIN = "CREATE TABLE IF NOT EXISTS "+DBContract.TABLE_ADMIN_USER+"("
                +DBContract.ADMIN_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DBContract.ADMIN_USERNAME+" TEXT,"
                +DBContract.ADMIN_ROLE+" TEXT,"
                +DBContract.ADMIN_FIRSTNAME+" TEXT,"
                +DBContract.ADMIN_LASTNAME+" TEXT,"
                +DBContract.ADMIN_EMAIL+" TEXT,"
                +DBContract.ADMIN_GENDER+ " TEXT,"
                +DBContract.ADMIN_CITY+ " TEXT,"
                +DBContract.ADMIN_MOBILE+" TEXT,"
                +DBContract.ADMIN_DATE+" TEXT,"
                +DBContract.ADMIN_IMAGE+" BLOB,"
                +DBContract.ADMIN_PASSWORD+" TEXT"+
               ")";

        String TABLE_VISITOR = "CREATE TABLE IF NOT EXISTS "+DBContract.TABLE_VISITOR_USER+"("
                +DBContract.VISITOR_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DBContract.VISITOR_VFIRSTNAME+" TEXT,"
                +DBContract.VISITOR_VLASTNAME+" TEXT,"
                +DBContract.VISITOR_VCITY+" TEXT,"
                +DBContract.VISITOR_VEMAIL+" TEXT,"
                +DBContract.VISITOR_ADMIN_ID+" INTEGER,"
                +DBContract.VISITOR_GENDER+" TEXT,"
                +DBContract.VISITOR_IS_DENY +" TEXT,"
                +DBContract.VISITOR_IMAGE+" BLOB,"
                +DBContract.VISITOR_VMOBILE+" TEXT" +
                ")";

        String TABLE_VISIT_DETAILS = "CREATE TABLE IF NOT EXISTS "+DBContract.TABLE_VISIT_DETAILS+"("
                +DBContract.VISIT_DETAILS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DBContract.VISIT_DETAILS_VISITOR_USER_ID+" INTEGER,"
                +DBContract.VISIT_DETAILS_MEET_TO+" TEXT,"
                +DBContract.VISIT_DETAILS_VISIT_DATE+" TEXT,"
                +DBContract.VISIT_DETAILS_ENTRY_TIME +" TEXT,"
                +DBContract.VISIT_DETAILS_EXIT_DATE_TIME +" TEXT,"
                +DBContract.VISIT_DETAILS_PURPOSE +" TEXT,"
                +DBContract.VISIT_DETAILS_STATUS +" TEXT,"
                +DBContract.VISIT_DETAILS_ITEM_CARRIED +" TEXT"+
                ")";


        String TABLE_STAFF_TABLE = "CREATE TABLE IF NOT EXISTS "+ DBContract.TABLE_STAFF_TABLE +"("
                +DBContract.STAFF_TABLE_USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DBContract.STAFF_TABLE_EMP_NAME +" TEXT, "
                +DBContract.STAFF_TABLE_DEPT_NAME +" TEXT, "
                +DBContract.STAFF_TABLE_GENDER+ " TEXT,"
                +DBContract.STAFF_TABLE_DESIGNATION +" TEXT, "
                +DBContract.STAFF_TABLE_EMAIL+" TEXT, "
                +DBContract.STAFF_TABLE_MOBILE +" TEXT "+
                ")";


        String TABLE_VISITOR_OTP = "CREATE TABLE IF NOT EXISTS "+DBContract.TABLE_VISITOR_OTP+"("
                +DBContract.VISITOR_OTP_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DBContract.VISITOR_OTP_MOBILE +" TEXT, "
                +DBContract.VISITOR_OTP_OTP +" TEXT, "
                +DBContract.VISITOR_OTP_GENERATED_DTM +" TEXT "+
                ")";



        db.execSQL(TABLE_VISITOR);
        db.execSQL(TABLE_VISIT_DETAILS);
        db.execSQL(TABLE_STAFF_TABLE);
        db.execSQL(TABLE_VISITOR_OTP);
        db.execSQL(TABLE_ADMIN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insertAdmin(String username,String role, String firstname, String lastname, String email,
                               String city, String mobile, String password, String date, byte[] imageInByte,String gender){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.ADMIN_USERNAME,username);
        cv.put(DBContract.ADMIN_ROLE,role);
        cv.put(DBContract.ADMIN_FIRSTNAME,firstname);
        cv.put(DBContract.ADMIN_LASTNAME,lastname);
        cv.put(DBContract.ADMIN_EMAIL,email);
        cv.put(DBContract.ADMIN_CITY,city);
        cv.put(DBContract.ADMIN_MOBILE,mobile);
        cv.put(DBContract.ADMIN_PASSWORD,password);
        cv.put(DBContract.ADMIN_DATE,date);
        cv.put(DBContract.ADMIN_IMAGE,imageInByte);
        cv.put(DBContract.ADMIN_GENDER,gender);

       return (int) database.insert(DBContract.TABLE_ADMIN_USER,null,cv)>0;

    }

    public  ArrayList<String> getemp_name(){
        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase database = this.getReadableDatabase();
        // Start the transaction.
        database.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM "+ TABLE_STAFF_TABLE;
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    // Add province name to arraylist
                    String sName= cursor.getString(cursor.getColumnIndex("emp_name"));
                    list.add(sName);
                }
            }
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
            // End the transaction.
            database.close();
            // Close database

        }
        return list;
    }


    public boolean updateAdmin(int id, String username,String role, String firstname, String lastname, String email,
                               String city, String mobile, String password, String date, byte[] imageInByte,String gender){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.ADMIN_USERNAME,username);
        cv.put(DBContract.ADMIN_ROLE,role);
        cv.put(DBContract.ADMIN_FIRSTNAME,firstname);
        cv.put(DBContract.ADMIN_LASTNAME,lastname);
        cv.put(DBContract.ADMIN_EMAIL,email);
        cv.put(DBContract.ADMIN_CITY,city);
        cv.put(DBContract.ADMIN_MOBILE,mobile);
        cv.put(DBContract.ADMIN_PASSWORD,password);
        cv.put(DBContract.ADMIN_DATE,date);
        cv.put(DBContract.ADMIN_IMAGE,imageInByte);
        cv.put(DBContract.ADMIN_GENDER,gender);

        String whereby = DBContract.ADMIN_USER_ID+"=?";
        String[] whereArg =new String[] {String.valueOf(id)};

        return (int) database.update(DBContract.TABLE_ADMIN_USER,cv,whereby,whereArg)>0;

    }


    public boolean updateVisitDetails(int id, String exit_date_time){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.VISIT_DETAILS_ID,id);
        cv.put(DBContract.VISIT_DETAILS_EXIT_DATE_TIME,exit_date_time);
        cv.put(DBContract.VISIT_DETAILS_STATUS,"completed");

        String whereby = DBContract.VISIT_DETAILS_ID+"=?";
        String[] whereArg =new String[] {String.valueOf(id)};

        return (int) database.update(DBContract.TABLE_VISIT_DETAILS,cv,whereby,whereArg)>0;

    }




    public int insertVisitor(String vfirstname,String vlastname,String vemail,String vcity,String vmobile,
                               String gender,byte[] imageInByte,int admin_id){

        SQLiteDatabase database =this.getReadableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(DBContract.VISITOR_VFIRSTNAME,vfirstname);
        cv.put(DBContract.VISITOR_VLASTNAME,vlastname);
        cv.put(DBContract.VISITOR_VEMAIL,vemail);
        cv.put(DBContract.VISITOR_VCITY,vcity);
        cv.put(DBContract.VISITOR_VMOBILE,vmobile);
        cv.put(DBContract.VISITOR_GENDER,gender);
        cv.put(DBContract.VISITOR_IMAGE,imageInByte);
        cv.put(DBContract.VISITOR_ADMIN_ID,admin_id);
        cv.put(DBContract.VISITOR_IS_DENY,"false");

        int vid = (int) database.insert(DBContract.TABLE_VISITOR_USER,null,cv);
        return vid ;

    }

    public boolean insertVisitDetails(int visitorID,String meet_to, String visit_date, String purpose, String item_carried,
                                        String entry_time ){

        SQLiteDatabase database =this.getReadableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(DBContract.VISIT_DETAILS_MEET_TO,meet_to);
        cv.put(DBContract.VISIT_DETAILS_VISIT_DATE,visit_date);
        cv.put(DBContract.VISIT_DETAILS_PURPOSE,purpose);
        cv.put(DBContract.VISIT_DETAILS_STATUS,"entry");
        cv.put(DBContract.VISIT_DETAILS_ITEM_CARRIED,item_carried);
        cv.put(DBContract.VISIT_DETAILS_ENTRY_TIME,entry_time);
        cv.put(DBContract.VISIT_DETAILS_VISITOR_USER_ID,visitorID);

        return (int) database.insert(DBContract.TABLE_VISIT_DETAILS,null,cv)>0;

    }


    public boolean insertStaffTable(String emp_name,String dept_name,
                                    String designation,String gender,String email,String mobile){

        SQLiteDatabase database =this.getReadableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(DBContract.STAFF_TABLE_EMP_NAME,emp_name);
        cv.put(DBContract.STAFF_TABLE_DEPT_NAME,dept_name);
        cv.put(DBContract.STAFF_TABLE_DESIGNATION,designation);
        cv.put(DBContract.STAFF_TABLE_GENDER,gender);
        cv.put(DBContract.STAFF_TABLE_EMAIL,email);
        cv.put(DBContract.STAFF_TABLE_MOBILE,mobile);

        return (int) database.insert(TABLE_STAFF_TABLE,null,cv)>0;

    }

    public boolean insertVisitorOtp(String mobile,String otp, String generated_dtm ){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.VISITOR_OTP_MOBILE,mobile);
        cv.put(DBContract.VISITOR_OTP_OTP,otp);
        cv.put(DBContract.VISITOR_OTP_GENERATED_DTM,generated_dtm);

        return (int) database.insert(DBContract.TABLE_VISITOR_OTP,null,cv)>0;
    }

    public boolean updateVisitor(int id, String vfirstname, String vlastname, String vemail, String
            vcity, String vmobile, String str_gender, byte[] imageInByte,String date_and_time,
                                 String deny,String done){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.VISITOR_VFIRSTNAME,vfirstname);
        cv.put(DBContract.VISITOR_VLASTNAME,vlastname);
        cv.put(DBContract.VISITOR_VEMAIL,vemail);
        cv.put(DBContract.VISITOR_VCITY,vcity);
        cv.put(DBContract.VISITOR_VMOBILE,vmobile);
        cv.put(DBContract.VISITOR_GENDER,str_gender);
        cv.put(DBContract.VISITOR_IMAGE,imageInByte);
        cv.put(DBContract.VISITOR_IS_DENY,deny);

        String whereby = DBContract.VISITOR_USER_ID+"=?";
        String[] whereArg =new String[] {String.valueOf(id)};

        return database.update(DBContract.TABLE_VISITOR_USER,cv,whereby,whereArg)>0;

    }


    public boolean updateStaff(int id,String emp_name,String dept_name,
                               String designation,String gender,String email,String mobile){

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBContract.STAFF_TABLE_EMP_NAME,emp_name);
        cv.put(DBContract.STAFF_TABLE_DEPT_NAME,dept_name);
        cv.put(DBContract.STAFF_TABLE_DESIGNATION,designation);
        cv.put(DBContract.STAFF_TABLE_GENDER,gender);
        cv.put(DBContract.STAFF_TABLE_EMAIL,email);
        cv.put(DBContract.STAFF_TABLE_MOBILE,mobile);


        String whereby = DBContract.TABLE_STAFF_TABLE+"=?";
        String[] whereArg =new String[] {String.valueOf(id)};

        return database.update(DBContract.TABLE_STAFF_TABLE,cv,whereby,whereArg)>0;

    }






    public boolean isUserExist(String username,String password){

        boolean isUser;
        String query = "select * from "+DBContract.TABLE_ADMIN_USER+" where "+DBContract.ADMIN_USERNAME+" = '"+username+"' and "+DBContract.ADMIN_PASSWORD+" = '"+password+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        isUser = cursor.moveToFirst();
        cursor.close();
        return isUser;
    }

    public String getUserRole(String username,String password){
        String userRole ;
        String query = "select * from "+DBContract.TABLE_ADMIN_USER+" where "+DBContract.ADMIN_USERNAME+" = '"+username+"' and "+DBContract.ADMIN_PASSWORD+" = '"+password+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        userRole=cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_ROLE));
        cursor.close();
        return userRole;
    }



    public String getDetails(String date){

        String details;
        String query = "select * FROM "+DBContract.TABLE_VISIT_DETAILS+" where "+DBContract.VISIT_DETAILS_VISIT_DATE+"= '"+date+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        details=cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE));
        cursor.close();
        return details;
    }






    public ArrayList<Visitor> getVisitor(){

        ArrayList<Visitor> visitors = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_VISITOR_USER;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Visitor visitor=new Visitor();
                visitor.setId(cursor.getInt(cursor.getColumnIndex(DBContract.VISITOR_USER_ID)));
                visitor.setFirstname(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VFIRSTNAME)));
                visitor.setLastname(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VLASTNAME)));
                visitor.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VEMAIL)));
                visitor.setCity(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VCITY)));
                visitor.setMob(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VMOBILE)));
                visitor.setGender(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_GENDER)));
                visitor.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.VISITOR_IMAGE)));
                visitor.setDeny(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_IS_DENY)));


                visitors.add(visitor);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitors;
    }

    public ArrayList<VisitDetails> getVisitorForDate(String date){

        int id ;
        ArrayList<VisitDetails> visitDetails= new ArrayList<>();
       // String query = "select * from "+DBContract.TABLE_VISITOR_USER;
        String query = "select * FROM "+DBContract.TABLE_VISIT_DETAILS+" where "+DBContract.VISIT_DETAILS_VISIT_DATE+"= '"+date+"'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
               VisitDetails visitDetails1 =new VisitDetails();
               id = cursor.getInt(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ID)) ;
               visitDetails1.setId(id);
               visitDetails1.setVisitor_name(getVisitorName(id));
               visitDetails1.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_PURPOSE)));
               visitDetails1.setMeet_to(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_MEET_TO)));
               visitDetails1.setVisit_date(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE)));
               visitDetails1.setEntry_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ENTRY_TIME)));
               visitDetails1.setExit_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_EXIT_DATE_TIME)));
               visitDetails1.setItem_carried(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ITEM_CARRIED)));

               visitDetails.add(visitDetails1);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitDetails;
    }


    public ArrayList<VisitDetails> getVisitorForPrint(){

        int id ;
        String visitor_mob;
        ArrayList<VisitDetails> visitDetails= new ArrayList<>();
        // String query = "select * from "+DBContract.TABLE_VISITOR_USER;
        String query = "select * FROM "+DBContract.TABLE_VISIT_DETAILS;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                VisitDetails visitDetails1 =new VisitDetails();
                id = cursor.getInt(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ID)) ;
                visitor_mob = cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VMOBILE));
                visitDetails1.setId(id);
                visitDetails1.setVisitor_mob(visitor_mob);
                visitDetails1.setVisitor_name(getVisitorName(id));
                visitDetails1.setVisitor_mob(getVisitorMob(visitor_mob));
                visitDetails1.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_PURPOSE)));
                visitDetails1.setMeet_to(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_MEET_TO)));
                visitDetails1.setVisit_date(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE)));
                visitDetails1.setEntry_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ENTRY_TIME)));
                visitDetails1.setExit_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_EXIT_DATE_TIME)));
                visitDetails1.setItem_carried(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ITEM_CARRIED)));

                visitDetails.add(visitDetails1);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitDetails;
    }





    public Admin getAdminForPsw(String uname) {

//        ArrayList<Admin> admins = new ArrayList<>();
        Admin admin = new Admin();

        // String query = "select * from "+DBContract.TABLE_VISITOR_USER;
        String query1 = "select * FROM " + DBContract.TABLE_ADMIN_USER + " where " + DBContract.ADMIN_USERNAME + "= '" +uname+ "'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor1 = sqLiteDatabase.rawQuery(query1, null);

        if (cursor1.moveToFirst()) {
            while (!cursor1.isAfterLast()) {
                admin.setId(cursor1.getInt(cursor1.getColumnIndex(DBContract.ADMIN_USER_ID)));
                admin.setUsername(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_USERNAME)));
                admin.setFirstname(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_FIRSTNAME)));
                admin.setLastname(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_LASTNAME)));
                admin.setEmail(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_EMAIL)));
                admin.setCity(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_CITY)));
                admin.setMob(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_MOBILE)));
                admin.setPsw(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_PASSWORD)));
                admin.setImageInByte(cursor1.getBlob(cursor1.getColumnIndex(DBContract.ADMIN_IMAGE)));
                admin.setGender(cursor1.getString(cursor1.getColumnIndex(DBContract.ADMIN_GENDER)));

                cursor1.moveToNext();

            }
        }
            cursor1.close();
            return admin;

    }




    public String getVisitorName(int id) {
//        Visitor visitor=new Visitor();
        String fname, lname;
        String name="";
        String query = "Select " + DBContract.VISITOR_VFIRSTNAME + "," + DBContract.VISITOR_VLASTNAME + " from " +
                DBContract.TABLE_VISITOR_USER + " where " + DBContract.VISITOR_USER_ID + " = '" + id + "'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() == 0)
            name = " ";
        if (cursor.moveToFirst()) {
            fname = cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VFIRSTNAME));
            lname = cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VLASTNAME));
            name = fname + " " + lname;

        }
            return name;
    }


    public String getVisitorMob(String visitor_mob) {
//        Visitor visitor=new Visitor();
        String vmob;
        String name="";

        String query = "Select " + DBContract.VISITOR_VMOBILE+" from "+DBContract.TABLE_VISITOR_USER+" where "+DBContract.VISITOR_VMOBILE+" = '"+visitor_mob+"'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() == 0)
            name = " ";
        if (cursor.moveToFirst()) {
            vmob = cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VMOBILE));
           name=vmob;

        }
        return name;
    }





    public ArrayList<Admin> getAdmins(){

        ArrayList<Admin> visitors = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_ADMIN_USER;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Admin admin=new Admin();
                admin.setId(cursor.getInt(cursor.getColumnIndex(DBContract.ADMIN_USER_ID)));
                admin.setUsername(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_USERNAME)));
                admin.setRole(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_ROLE)));
                admin.setFirstname(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_FIRSTNAME)));
                admin.setLastname(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_LASTNAME)));
                admin.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_EMAIL)));
                admin.setCity(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_CITY)));
                admin.setMob(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_MOBILE)));
                admin.setPsw(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_PASSWORD)));
                admin.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.ADMIN_IMAGE)));
                admin.setGender(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_GENDER)));


                visitors.add(admin);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitors;
    }


    public ArrayList<Staff> getStaff(){

        ArrayList<Staff> staffs= new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_STAFF_TABLE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Staff staff=new Staff();
                staff.setId(cursor.getInt(cursor.getColumnIndex(DBContract.STAFF_TABLE_USER_ID)));
                staff.setEmp_name(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_EMP_NAME)));
                staff.setDept_name(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_DEPT_NAME)));
                staff.setGender(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_GENDER)));
                staff.setDesignation(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_DESIGNATION)));
                staff.setMobile(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_MOBILE)));
                staff.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_EMAIL)));

                staffs.add(staff);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return staffs;
    }









    public Visitor getVisitor(String phoneNum){

        Visitor visitor=new Visitor();
        String query = "Select * from "+DBContract.TABLE_VISITOR_USER+" where "+DBContract.VISITOR_VMOBILE+" = '"+phoneNum+"'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
                visitor.setId(cursor.getInt(cursor.getColumnIndex(DBContract.VISITOR_USER_ID)));
                visitor.setFirstname(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VFIRSTNAME)));
                visitor.setLastname(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VLASTNAME)));
                visitor.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VEMAIL)));
                visitor.setCity(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VCITY)));
                visitor.setMob(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_VMOBILE)));
                visitor.setGender(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_GENDER)));
                visitor.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.VISITOR_IMAGE)));
                visitor.setDeny(cursor.getString(cursor.getColumnIndex(DBContract.VISITOR_IS_DENY)));


        }

        cursor.close();


        return visitor;
    }



    public Admin getSingleAdmin(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Admin admin = new Admin();
        String query = "select * from "+DBContract.TABLE_ADMIN_USER+" where "
                +DBContract.ADMIN_USERNAME+" = '"+ MySharedPrefrenced.getInstance(context).getUser() +"' and "+DBContract.ADMIN_PASSWORD
                +" = '"+ MySharedPrefrenced.getInstance(context).getPassword() +"'";

        Log.d("test1","query "+query);

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            Log.d("test1","if condition");
            admin.setId(cursor.getInt(cursor.getColumnIndex(DBContract.ADMIN_USER_ID)));
            admin.setUsername(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_USERNAME)));
            admin.setFirstname(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_FIRSTNAME)));
            admin.setLastname(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_LASTNAME)));
            admin.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_EMAIL)));
            admin.setCity(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_CITY)));
            admin.setMob(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_MOBILE)));
            admin.setPsw(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_PASSWORD)));
            admin.setImageInByte(cursor.getBlob(cursor.getColumnIndex(DBContract.ADMIN_IMAGE)));
            admin.setGender(cursor.getString(cursor.getColumnIndex(DBContract.ADMIN_GENDER)));


        }
        cursor.close();
        return  admin;
    }

    public int getAdminId(String user, String password) {
        int id =0;
        SQLiteDatabase database = this.getReadableDatabase();
        String query="select "+DBContract.ADMIN_USER_ID+" from "+DBContract.TABLE_ADMIN_USER+" where "+DBContract.ADMIN_USERNAME +" = '"
                +user+"' and "+DBContract.ADMIN_PASSWORD+" = '"+password+"'";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    /*public ArrayList<Staff> selectStaff(String select){
        ArrayList<Staff> staffs = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_STAFF_TABLE+" where "+DBContract.STAFF_TABLE_EMP_NAME+" = '"+emp+"'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Staff staff = new Staff();
                staff.setId(cursor.getInt(cursor.getColumnIndex(DBContract.STAFF_TABLE_USER_ID)));
                staff.setEmp_name(cursor.getString(cursor.getColumnIndex(DBContract.STAFF_TABLE_EMP_NAME)));
                staffs.add(staff);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return staffs;
    }
*/


    public ArrayList<VisitDetails> getCompleted(){

        int id ;
        ArrayList<VisitDetails> visitDetails = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_VISIT_DETAILS+" where "+DBContract.VISIT_DETAILS_STATUS+" = '"+"completed"+"'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                VisitDetails visitDetails1 =new VisitDetails();
                id = cursor.getInt(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ID)) ;
                visitDetails1.setId(id);
                visitDetails1.setVisitor_name(getVisitorName(id));
                visitDetails1.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_PURPOSE)));
                visitDetails1.setMeet_to(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_MEET_TO)));
                visitDetails1.setVisit_date(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE)));
                visitDetails1.setEntry_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ENTRY_TIME)));
                visitDetails1.setExit_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_EXIT_DATE_TIME)));
                visitDetails1.setItem_carried(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ITEM_CARRIED)));

                visitDetails.add(visitDetails1);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitDetails;
    }

    public ArrayList<VisitDetails> getCurrent(){

        int id ;
        ArrayList<VisitDetails> visitDetails = new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_VISIT_DETAILS+" where "+DBContract.VISIT_DETAILS_STATUS+" = '"+"entry"+"'";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                VisitDetails visitDetails1 =new VisitDetails();
                id = cursor.getInt(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ID)) ;
                visitDetails1.setId(id);
                visitDetails1.setVisitor_name(getVisitorName(id));
                visitDetails1.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_PURPOSE)));
                visitDetails1.setMeet_to(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_MEET_TO)));
                visitDetails1.setVisit_date(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE)));
                visitDetails1.setEntry_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ENTRY_TIME)));
                visitDetails1.setExit_time(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_EXIT_DATE_TIME)));
                visitDetails1.setItem_carried(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ITEM_CARRIED)));

                visitDetails.add(visitDetails1);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return visitDetails;
    }




    public boolean isVisitorUserExist(String mobile){

        boolean isVisitorUser;
        String query = "select * from "+DBContract.TABLE_VISITOR_USER+" where "+DBContract.VISITOR_VMOBILE+" = '"+mobile+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        isVisitorUser= cursor.moveToFirst();
        cursor.close();
        return isVisitorUser;
    }

    public boolean isVisitorOtpExist(String otp){
        boolean isVisitorOtp;
        String query = "select * from "+ DBContract.TABLE_VISITOR_OTP+"where "+DBContract.VISITOR_OTP_OTP+" = '"+otp+"' ORDER BY DESC''" ;
        SQLiteDatabase database =this.getReadableDatabase();
        Cursor cursor =database.rawQuery(query,null);
        isVisitorOtp= cursor.moveToFirst();
        cursor.close();
        return isVisitorOtp;
    }


    public void updateVisitStatus(int id, String status) {
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try {
            values = new ContentValues();
            values.put("status", status);
            String whereby = DBContract.VISITOR_USER_ID+"=?";
            String[] whereArg =new String[] {String.valueOf(id)};
            db.update(TABLE_VISIT_DETAILS,values,whereby, whereArg);
 //           db.(TABLE_VISIT_DETAILS, null, values);
            // Insert into database successfully.
//            db.setTransactionSuccessful();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }

    }

    public ArrayList<String> getStatus(){
        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM "+ TABLE_VISIT_DETAILS;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    // Add province name to arraylist
                    String sName= cursor.getString(cursor.getColumnIndex("status"));
                    list.add(sName);
                }
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database

        }
        return list;
    }


    public ArrayList<VisitDetails> getVisitDetail(){

        ArrayList<VisitDetails> listVisitDetails= new ArrayList<>();
        String query = "select * from "+DBContract.TABLE_VISIT_DETAILS;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                VisitDetails visitDetails=new VisitDetails();
                visitDetails.setId(cursor.getInt(cursor.getColumnIndex(DBContract.VISIT_DETAILS_ID)));
                visitDetails.setVisit_date(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_VISIT_DATE)));
                visitDetails.setStatus(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_STATUS)));
                visitDetails.setMeet_to(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_MEET_TO)));
                visitDetails.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.VISIT_DETAILS_PURPOSE)));

                listVisitDetails.add(visitDetails);
                cursor.moveToNext();
            }
        }

        cursor.close();


        return listVisitDetails;
    }



}

