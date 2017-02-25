package com.chailijun.mtime.data.home;

public class GotoPageBean{

    private String gotoType;
    private boolean isGoH5;

    private ParametersBean parameters;

    private Parameters1Bean parameters1;
    private String url;

    public String getGotoType() {
        return gotoType;
    }

    public void setGotoType(String gotoType) {
        this.gotoType = gotoType;
    }

    public boolean isIsGoH5() {
        return isGoH5;
    }

    public void setIsGoH5(boolean isGoH5) {
        this.isGoH5 = isGoH5;
    }

    public ParametersBean getParameters() {
        return parameters;
    }

    public void setParameters(ParametersBean parameters) {
        this.parameters = parameters;
    }

    public Parameters1Bean getParameters1() {
        return parameters1;
    }

    public void setParameters1(Parameters1Bean parameters1) {
        this.parameters1 = parameters1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ParametersBean {
        private int newId;
        private int movieId;

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getNewId() {
            return newId;
        }

        public void setNewId(int newId) {
            this.newId = newId;
        }
    }

    public static class Parameters1Bean{
        private String newId;
        private String movieId;

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getNewId() {
            return newId;
        }

        public void setNewId(String newId) {
            this.newId = newId;
        }
    }
}
