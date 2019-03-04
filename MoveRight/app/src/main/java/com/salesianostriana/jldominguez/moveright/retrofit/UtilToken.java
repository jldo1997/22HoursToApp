package com.salesianostriana.jldominguez.moveright.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.jldominguez.moveright.R;
import com.salesianostriana.jldominguez.moveright.model.User;

public class UtilToken {

    public static void setToken(Context mContext, String token) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.jwt), token);
        editor.commit();
    }


    public static String getToken(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String jwt = sharedPreferences
                .getString(mContext.getString(R.string.jwt), null);

        return jwt;
    }

    public static void setUserData(Context mContext, String userId, String pict, String username, String email, String role){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.sharedpreferences_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.userId), userId);
        editor.putString(mContext.getString(R.string.pict), pict);
        editor.putString(mContext.getString(R.string.username), username);
        editor.putString(mContext.getString(R.string.userEmail), email);
        editor.putString(mContext.getString(R.string.role), role);
        editor.commit();
    }

    public static String getUserId(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String s = sharedPreferences
                .getString(mContext.getString(R.string.userId), null);

        return s;
    }

    public static String getPict(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String s = sharedPreferences
                .getString(mContext.getString(R.string.pict), null);

        return s;
    }

    public static String getUsername(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String s = sharedPreferences
                .getString(mContext.getString(R.string.username), null);

        return s;
    }

    public static String getUserEmail(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String s = sharedPreferences
                .getString(mContext.getString(R.string.userEmail), null);

        return s;
    }

    public static String getUserRole(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String s = sharedPreferences
                .getString(mContext.getString(R.string.role), null);

        return s;
    }

}
