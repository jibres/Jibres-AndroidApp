package com.jibres.android.function;

import android.content.Context;
import com.jibres.android.managers.AppManager;

public class Chake {
    public static Boolean userIsAddTemp(Context context){
        return AppManager.getApikey(context) !=null
                && AppManager.getUserCode(context) !=null
                && AppManager.getZonId(context) !=null ;
    }

    public static Boolean userHasMobileNumber(Context context){
        return AppManager.getApikey(context) !=null
                && AppManager.getUserCode(context) !=null
                && AppManager.getZonId(context) !=null
                && AppManager.getMobile(context) !=null ;
    }
}
