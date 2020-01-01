package com.jibres.android.utility.translate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.jibres.android.R;

import java.util.HashMap;
import java.util.Map;

public class TransManager {

  public static class Save extends ContextWrapper {
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String SH_PREF_NAME = "ShPerfManager_Payamres";

    @SuppressLint("CommitPrefEdits")
    private Save(Context context) {
      super(context);
      sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
      editor = sharedPreferences.edit();
    }
    public static Save get(Context context) {
      return new Save(context);
    }

    /** App Info */
    public static final String your_phone = "your_phone";
    public static final String phone_number = "phone_number";
    public static final String enter_number = "enter_number";
    public static final String desc_enter = "desc_enter";

    public static final String phone_verification = "phone_verification";
    public static final String enter_the_otp = "enter_the_otp";
    public static final String edit = "edit";
    public static final String resend = "resend";


    public void setTranslate(String your_phones, String phone_numbers, String enter_numbers,
                             String desc_enters, String phone_verifications, String enter_the_otps,
                             String edits, String resends) {
      editor.putString(your_phone, your_phones);
      editor.putString(phone_number,phone_numbers );
      editor.putString(enter_number, enter_numbers);
      editor.putString(desc_enter, desc_enters);
      editor.putString(phone_verification, phone_verifications);
      editor.putString(enter_the_otp, enter_the_otps);
      editor.putString(edit, edits);
      editor.putString(resend,resends );
      editor.apply();
    }

    public Map<String, String> getTranslate() {
      HashMap<String, String> hashMap = new HashMap<>();
      hashMap.put(your_phone, sharedPreferences.getString(your_phone, getString(R.string.your_phone) ));
      hashMap.put(phone_number, sharedPreferences.getString(phone_number, getString(R.string.phone_number) ));
      hashMap.put(enter_number, sharedPreferences.getString(enter_number, getString(R.string.enter_number) ));
      hashMap.put(desc_enter, sharedPreferences.getString(desc_enter, getString(R.string.desc_enter) ));
      hashMap.put(phone_verification, sharedPreferences.getString(phone_verification, getString(R.string.phone_verification) ));
      hashMap.put(enter_the_otp, sharedPreferences.getString(enter_the_otp, getString(R.string.enter_the_otp) ));
      hashMap.put(edit, sharedPreferences.getString(edit, getString(R.string.edit) ));
      hashMap.put(resend, sharedPreferences.getString(resend, getString(R.string.resend)));

      return hashMap;
    }
  }

  public static class Get{
    static Context context;

    public Get(Context context) {
      this.context = context;
    }
    public static String your_phone(){
      return TransManager.Save.get(context).getTranslate().get(TransManager.Save.desc_enter);
    }
  }
}
