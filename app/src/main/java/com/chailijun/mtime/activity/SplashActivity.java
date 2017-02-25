package com.chailijun.mtime.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.chailijun.mtime.MainActivity;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.location.LocationService;
import com.chailijun.mtime.mvp.model.Advertisement;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;
import com.google.gson.Gson;

import java.io.File;

public class SplashActivity extends com.chailijun.mtime.base.BaseActivity {

    //定位服务
    private LocationService locationService;

    private Handler handler = new Handler();
    private boolean isStartMain = false;
    private boolean isFirst =  false;

//    private boolean isLocation;

    /**
     * 定位回调监听
     */
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //保存定位地址的经纬度
                SPUtil.save(Constants.LATITUDE,location.getLatitude()+"");
                SPUtil.save(Constants.LONGITUDE,location.getLongitude()+"");

//                isLocation = true;
            }else {
                showToast("定位失败");
            }
            locationService.stop();
            handler.postDelayed(() -> isFirst(), 500);
        }
    };

    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(View view) {
        isFirst = SPUtil.getBoolean(Constants.IS_FIRST,true);
        initLocationService();
        applyPermission();
//        handler.postDelayed(() -> isFirst(), 500);
    }

    /**
     * 初始化定位服务
     */
    private void initLocationService() {
        locationService = ((MtimeApp) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
//        locationService.start();
    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    private void isFirst(){
        if (!isStartMain){
            isStartMain = true;
            if (isFirst){
                startActivity(new Intent(SplashActivity.this,LeadActivity.class));
            }else if(!isFirst && isHasAdv()){
                startActivity(new Intent(SplashActivity.this,StartAdvActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
            finish();
        }
    }

    /***************动态权限申请（代码开始处）**********************/

    private static final int BAIDU_READ_PHONE_STATE =100;

    private AlertDialog dialog;

    // 要申请的权限
    private String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 动态权限申请
     */
    private void applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, permissions[0])
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, permissions[1])
                            != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, permissions[2])
                            != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, permissions[3])
                            != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, permissions[4])
                            != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求
//                showDialogTipUserRequestPermission();
                startRequestPermission();
            }else {
                locationService.start();
            }
        }else {
            locationService.start();
        }
    }

    /**
     * 提示用户该请求权限的弹出框
     */
    /*private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("定位权限未获取，是否设置权限？\n否则您将无法获取当前位置")
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false).show();
    }*/

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions,BAIDU_READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BAIDU_READ_PHONE_STATE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    }else {
                        Toast.makeText(this, "权限获取失败，将无法获取当前位置", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    locationService.start();
                }
            }
        }
    }

    /**
     * 提示用户去应用设置界面手动开启权限
     */
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("请在-应用设置-权限-中，允许" + getString(R.string.app_name) + "获取位置权限")
                .setPositiveButton(getString(R.string.setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    /**
     * 跳转到当前应用的设置界面
     */
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, BAIDU_READ_PHONE_STATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BAIDU_READ_PHONE_STATE) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                if (ContextCompat.checkSelfPermission(this, permissions[0])
                        != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, permissions[1])
                                != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, permissions[2])
                                != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, permissions[3])
                                != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, permissions[4])
                                != PackageManager.PERMISSION_GRANTED) {

                    // 如果没有授予该权限，就去提示用户请求
//                    showDialogTipUserRequestPermission();
                    startRequestPermission();
                }else {
                    /*if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }*/
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();


                }
            }
        }

    }

    /*******************动态权限申请（代码结束处）*************************/

    /**
     * 查找是否有开启页广告
     * @return
     */
    private boolean isHasAdv() {

//        return false;//测试版屏蔽开屏页广告，如果打开需同时打开MainActivity中getAdvInfo()
        String advAll = SPUtil.get(Constants.ADV_ALL, "");

        if (!TextUtils.isEmpty(advAll)){
            Advertisement advertisement = new Gson().fromJson(advAll, Advertisement.class);
            if (advertisement.getAdvList() != null && advertisement.getAdvList().size() > 0){
                for (int i = 0; i < advertisement.getAdvList().size(); i++) {
                    if (advertisement.getAdvList().get(i).getType().equals("100")){//开启页广告
                        //判断广告是否过期(endDate:10位unix时间戳)
                        long endDate = advertisement.getAdvList().get(i).getEndDate();
                        if (endDate*1000 > System.currentTimeMillis()){

                            File appDir = Imager.getImageDir(
                                    MtimeApp.getContext().getString(R.string.app_cache));
                            if (appDir.exists()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        isFirst();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
