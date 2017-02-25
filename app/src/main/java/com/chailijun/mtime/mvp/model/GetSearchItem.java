package com.chailijun.mtime.mvp.model;

import java.io.Serializable;
import java.util.List;

public class GetSearchItem extends BaseData implements Serializable{

    private String code;
    private String showMsg;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        private List<AreaBean> area;

        private List<GenreTypesBean> genreTypes;

        private List<YearsBean> years;

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public List<GenreTypesBean> getGenreTypes() {
            return genreTypes;
        }

        public void setGenreTypes(List<GenreTypesBean> genreTypes) {
            this.genreTypes = genreTypes;
        }

        public List<YearsBean> getYears() {
            return years;
        }

        public void setYears(List<YearsBean> years) {
            this.years = years;
        }

        public static class AreaBean extends BaseData implements Serializable{
            private String name;
            private String subName;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubName() {
                return subName;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }

            @Override
            public String toString() {
                return "AreaBean{" +
                        "name='" + name + '\'' +
                        ", subName='" + subName + '\'' +
                        '}';
            }
        }

        public static class GenreTypesBean extends BaseData implements Serializable{
            private String name;
            private String subName;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubName() {
                return subName;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }

            @Override
            public String toString() {
                return "GenreTypesBean{" +
                        "name='" + name + '\'' +
                        ", subName='" + subName + '\'' +
                        '}';
            }
        }

        public static class YearsBean extends BaseData implements Serializable{
            private String name;
            private String smallName;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSmallName() {
                return smallName;
            }

            public void setSmallName(String smallName) {
                this.smallName = smallName;
            }

            @Override
            public String toString() {
                return "YearsBean{" +
                        "name='" + name + '\'' +
                        ", smallName='" + smallName + '\'' +
                        '}';
            }
        }
    }
}
