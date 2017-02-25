package com.chailijun.mtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.mtime.MainActivity;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.*;
import com.chailijun.mtime.customview.Indicator;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends com.chailijun.mtime.base.BaseActivity {

    private ViewPager mViewpager;
    private Indicator mIndicator;
    private ViewPagerAdapter mAdapter;
    private ArrayList<View> viewContainter = new ArrayList<View>();
    private int[] guideRes = {R.layout.lead1,R.layout.lead2,R.layout.lead3,R.layout.lead4};
    private List<City> mCityList;

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
        return R.layout.activity_lead;
    }

    @Override
    public void initView(View view) {
//        initCityData();
        imporDatabase();
        initView();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (Indicator) findViewById(R.id.indicator);

        for (int i = 0; i < guideRes.length; i++) {
            View view = LayoutInflater.from(this).inflate(guideRes[i], null);
            viewContainter.add(view);
        }
        mAdapter = new ViewPagerAdapter();
        mViewpager.setAdapter(mAdapter);
        mIndicator.setIndicatorCount(viewContainter.size());

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //指示器滚动
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 直接导入数据库文件
     */
    private void imporDatabase() {
        //存放数据库的目录
        String dirPath="/data/data/"+getPackageName()+"/databases";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        //数据库文件
        File file = new File(dir, "city.db");

        InputStream is = null;
        FileOutputStream fos = null;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            //加载需要导入的数据库
            is = this.getApplicationContext().getResources().openRawResource(R.raw.city);
            fos = new FileOutputStream(file);
            byte[] buffere=new byte[is.available()];
            is.read(buffere);
            fos.write(buffere);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * json数据-->java对象-->数据库文件
     */
    private void initCityData() {
        /*new Thread(){
            @Override
            public void run() {
                super.run();

            }
        }.start();*/
        InputStream is = null;
        try {
            is = getResources().getAssets().open("cityJson");
            String cityList = getStringFromInputStream(is);
            mCityList = getCityList(cityList);
            saveToDB();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveToDB() {
        if (mCityList != null && mCityList.size() > 0){
            CityDao cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
            List<City> list = cityDao.queryBuilder().list();
            if (list.size() == mCityList.size()){
                for (int i = 0; i < mCityList.size(); i++) {
                    cityDao.update(mCityList.get(i));
                }
            }else {
                for (int i = 0; i < mCityList.size(); i++) {
                    long l = cityDao.insert(mCityList.get(i));
                    Logger.i(LeadActivity.class.getSimpleName(),"insert:"+l);
                }
            }
        }else {
            try {
                throw new Exception("城市列表数据解析出错！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从输入流中获取字符串
     * @param is
     */
    private String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * Json原生解析
     * @param cityList
     * @return
     */
    private List<City> getCityList(String cityList) {
        List<City> cityList1 = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(cityList);
            String p = jsonObject.optString("p");
            JSONArray jsonArray = new JSONArray(p);
            for (int i = 0; i < jsonArray.length(); i++) {
                City city = new City();
                JSONObject cityObject = jsonArray.getJSONObject(i);
                city.setId((long) cityObject.optInt("id"));
                city.setN(cityObject.optString("n"));
                city.setCount(cityObject.optInt("count"));
                city.setPinyinFull(cityObject.optString("pinyinFull"));
                city.setPinyinShort(cityObject.optString("pinyinShort"));
                cityList1.add(city);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityList1;
    }

    public void enterMain(View view){
        SPUtil.save(Constants.IS_FIRST,false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewContainter == null ? 0 : viewContainter.size();
        }

        /**
         * 滑动切换的时候销毁当前的组件
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewContainter.get(position));
        }

        /**
         * 每次滑动的时候生成的组件
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainter.get(position));
            return viewContainter.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
}
