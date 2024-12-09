package com.onleetosh.pluralsight.models;

public class Film {

    int filmID;
    String title;
    String description;
    int releaseYear;
    int length;

    public Film(int filmID,
                String title,
                String description,
                int releaseYear,
                int length) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    /**
     * GETTERS methods used to get a value
     * @return
     */
    public int getFilmID() {
        return filmID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getLength() {
        return length;
    }


    /**
     * SETTERS methods used to set a value
     */
    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setLength(int length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return filmID +
                " " + title +
                " " + description +
                " " + releaseYear +
                " " + length;
    }
}

