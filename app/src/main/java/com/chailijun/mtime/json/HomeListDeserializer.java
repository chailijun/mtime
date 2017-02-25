package com.chailijun.mtime.json;

import com.chailijun.mtime.data.home.HomeList;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.list_item.FilmReview;
import com.chailijun.mtime.data.home.list_item.GuessMovie;
import com.chailijun.mtime.data.home.list_item.ImageList_51_1;
import com.chailijun.mtime.data.home.list_item.NewMovie;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_0;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_2;
import com.chailijun.mtime.data.home.list_item.TopList;
import com.chailijun.mtime.data.home.list_item.Vote;
import com.chailijun.mtime.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * homePage中新闻item的解析器
 */

public class HomeListDeserializer implements JsonDeserializer<HomeList> {
    @Override
    public HomeList deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        int count = jsonObject.get("count").getAsInt();
        JsonArray data = jsonObject.get("data").getAsJsonArray();

        HomeList homeList = new HomeList();
        homeList.setCount(count);
        List<HomeListSuper> datas = new ArrayList<>();

        Gson gson = new Gson();
        for (JsonElement jsonElement : data) {
            JsonObject jsonObj = jsonElement.getAsJsonObject();
            int type = jsonObj.get("type").getAsInt();
//            Logger.e("TAG","自定义解析器："+type);
            switch (type){
                case -1:
                    NewMovie newMovie = gson.fromJson(jsonElement, NewMovie.class);
                    datas.add(newMovie);
                    break;
                case 51:
                    int dataType = jsonObj.get("dataType").getAsInt();
                    switch (dataType){
                        case 0:
                            ShortNews_51_0 shortNews = gson.fromJson(jsonElement, ShortNews_51_0.class);
                            datas.add(shortNews);
                            break;
                        case 1:
                            ImageList_51_1 imageList = gson.fromJson(jsonElement, ImageList_51_1.class);
                            datas.add(imageList);
                            break;
                        case 2:
                            ShortNews_51_2 shortNews2 = gson.fromJson(jsonElement, ShortNews_51_2.class);
                            datas.add(shortNews2);
                            break;
                        default:
                            break;
                    }

                    break;
                case 336:
                    FilmReview filmReview = gson.fromJson(jsonElement, FilmReview.class);
                    datas.add(filmReview);
                    break;
                case 64:
                    GuessMovie guessMovie = gson.fromJson(jsonElement, GuessMovie.class);
                    datas.add(guessMovie);
                    break;
                case 90:
                    TopList topList = gson.fromJson(jsonElement, TopList.class);
                    datas.add(topList);
                    break;
                case 44:
                    Vote vote = gson.fromJson(jsonElement, Vote.class);
                    datas.add(vote);
                    break;
                default:
                    break;
            }
        }

        homeList.setData(datas);

        return homeList;
    }
}
