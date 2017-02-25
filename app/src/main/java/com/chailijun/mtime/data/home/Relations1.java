package com.chailijun.mtime.data.home;

/**
 * [type：51  dataType：1 "图集"] 和 [type：51  dataType：0  "简讯" ]
 * 和 [type：51  dataType：2  "简讯"  带有视频标识]
 * 对应的relations字段
 */
@Deprecated
public  class Relations1{

    private int type;
    private int id;
    private String name;
    private String image;
    private String year;
    private String rating;
    private int scoreCount;
    private String releaseDate;
    private String relaseLocation;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRelaseLocation() {
        return relaseLocation;
    }

    public void setRelaseLocation(String relaseLocation) {
        this.relaseLocation = relaseLocation;
    }
}
