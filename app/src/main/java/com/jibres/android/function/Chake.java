package com.jibres.android.function;

import android.content.Context;
import com.jibres.android.managers.UserManager;

public class Chake {
    public static Boolean userIsAddTemp(Context context){
        return UserManager.getApikey(context) !=null
                && UserManager.getUserCode(context) !=null
                && UserManager.getZonId(context) !=null ;
    }

    public static Boolean userHasMobileNumber(Context context){
        return UserManager.getApikey(context) !=null
                && UserManager.getUserCode(context) !=null
                && UserManager.getZonId(context) !=null
                && UserManager.getMobile(context) !=null ;
    }
}
