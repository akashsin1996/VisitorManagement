package com.sisoft.vm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefrenced {

    private static SharedPreferences sharedPreferences;
    private static MySharedPrefrenced mySharedPrefrenced;
    private static final String SharedPrefName = "sharedPref";
    private static final String USER_NAME ="user_name";
    private static final String PASSWORD ="password";
    private static final String VMobile="mobile";
    private static final String Role="role";



    private MySharedPrefrenced(Context context){

        if(sharedPreferences ==null){
            sharedPreferences = context.getSharedPreferences(SharedPrefName, Activity.MODE_PRIVATE);
        }
    }


    public static MySharedPrefrenced getInstance(Context context){
        if(mySharedPrefrenced ==null){
            mySharedPrefrenced = new MySharedPrefrenced(context);
        }

        return mySharedPrefrenced;
    }

    //for user

    public  void setUser(String user){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_NAME,user);
        editor.apply();
    }

    public void removeUser(){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(USER_NAME);
        editor.apply();
    }


    public  void setRole(String role){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Role,role);
        editor.apply();
    }

    public void removeRole(){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(Role);
        editor.apply();
    }

    public String getRole(){
        return sharedPreferences.getString(Role,"");
    }

    public String getUser(){
        return sharedPreferences.getString(USER_NAME,"");
    }




    //password


    public  void setPassword(String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(PASSWORD,password);
        editor.apply();
    }

    public  void setMobile(String mobile){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(VMobile,mobile);
        editor.apply();
    }
    public String getVMobile(){
        return sharedPreferences.getString(VMobile,"");
    }


    public String getPassword(){
        return sharedPreferences.getString(PASSWORD,"");
    }

    public void removePassword(){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(PASSWORD);
        editor.apply();
    }

}
