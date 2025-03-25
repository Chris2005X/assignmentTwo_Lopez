package com.example.moviesearcher.models;

import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    @SerializedName("Title")
    private final String title;

    @SerializedName("Year")
    private final String year;

    @SerializedName("Rated")
    private final String rating;

    @SerializedName("Poster")
    private final String posterUrl;

    @SerializedName("Plot")
    private final String plot;

    @SerializedName("Production")
    private final String studio;

    @SerializedName("imdbID")
    private final String imdbId;

    protected Movie(Parcel in) {
        title = in.readString();
        year = in.readString();
        rating = in.readString();
        posterUrl = in.readString();
        plot = in.readString();
        studio = in.readString();
        imdbId = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(rating);
        dest.writeString(posterUrl);
        dest.writeString(plot);
        dest.writeString(studio);
        dest.writeString(imdbId);
    }

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getRating() { return rating; }
    public String getPlot() { return plot; }
    public String getPosterUrl() { return posterUrl; }
    public String getStudio() { return studio != null && !studio.equals("N/A") ? studio : "Unknown"; }
    public String getImdbId() { return imdbId; }
}