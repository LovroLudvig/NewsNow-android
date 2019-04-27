package com.lovroludvig.newsnow.networking;

import com.lovroludvig.newsnow.entities.Article;
import com.squareup.moshi.Json;

import java.util.List;

public class GenericResponse {
    @Json(name = "status")
    private String status;

    @Json(name = "totalResults")
    private Long totalResults;

    @Json(name = "articles")
    private List<Article> articles;

    public GenericResponse(String status, Long totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
