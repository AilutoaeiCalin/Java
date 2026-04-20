package com.kapa.lab7.dto;

public class MovieRequest {
    private String title;
    private String releaseDate;
    private int duration;
    private double score;
    private int genreId;

    public MovieRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public double getScore() {
        return score;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}