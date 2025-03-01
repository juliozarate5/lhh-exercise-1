package com.lhh.filtrado.dto;

public record BookNew (
        Long id,
        String title,
        String publicationTimestamp,
        Long pages,
        String summary,
        Author author,
        Long wordCount
) {
}