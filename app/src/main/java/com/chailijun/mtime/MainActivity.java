package com.chailijun.mtime;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.chailijun.mtime.api.ApiConstants;
import com.chailijun.mtime.api.ApiManage;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.customview.CLJDialog;
import com.chailijun.mtime.data.home.HomeIndexJson;
import com.chailijun.mtime.data.home.HomeList;
import com.chailijun.mtime.data.home.list_item.ImageList_51_1;
import com.chailijun.mtime.data.home.list_item.NewMovie;
import com.chailijun.mtime.find.FindFragment;
import com.chailijun.mtime.find.FindPresenter;
import com.chailijun.mtime.homepage.HomeFragment;
import com.chailijun.mtime.user.UserFragment;
import com.chailijun.mtime.homepage.HomePresenter;
import com.chailijun.mtime.json.HomeListDeserializer;
import com.chailijun.mtime.mall.MallFragment;
import com.chailijun.mtime.mall.MallPresenter;
import com.chailijun.mtime.mvp.model.Advertisement;
import com.chailijun.mtime.payticket.PayticketFragment;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private List<Fragment> mFragments;

    public BottomNavigationBar navBar;
    private int currentTabIndex;

    private int locationId;

    private HomePresenter mHomePresenter;
    private MallPresenter mMallPresenter;
    private FindPresenter mFindPresenter;



    @Override
    public void initParms(Bundle parms) {

//        setSteepStatusBar(true);

        mFragments = new ArrayList<>();

        HomeFragment homeFragment = HomeFragment.newInstance("首页");
        PayticketFragment payticketFragment = PayticketFragment.newInstance("购票");
        MallFragment mallFragment = MallFragment.newInstance("商城");
        FindFragment findFragment = FindFragment.newInstance("发现");

        mFragments.add(homeFragment);
        mFragments.add(payticketFragment);
        mFragments.add(mallFragment);
        mFragments.add(findFragment);
        mFragments.add(UserFragment.newInstance("我的"));

        //将Fragment与Presenter绑定
        mHomePresenter = new HomePresenter(homeFragment);
        mMallPresenter = new MallPresenter(mallFragment);
        mFindPresenter = new FindPresenter(findFragment);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        navBar = $(R.id.nav_bar);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
        navBar.setMode(BottomNavigationBar.MODE_FIXED);
        navBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        navBar.addItem(new BottomNavigationItem(R.drawable.tab_home02, R.string.homepage)
                .setActiveColorResource(R.color.colorHome)
                .setInactiveIconResource(R.drawable.tab_home01)
                .setInActiveColorResource(R.color.colorHome2))
                .addItem(new BottomNavigationItem(R.drawable.tab_payticket02, R.string.payticket)
                        .setActiveColorResource(R.color.colorHome)
                        .setInactiveIconResource(R.drawable.tab_payticket01)
                        .setInActiveColorResource(R.color.colorHome2))
                .addItem(new BottomNavigationItem(R.drawable.tab_shop02, R.string.mall)
                        .setActiveColorResource(R.color.colorHome)
                        .setInactiveIconResource(R.drawable.tab_shop01)
                        .setInActiveColorResource(R.color.colorHome2))
                .addItem(new BottomNavigationItem(R.drawable.tab_discover02, R.string.find)
                        .setActiveColorResource(R.color.colorHome)
                        .setInactiveIconResource(R.drawable.tab_discover01)
                        .setInActiveColorResource(R.color.colorHome2))
                .addItem(new BottomNavigationItem(R.drawable.tab_user02, R.string.user)
                        .setActiveColorResource(R.color.colorHome)
                        .setInactiveIconResource(R.drawable.tab_user01)
                        .setInActiveColorResource(R.color.colorHome2))
                .setFirstSelectedPosition(0)
                .initialise();

        navBar.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                super.onTabSelected(position);
                if (currentTabIndex != position) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(mFragments.get(currentTabIndex));
                    if (!mFragments.get(position).isAdded()) {
                        trx.add(R.id.layFrame, mFragments.get(position));
                    }
                    trx.show(mFragments.get(position)).commit();
                }
                currentTabIndex = position;
            }
        });

        showFragment(mFragments.get(0));

        getAdvInfo();

//        Logger.e("TAG","json测试开始");
//        testJson();
//        Logger.e("TAG","json测试结束");

