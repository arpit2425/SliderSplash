package com.example.slidersplash;

import android.content.Context;
import android.content.SharedPreferences;

public class Intromanager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public Intromanager(Context context) {
        this.context = context;
        pref=context.getSharedPreferences("first",0);
        editor=pref.edit();
    }
    public void setFirst(Boolean isFirst)
    {
      editor.putBoolean("check",isFirst);
      editor.commit();
    }
    public Boolean check()
    {
        return pref.getBoolean("check",true);
    }
}
