package com.chailijun.mtime;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

//import com.antfortune.freeline.FreelineCore;
import com.baidu.mapapi.SDKInitializer;
import com.chailijun.mtime.db.gen.DaoMaster;
import com.chailijun.mtime.db.gen.DaoSession;
import com.chailijun.mtime.location.LocationService;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

public class MtimeApp extends Application{

    private static Context mContext;
    public static MtimeApp instances;
    public static boolean isReload;

    public LocationService locationService;
    public Vibrator mVibrator;

//    private RefWatcher refWatcher;
    private DaoSession mDaoSession;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
//        FreelineCore.init(this);
        mContext = getApplicationContext();
        instances = this;

//        refWatcher = LeakCanary.install(this);

//        initBugtags();

        locationCity();

        initDatabase();

        initLocationSDK();
    }

    /**
     * 初始化百度定位sdk
     */
    private void initLocationSDK() {
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }

    public static MtimeApp getInstances() {
        return instances;
    }

    public static Context getContext() {
        return mContext;
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "city.db", null);
        mDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getmDatabase() {
        return mDatabase;
    }

    //定位当前城市
    private void locationCity() {
        int locationId = SPUtil.getInt(Constants.LOCATION_ID);
        if (locationId == 0){
            SPUtil.saveInt(Constants.LOCATION_ID,292);//此处模拟定位到上海
        }
    }

    /**
     * Bugtags
     */
    /*private void initBugtags() {
        Bugtags.start("ecf3cd14f35c1f8f5025d2562ecbf52f", this, Bugtags.BTGInvocationEventNone);
    }*/

//    public static RefWatcher getRefWatcher(Context context) {
//        MtimeApp application = (MtimeApp) context.getApplicationContext();
//        return application.refWatcher;
//    }
}
