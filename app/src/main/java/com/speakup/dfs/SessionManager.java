package com.speakup.dfs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String MOBILE = "MOBILE";
    public static final String EMAIL = "EMAIL";
    public static final String ADDRESS = "ADDRESS";
    public static final String ID = "ID";

    public static final String PLATE = "PLATE";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String id){

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.apply();

    }

    public void createSessionEdit(String name, String username, String password, String mobile, String email, String address, String id){

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putString(MOBILE, mobile);
        editor.putString(EMAIL, email);
        editor.putString(ADDRESS, address);
        editor.putString(ID, id);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

//    public HashMap<String, String> getUserDetail2(){
//
//        HashMap<String, String> details = new HashMap<>();
//        details.put(NAME, sharedPreferences.getString(NAME, null));
//        details.put(EMAIL, sharedPreferences.getString(EMAIL, null));
//        details.put(USERNAME, sharedPreferences.getString(USERNAME, null));
//        details.put(ID, sharedPreferences.getString(ID, null));
//
//        return details;
//    }


    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((HomeActivity) context).finish();

    }

}