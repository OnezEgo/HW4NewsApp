package com.example.android.newsapplication;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

// a model of the article object to store information

public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article(String aauthor, String atitle, String adescript, String aurl, String aurlToImage, String apublishedAt){
        this.author = aauthor;
        this.title = atitle;
        this.description = adescript;
        this.url = aurl;
        this.urlToImage = aurlToImage;
        this.publishedAt = apublishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
