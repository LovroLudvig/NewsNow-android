package com.lovroludvig.newsnow.entities;

import com.squareup.moshi.Json;

public class Article {
    @Json(name = "status")
    private String author;

    @Json(name = "totalResults")
    private String title;

    @Json(name = "totalResults")
    private String description;

    @Json(name = "totalResults")
    private String url;

    @Json(name = "totalResults")
    private String urlToImage;

    @Json(name = "totalResults")
    private String publishedAt;

    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Article) {
            if (((Article) obj).getTitle() == null || this.getTitle() == null) {
                return false;
            }
            return this.getTitle().equals(((Article) obj).getTitle());
        } else {
            return false;
        }
    }
}
