package com.lhh.filtrado.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookJson {

    private Long id;

    private String title;

    @JsonIgnore
    private String publicationTimestamp;

    private Author author;

    private Long pages;

    @JsonIgnore
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationTimestamp() {
        return publicationTimestamp;
    }

    public void setPublicationTimestamp(String publicationTimestamp) {
        this.publicationTimestamp = publicationTimestamp;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
