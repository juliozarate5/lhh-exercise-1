package com.lhh.filtrado.dto;

public record Book(
        Long id,
        String title,
        String publicationTimestamp,
        Long pages,
        String summary,
        Author author
) {
}
