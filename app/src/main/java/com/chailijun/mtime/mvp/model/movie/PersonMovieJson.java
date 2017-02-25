package com.chailijun.mtime.mvp.model.movie;

import com.chailijun.mtime.mvp.interf.Data;
import com.chailijun.mtime.mvp.model.BaseData;

import java.util.List;

/**
 * 演员相关作品
 */
public class PersonMovieJson implements Data{

    private int id;
    private String image;
    private String name;
    private String year;
    private String rating;
    private String releaseDate;
    private String releaseCountry;
    private int totalCount;

    private List<OfficesBean> offices;

    private List<PersonatesBean> personates;

    private List<AwardsBean> awards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseCountry() {
        return releaseCountry;
    }

    public void setReleaseCountry(String releaseCountry) {
        this.releaseCountry = releaseCountry;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<OfficesBean> getOffices() {
        return offices;
    }

    public void setOffices(List<OfficesBean> offices) {
        this.offices = offices;
    }

    public List<PersonatesBean> getPersonates() {
        return personates;
    }

    public void setPersonates(List<PersonatesBean> personates) {
        this.personates = personates;
    }

    public List<AwardsBean> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardsBean> awards) {
        this.awards = awards;
    }

    public static class OfficesBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PersonatesBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class AwardsBean {
        private String eventName;
        private String year;
        private String awardName;

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getAwardName() {
            return awardName;
        }

        public void setAwardName(String awardName) {
            this.awardName = awardName;
        }
    }
}
