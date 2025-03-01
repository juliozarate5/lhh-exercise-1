package com.lhh.filtrado.dto;

import java.util.List;

public record ResultSeven(List<String> duplicatedAuthors, List<Book> booksNoPublicationsTimestamp){
}