//        testToJson();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void testToJson() {
        HomeIndexJson homeIndexJson = new HomeIndexJson();
        homeIndexJson.setCode("1");
        homeIndexJson.setMsg("成功");

        HomeIndexJson.DataBean dataBean = new HomeIndexJson.DataBean();
        dataBean.setSearchBarDescribe("影片/影人/影院，任你搜");

        List<HomeIndexJson.DataBean.TopPostersBean> topPostersBeanList = new ArrayList<>();

        HomeIndexJson.DataBean.TopPostersBean topPostersBean = new HomeIndexJson.DataBean.TopPostersBean();
        topPostersBean.setImg("http://chailijun.oss-cn-shanghai.aliyuncs.com/mg/2017/01/084455.59735412.jpg");
        topPostersBean.setId("1564984");
        topPostersBean.setGotoType("gotonews");
        topPostersBeanList.add(topPostersBean);

        HomeIndexJson.DataBean.TopPostersBean topPostersBean2 = new HomeIndexJson.DataBean.TopPostersBean();
        topPostersBean2.setImg("http://chailijun.oss-cn-shanghai.aliyuncs.com/mg/2017/01/084455.59735412.jpg");
        topPostersBean2.setId("1564984");
        topPostersBean2.setGotoType("gotonews");
        topPostersBeanList.add(topPostersBean2);

        HomeIndexJson.DataBean.TopPostersBean topPostersBean3 = new HomeIndexJson.DataBean.TopPostersBean();
        topPostersBean3.setImg("http://chailijun.oss-cn-shanghai.aliyuncs.com/mg/2017/01/112105.43466061.jpg");
        topPostersBean3.setId("1564909");
        topPostersBean3.setGotoType("gotonews");
        topPostersBeanList.add(topPostersBean3);

        dataBean.setTopPosters(topPostersBeanList);

        homeIndexJson.setData(dataBean);

        Gson gson = new Gson();
        String jsonString = gson.toJson(homeIndexJson);
        Logger.e("TAG","jsonString==="+jsonString);
    }

    private void testJson() {
        String json = "{\n" +
                "    \"count\": 10,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"titleCn\": \"《你会在那里吗？》2016\",\n" +
                "            \"titleEn\": \"法国小说改编韩国奇幻电影\",\n" +
                "            \"rating\": \"6.7\",\n" +
                "            \"tag\": \"日韩新片\",\n" +
                "            \"status\": 2,\n" +
                "            \"content\": \"金允石穿越时空三十年挽救真爱\",\n" +
                "            \"id\": \"231218\",\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"comSpecialObjId\": 23395116,\n" +
                "            \"image\": \"http://img5.mtime.cn/mt/2016/11/17/160910.59564866_1280X720X2.jpg\",\n" +
                "            \"type\": -1\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"《极限特工：终极回归》举办全球首映礼\",\n" +
                "            \"titlesmall\": \"《极限特工：终极回归》全球首映礼\",\n" +
                "            \"publishTime\": 1483802369,\n" +
                "            \"tag\": \"图集\",\n" +
                "            \"id\": 1565199,\n" +
                "            \"images\": [\n" +
                "                {\n" +
                "                    \"gId\": 534169,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》首映礼，主创大合影\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152029.79037466.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152029.79037466_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534174,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》男主角范·迪塞尔\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152107.42188786.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152107.42188786_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534173,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》男主角范·迪塞尔\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152059.95463666.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152059.95463666_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534175,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"范·迪塞尔和歌手尼基·贾姆合作\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152115.15614841.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152115.15614841_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534172,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》女演员迪皮卡·帕度柯妮\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152051.32580765.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152051.32580765_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534171,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》女演员迪皮卡·帕度柯妮\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152044.43298914.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152044.43298914_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534170,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》女演员迪皮卡·帕度柯妮\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152037.17147897.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152037.17147897_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534184,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城举行全球首映礼，女演员妮娜·杜波夫\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152259.49525474.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152259.49525474_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534183,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城举行全球首映礼，女演员妮娜·杜波夫\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152251.90238670.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152251.90238670_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534181,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城举行全球首映礼，女演员露比·罗丝\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152237.26642076.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152237.26642076_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534180,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城举行全球首映礼，女演员露比·罗丝\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152230.57280808.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152230.57280808_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534179,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城举行全球首映礼，女演员露比·罗丝\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152223.55851493.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152223.55851493_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534177,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》首映礼，环球小姐阿里亚德娜·古铁雷斯 \",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152208.26189407.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152208.26189407_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534176,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》首映礼，环球小姐阿里亚德娜·古铁雷斯 \",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152201.21348159.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152201.21348159_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534188,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》在墨西哥城的新闻发布会，主创大合影\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152721.49363534.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152721.49363534_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534194,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》男主角范·迪塞尔\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152755.76225220.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152755.76225220_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534193,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》男主角范·迪塞尔\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152748.92338122.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152748.92338122_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534195,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"范·迪塞尔和歌手尼基·贾姆\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152801.79973923.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152801.79973923_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534196,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》女演员：妮娜·杜波夫\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152806.49164324.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152806.49164324_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534192,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"《极限特工：终极回归》女演员：迪皮卡·帕度柯妮\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152744.23442341.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152744.23442341_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534189,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"导演D·J 卡卢索 \",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152727.28848778.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152727.28848778_900.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"status\": 0,\n" +
                "            \"comSpecialObjId\": 23394608,\n" +
                "            \"dataType\": 1,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 125805,\n" +
                "                    \"name\": \"极限特工：终极回归\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/mt/2017/01/05/105822.16893974_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2017\",\n" +
                "                    \"rating\": \"0.0\",\n" +
                "                    \"scoreCount\": 159,\n" +
                "                    \"releaseDate\": \"2017年2月10日\",\n" +
                "                    \"relaseLocation\": \"中国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 913378,\n" +
                "                    \"name\": \"范·迪塞尔\",\n" +
                "                    \"nameEn\": \"Vin Diesel\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2014/09/01/170748.64755972_1280X720X2.jpg\",\n" +
                "                    \"year\": 1967,\n" +
                "                    \"rating\": 8.9\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1266158,\n" +
                "                    \"name\": \"妮娜·杜波夫\",\n" +
                "                    \"nameEn\": \"Nina Dobrev\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2016/05/04/162943.45619387_1280X720X2.jpg\",\n" +
                "                    \"year\": 1989,\n" +
                "                    \"rating\": 9\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1763914,\n" +
                "                    \"name\": \"露比·罗丝\",\n" +
                "                    \"nameEn\": \"Ruby Rose\",\n" +
                "                    \"image\": \"http://img21.mtime.cn/ph/2010/06/25/094715.29791367_1280X720X2.jpg\",\n" +
                "                    \"year\": 0,\n" +
                "                    \"rating\": 8\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152029.79037466_900.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152107.42188786_900.jpg\",\n" +
                "            \"img3\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/152059.95463666_900.jpg\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 18,\n" +
                "            \"time\": \"2017-1-7 15:46:40\",\n" +
                "            \"content\": \"范迪塞尔现场高歌 主创贴心为女神庆生\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"林心如周五报喜女儿诞生\",\n" +
                "            \"titlesmall\": \"林心如周五报喜女儿诞生\",\n" +
                "            \"publishTime\": 1483799362,\n" +
                "            \"tag\": \"图集\",\n" +
                "            \"id\": 1565195,\n" +
                "            \"images\": [\n" +
                "                {\n" +
                "                    \"gId\": 534167,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"霍建华&amp;林心如女儿的小脚丫的脚印\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143738.48109451.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143738.48109451_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534166,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"两人的婚礼\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143718.33791618.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143718.33791618_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534165,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"林心如婚礼上，“还珠”三美聚齐了，赵薇也生了女儿\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143712.68277354.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143712.68277354_900.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"status\": 0,\n" +
                "            \"comSpecialObjId\": 23394468,\n" +
                "            \"dataType\": 1,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 968122,\n" +
                "                    \"name\": \"林心如\",\n" +
                "                    \"nameEn\": \"Ruby Lin\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2016/01/27/095342.13160528_1280X720X2.jpg\",\n" +
                "                    \"year\": 1976,\n" +
                "                    \"rating\": 8\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1148247,\n" +
                "                    \"name\": \"霍建华\",\n" +
                "                    \"nameEn\": \"Wallace Huo\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2016/01/21/131608.52130498_1280X720X2.jpg\",\n" +
                "                    \"year\": 1979,\n" +
                "                    \"rating\": 8.3\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143738.48109451_900.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143718.33791618_900.jpg\",\n" +
                "            \"img3\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/143712.68277354_900.jpg\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 4,\n" +
                "            \"time\": \"2017-1-7 14:46:59\",\n" +
                "            \"content\": \"开心贴出女儿小脚印 霍建华飞台北陪护\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"罗泓轸或将打造韩国超级英雄电影\",\n" +
                "            \"titlesmall\": \"罗泓轸或将打造韩国超级英雄电影\",\n" +
                "            \"publishTime\": 1483799005,\n" +
                "            \"tag\": \"简讯\",\n" +
                "            \"id\": 1565194,\n" +
                "            \"images\": [\n" +
                "                {\n" +
                "                    \"gId\": 534156,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"2008年公开的真人版电影概念图\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142421.66185182.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142421.66185182_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534157,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"2008年公开的真人版电影概念图\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142423.66668010.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142423.66668010_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534158,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"2008年公开的真人版电影概念图\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142425.61457365.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142425.61457365_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534159,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"2008年公开的真人版电影概念图\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142428.58180971.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142428.58180971_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534160,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"动画电影《机器人跆拳剧场版》海报(1976)\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142429.15017942.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142429.15017942_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534162,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"动画电影《机器人跆拳V4》海报 (1978)\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142439.99369089.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142439.99369089_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534163,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"动画电影《机器人跆拳V3》海报（1977）\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142440.51200359.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142440.51200359_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534164,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"动画电影《机器人跆拳V2》海报（1976）\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142442.33881363.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142442.33881363_900.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"status\": 1,\n" +
                "            \"comSpecialObjId\": 23394466,\n" +
                "            \"dataType\": 1,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 77741,\n" +
                "                    \"name\": \"机器人跆拳V\",\n" +
                "                    \"image\": \"http://img21.mtime.cn/mt/2010/06/22/110611.97283907_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2017\",\n" +
                "                    \"rating\": \"0.0\",\n" +
                "                    \"scoreCount\": 3,\n" +
                "                    \"releaseDate\": \"\",\n" +
                "                    \"relaseLocation\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1330874,\n" +
                "                    \"name\": \"罗泓轸\",\n" +
                "                    \"nameEn\": \"Hong-jin Na\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2016/04/06/100850.86023963_1280X720X2.jpg\",\n" +
                "                    \"year\": 1974,\n" +
                "                    \"rating\": 8.4\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142421.66185182_900.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142423.66668010_900.jpg\",\n" +
                "            \"img3\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/142425.61457365_900.jpg\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 30,\n" +
                "            \"time\": \"2017-1-7 14:34:35\",\n" +
                "            \"content\": \"真人版《机器人跆拳V》项目时隔九年重启\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"克尔斯滕邓斯特将主演AMC新剧\",\n" +
                "            \"titlesmall\": \"克尔斯滕邓斯特将主演AMC新剧\",\n" +
                "            \"publishTime\": 1483789924,\n" +
                "            \"tag\": \"简讯\",\n" +
                "            \"id\": 1565190,\n" +
                "            \"status\": 1,\n" +
                "            \"comSpecialObjId\": 23394075,\n" +
                "            \"dataType\": 0,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 239181,\n" +
                "                    \"name\": \"On Becoming A God\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/mt/1181/239181/239181_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2017\",\n" +
                "                    \"rating\": \"0.0\",\n" +
                "                    \"scoreCount\": 0,\n" +
                "                    \"releaseDate\": \"\",\n" +
                "                    \"relaseLocation\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 929797,\n" +
                "                    \"name\": \"克尔斯滕·邓斯特\",\n" +
                "                    \"nameEn\": \"Kirsten Dunst\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2014/03/14/152510.91171810_1280X720X2.jpg\",\n" +
                "                    \"year\": 1982,\n" +
                "                    \"rating\": 8.6\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1633046,\n" +
                "                    \"name\": \"欧格斯·兰斯莫斯\",\n" +
                "                    \"nameEn\": \"Yorgos Lanthimos\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2015/05/06/091310.98348622_1280X720X2.jpg\",\n" +
                "                    \"year\": 1973,\n" +
                "                    \"rating\": 7.7\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/mg/2017/01/07/115111.94339330.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/mg/2017/01/07/115111.94339330.jpg\",\n" +
                "            \"img3\": \"\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 12,\n" +
                "            \"time\": \"2017-1-7 12:55:04\",\n" +
                "            \"content\": \"\\\"龙虾\\\"导演欧格斯兰斯莫斯执导黑色喜剧\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"《侠盗一号》中国内地首日收7073万\",\n" +
                "            \"titlesmall\": \"《侠盗一号》中国内地首日收7073万\",\n" +
                "            \"publishTime\": 1483786355,\n" +
                "            \"tag\": \"简讯\",\n" +
                "            \"id\": 1565189,\n" +
                "            \"status\": 1,\n" +
                "            \"comSpecialObjId\": 23394074,\n" +
                "            \"dataType\": 0,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 214815,\n" +
                "                    \"name\": \"星球大战外传：侠盗一号\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/mt/2016/12/06/112818.78182251_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2016\",\n" +
                "                    \"rating\": 7.6,\n" +
                "                    \"scoreCount\": 1174,\n" +
                "                    \"releaseDate\": \"2017年1月6日\",\n" +
                "                    \"relaseLocation\": \"中国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 225093,\n" +
                "                    \"name\": \"铁道飞虎\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/mt/2016/12/12/115745.33946817_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2016\",\n" +
                "                    \"rating\": 6,\n" +
                "                    \"scoreCount\": 1513,\n" +
                "                    \"releaseDate\": \"2016年12月23日\",\n" +
                "                    \"relaseLocation\": \"中国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 237503,\n" +
                "                    \"name\": \"情圣\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/mt/2016/12/19/180749.11589052_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2016\",\n" +
                "                    \"rating\": 7.1,\n" +
                "                    \"scoreCount\": 1326,\n" +
                "                    \"releaseDate\": \"2016年12月30日\",\n" +
                "                    \"relaseLocation\": \"中国\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/mg/2017/01/07/111718.53465639.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/mg/2017/01/07/111718.53465639.jpg\",\n" +
                "            \"img3\": \"\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 181,\n" +
                "            \"time\": \"2017-1-7 12:53:02\",\n" +
                "            \"content\": \"周五单日大盘破1亿 《情圣》累计过3亿\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"美国女星弗朗辛约克去世享年80岁\",\n" +
                "            \"titlesmall\": \"美国女星弗朗辛约克去世享年80岁\",\n" +
                "            \"publishTime\": 1483781059,\n" +
                "            \"tag\": \"简讯\",\n" +
                "            \"id\": 1565187,\n" +
                "            \"images\": [\n" +
                "                {\n" +
                "                    \"gId\": 534152,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克在《蝙蝠侠》（1966）第一季中饰演大反派书虫（罗迪·麦克道尔  饰）的情妇\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095122.50309513.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095122.50309513_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534155,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克在《蝙蝠侠》（1966）第一季中饰演大反派书虫（罗迪·麦克道尔  饰）的情妇\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095130.62084991.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095130.62084991_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534151,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克在《蝙蝠侠》（1966）第一季中饰演大反派书虫（罗迪·麦克道尔  饰）的情妇\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095119.64967837.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095119.64967837_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534150,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克客串《迷失太空》（1965）\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095116.69041149.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095116.69041149_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534149,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克客串《迷失太空》（1965）\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095113.41074319.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095113.41074319_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534153,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克与猫王在《乐不可支》（1965）中合作\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095125.76227391.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095125.76227391_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534154,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"弗朗辛·约克在《居家男人》（2000）中出演的尼古拉斯·凯奇的母亲一角\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095127.46906846.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095127.46906846_900.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"status\": 1,\n" +
                "            \"comSpecialObjId\": 23393426,\n" +
                "            \"dataType\": 1,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 14058,\n" +
                "                    \"name\": \"居家男人\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/mt/2014/02/22/232813.75766705_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2000\",\n" +
                "                    \"rating\": 8,\n" +
                "                    \"scoreCount\": 2498,\n" +
                "                    \"releaseDate\": \"2000年12月12日\",\n" +
                "                    \"relaseLocation\": \"美国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 24435,\n" +
                "                    \"name\": \"乐不可支\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/mt/2014/02/23/013056.37973079_1280X720X2.jpg\",\n" +
                "                    \"year\": \"1965\",\n" +
                "                    \"rating\": -1,\n" +
                "                    \"scoreCount\": 14,\n" +
                "                    \"releaseDate\": \"1965年6月30日\",\n" +
                "                    \"relaseLocation\": \"美国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 113039,\n" +
                "                    \"name\": \"蝙蝠侠\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/mt/2015/04/28/162753.42289383_1280X720X2.jpg\",\n" +
                "                    \"year\": \"1966\",\n" +
                "                    \"rating\": -1,\n" +
                "                    \"scoreCount\": 3,\n" +
                "                    \"releaseDate\": \"1966年1月12日\",\n" +
                "                    \"relaseLocation\": \"美国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 162554,\n" +
                "                    \"name\": \"明迪烦事多\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/mt/2012/09/23/233127.29702430_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2012\",\n" +
                "                    \"rating\": 7.8,\n" +
                "                    \"scoreCount\": 17,\n" +
                "                    \"releaseDate\": \"2012年9月25日\",\n" +
                "                    \"relaseLocation\": \"美国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 983708,\n" +
                "                    \"name\": \"弗朗辛·约克\",\n" +
                "                    \"nameEn\": \"Francine York\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/ph/2017/01/07/095537.67422131_1280X720X2.jpg\",\n" +
                "                    \"year\": 1938,\n" +
                "                    \"rating\": -1\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095122.50309513_900.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095130.62084991_900.jpg\",\n" +
                "            \"img3\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/07/095119.64967837_900.jpg\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 25,\n" +
                "            \"time\": \"2017-1-7 10:07:52\",\n" +
                "            \"content\": \"曾出演\\\"枕边故事\\\"\\\"居家男人\\\"和\\\"蝙蝠侠\\\"剧集\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"电影《欢乐喜剧人》曝人物版预告片\",\n" +
                "            \"titlesmall\": \"《欢乐喜剧人》人物版预告片\",\n" +
                "            \"publishTime\": 1483776173,\n" +
                "            \"tag\": \"简讯\",\n" +
                "            \"id\": 1565186,\n" +
                "            \"status\": 1,\n" +
                "            \"comSpecialObjId\": 23393318,\n" +
                "            \"dataType\": 2,\n" +
                "            \"relations\": [\n" +
                "                {\n" +
                "                    \"type\": 1,\n" +
                "                    \"id\": 238020,\n" +
                "                    \"name\": \"欢乐喜剧人\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/mt/2016/12/13/155832.74041160_1280X720X2.jpg\",\n" +
                "                    \"year\": \"2017\",\n" +
                "                    \"rating\": \"0.0\",\n" +
                "                    \"scoreCount\": 86,\n" +
                "                    \"releaseDate\": \"2017年1月28日\",\n" +
                "                    \"relaseLocation\": \"中国\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 900722,\n" +
                "                    \"name\": \"罗温·艾金森\",\n" +
                "                    \"nameEn\": \"Rowan Atkinson\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2014/06/15/095337.58271863_1280X720X2.jpg\",\n" +
                "                    \"year\": 1955,\n" +
                "                    \"rating\": 9.4\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1249054,\n" +
                "                    \"name\": \"郭德纲\",\n" +
                "                    \"nameEn\": \"Degang Guo\",\n" +
                "                    \"image\": \"http://img31.mtime.cn/ph/2016/08/30/160847.94668956_1280X720X2.jpg\",\n" +
                "                    \"year\": 1973,\n" +
                "                    \"rating\": 7.5\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": 2,\n" +
                "                    \"id\": 1858614,\n" +
                "                    \"name\": \"岳云鹏\",\n" +
                "                    \"nameEn\": \"Yunpeng Yue\",\n" +
                "                    \"image\": \"http://img5.mtime.cn/ph/2016/12/14/144629.28266265_1280X720X2.jpg\",\n" +
                "                    \"year\": 1985,\n" +
                "                    \"rating\": 7.1\n" +
                "                }\n" +
                "            ],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/mg/2017/01/06/225858.35195922.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/mg/2017/01/06/225858.35195922.jpg\",\n" +
                "            \"img3\": \"\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 49,\n" +
                "            \"time\": \"2017-1-7 9:48:45\",\n" +
                "            \"content\": \"郭德纲小岳岳本色演出 憨豆先生献终极秀\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"欧美明星一周街拍\",\n" +
                "            \"titlesmall\": \"欧美明星一周街拍\",\n" +
                "            \"publishTime\": 1483779604,\n" +
                "            \"tag\": \"图集\",\n" +
                "            \"id\": 1565172,\n" +
                "            \"images\": [\n" +
                "                {\n" +
                "                    \"gId\": 534046,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"怀孕后的波特曼真是不努力就去die的典型，出镜率比之前二十年加起来都多了吧……右边是怀孕的伊莲娜。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140404.93795033.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140404.93795033_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534040,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"跨年夜，地球对面的卫视也在抢收视率，甚至不惜任由耳返失灵让歌手当众出丑来博眼球，牛姐表示很气但还是要保持微孝，回家全部写入《玛哲》。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140219.47730454.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140219.47730454_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534038,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"穿着衣服都是衣冠楚楚的鲜肉or腊肉，脱了衣服是紧是松就藏不住了。如此看来，倒是年纪最大的捏裆鼻祖沃尔伯格身材保持得最好。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140206.70692758.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140206.70692758_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534037,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"小编的两大长腿男神，一静一动一细一糙，两种风情都很好，不过抖森发际线所呈现出的欲与双腿试比长的态势，还是让人隐隐担心。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140200.57376526.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140200.57376526_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534047,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"这些从肚脐就开叉的长腿，在街拍中总是一道能让维纳斯诞生的美景。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140411.91362155.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140411.91362155_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534130,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"有长腿就有短腿，乔纳斯两兄弟都属于五五身，不过颜值还是够看的。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/150806.83420722.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/150806.83420722_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534045,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"矮个女士的穿衣指南，高跟鞋必备！必备！必备！至于右边的麻辣鸡可以忽略，毕竟上下两围都天赋异禀，一般人驾驭不了。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140355.98473625.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140355.98473625_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534043,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"在寒冬腊月看着芭姐暖暖的夏日风情，隔着屏幕都能感受到一股混着青草香的海风徐来。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140240.35685663.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140240.35685663_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534041,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"好莱坞星光大道又留下了一颗闪亮的星——维奥拉·戴维斯，不仅有大牌好友梅姨助阵，更有《逍遥法外》群星撑台。PS：为黑莲花的死撒花！\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140224.53677229.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140224.53677229_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534042,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"一组冻龄女神，有被玻尿酸冻僵的，有被岁月做成诱人果冻的，有穿着一身春花抗冻的，还有怀孕后不怕冻的。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140232.90812889.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140232.90812889_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534044,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"总是听人说荷兰弟头大，这么一看真是挺大，其实腿也不算短，但禁不住脖子和头太压称。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140348.38326725.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140348.38326725_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534131,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"男人越老反而越是有味道，倒不是说颜值多么令人垂涎，穿衣的品味和一举一动的成熟别有特色。阿诺不愧是终结者，连脚都成这样了还淡定自若，鼓掌！\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/150813.69557530.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/150813.69557530_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534039,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"虐狗时间到，左右就不说了，重点是中间这对。据不完全统计，超模麦克斯韦是小K在2016年交往的第4位女友，作为半路出家的小蕾丝，小K正在向小李看齐。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140214.24283398.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140214.24283398_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534033,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"戴利七公主还是一如既往的纯萌，听说和未婚夫将在今年完婚，还准备养一群小孩学跳水。不到23岁戴利就计划着养娃了，快27岁的小编还觉得自己是个娃。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140138.34362714.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140138.34362714_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534036,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"不知道怎么分类了，为了凑个数就暂且把这俩放到一起吧：你出剪刀我出布？\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140154.64517444.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140154.64517444_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534138,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"碧波凉凉怀揣着一颗童心到托儿所给小朋友送关怀，不过据前线报道，小朋友被cos猪鼻日的丁日吓哭了（假的）。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/154932.20368596.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/154932.20368596_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534035,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"哪怕是穿着最低调的黑色，日婆在闪光灯下依旧艳光四射，倒是阿黛尔就秉持着黑色的低调特性。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140147.15591278.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140147.15591278_900.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"gId\": 534034,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"desc\": \"最后奉上高颜值的小贝一家，虽然是渣像素，但朦朦胧胧中也能感受到基因的强大。\",\n" +
                "                    \"url1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140143.51013082.jpg\",\n" +
                "                    \"url2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140143.51013082_900.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"status\": 0,\n" +
                "            \"comSpecialObjId\": 23393317,\n" +
                "            \"dataType\": 1,\n" +
                "            \"relations\": [],\n" +
                "            \"isShow\": \"是\",\n" +
                "            \"img1\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140404.93795033_900.jpg\",\n" +
                "            \"img2\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140219.47730454_900.jpg\",\n" +
                "            \"img3\": \"http://img5.mtime.cn/CMS/Gallery/2017/01/06/140206.70692758_900.jpg\",\n" +
                "            \"type\": 51,\n" +
                "            \"commentCount\": 31,\n" +
                "            \"time\": \"2017-1-7 9:48:15\",\n" +
                "            \"content\": \"抖森秀长腿被发际线抢镜 小K换女友堪比小李\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"7992145\",\n" +
                "            \"nickname\": \"陶醉在电影中的晖\",\n" +
                "            \"comSpecialObjId\": 23393143,\n" +
                "            \"userImage\": \"http://img32.mtime.cn/up/2015/11/20/191013.39960343_128X128.jpg\",\n" +
                "            \"title\": \"是什么彻底摧毁了人性？\",\n" +
                "            \"summaryInfo\": \"影片没有强调谁对谁错，只是告诉我们：困境一直存在，生活一直继续。\",\n" +
                "            \"tag\": \"影评\",\n" +
                "            \"type\": 336,\n" +
                "            \"rating\": \"9.0\",\n" +
                "            \"relatedObj\": {\n" +
                "                \"type\": [\n" +
                "                    \"犯罪\",\n" +
                "                    \"剧情\",\n" +
                "                    \"悬疑\"\n" +
                "                ],\n" +
                "                \"id\": 237852,\n" +
                "                \"title\": \"控方证人\",\n" +
                "                \"name\": \"控方证人\",\n" +
                "                \"titleCn\": \"控方证人\",\n" +
                "                \"titleEn\": \"The Witness for the Prosecution\",\n" +
                "                \"runtime\": \"0分钟\",\n" +
                "                \"url\": \"http://movie.mtime.com/237852/\",\n" +
                "                \"wapUrl\": \"http://movie.wap.mtime.com/237852/\",\n" +
                "                \"rating\": 6.8,\n" +
                "                \"image\": \"http://img5.mtime.cn/mg/2017/01/07/085842.16947621.jpg\",\n" +
                "                \"releaseLocation\": \"英国\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        GsonBuilder gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(HomeList.class, new HomeListDeserializer());
        gsonb.serializeNulls();
        Gson gson = gsonb.create();
        HomeList homeList = gson.fromJson(json, HomeList.class);

        Logger.e("TAG","=================="+homeList.getCount());
        if (homeList.getData().get(0) instanceof NewMovie){
            NewMovie newMovie = (NewMovie) homeList.getData().get(0);
            Logger.e("TAG","=================="+newMovie.getTitleCn());
        }
        if (homeList.getData().get(1) instanceof ImageList_51_1){
            ImageList_51_1 imageList = (ImageList_51_1) homeList.getData().get(1);
            Logger.e("TAG","=================="+imageList.getImages().get(0).getDesc());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.layFrame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();//测试版退出确认
        doublePressBackToQuit();
//        System.exit(0);
    }

    private void doublePressBackToQuit() {
        if (0 != currentTabIndex) {
            navBar.selectTab(0);
            return;
        }
        showExitDialog();
    }

    private void showExitDialog() {
        CLJDialog.Builder builder = new CLJDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_exit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //startActivity(new Intent(MainActivity.this, DummyActivity.class));//解决InputMethodManager内存泄露

                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create().show();
    }

    /**
     * 异步获取广告信息
     * 缓存广告图片，下次直接取缓存图片
     */
    private void getAdvInfo() {
        Logger.e("TAG", "获取广告信息");
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
        if (locationId == 0){
            Logger.e("TAG","当前城市获取失败");
            throw new RuntimeException("当前城市获取失败");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = ApiConstants.Adv_URL + locationId;
                    OkHttpClient client = ApiManage.getOkHttpClient();

                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String adv = response.body().string();
                        Log.e("CLJ", adv);
                        SPUtil.save(Constants.ADV_ALL,adv);
                        cacheAdvImage(adv);
                    } else {
                        Log.e("CLJ", "okHttp is request error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 缓存开启页广告图片
     * @param adv
     */
    private void cacheAdvImage(String adv) {
        Advertisement advertisement = new Gson().fromJson(adv, Advertisement.class);
        if (advertisement.isSuccess()){
            for (int i = 0; i < advertisement.getAdvList().size(); i++) {
                if (advertisement.getAdvList().get(i).getType().equals("100")){//开启页广告

                    String url = advertisement.getAdvList().get(i).getUrl();

                    Imager.downLoad(url,Imager.setImagePath(url));
                }
            }
        }
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;

        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];

            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    }else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了

                    }
                    break;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
