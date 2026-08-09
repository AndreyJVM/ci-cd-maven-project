package org.example.dto;

import org.example.entity.Link;

public class LinkResponse {

    private String shortUrl;
    private String originalUrl;
    private int clickCount;

    public LinkResponse(Link link, String baseUrl) {
        this.shortUrl = baseUrl + "/" + link.getShortCode();
        this.originalUrl = link.getOriginalUrl();
        this.clickCount = link.getClickCount();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public int getClickCount() {
        return clickCount;
    }
}