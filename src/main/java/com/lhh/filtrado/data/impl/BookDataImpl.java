package com.lhh.filtrado.data.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lhh.filtrado.data.IBookData;
import com.lhh.filtrado.dto.Book;
import com.lhh.filtrado.dto.BookJson;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author JULIO MARTINEZ
 */
public class BookDataImpl implements IBookData {

    private static final String NAME_FILE = "books.json";

    private static final String CSV_NAME_FILE = "src/main/resources/books.csv";

    private ObjectMapper objectMapper;

    public BookDataImpl() {
        objectMapper = new ObjectMapper();
    }

    public List<Book> getDataFromJson() throws IOException {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final InputStream is = classloader.getResourceAsStream(NAME_FILE);
        if(is == null) {
            throw new RuntimeException("No Data...");
        }
        return objectMapper.readValue(is, new TypeReference<List<Book>>() {});
    }

    @Override
    public void convertToJSONAndSaveCSV(List<Book> books) throws IOException {
        String json = convertToJSON( books);
        List<BookJson> bookJsons = objectMapper.readValue(json, new TypeReference<List<BookJson>>() {});
        saveBooksToCsv(bookJsons);
    }

    private String convertToJSON(List<Book> books) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(books);
    }

    private void saveBooksToCsv(List<BookJson> bookJsons) throws IOException {
        try (FileWriter writer = new FileWriter(CSV_NAME_FILE)) {
            writer.write("id,title,author_name,pages\n");
            for (BookJson book : bookJsons) {
                String authorName = book.getAuthor().name();
                String authorSurname = StringUtils.isNotEmpty(book.getAuthor().firstSurname())
                        ? book.getAuthor().firstSurname()
                        : "-";
                StringBuilder line = new StringBuilder();
                line.append(book.getId()).append(",");
                line.append("\"").append(book.getTitle()).append("\",");
                line.append("\"").append(authorName).append(" ").append(authorSurname).append("\",");
                line.append(book.getPages()).append("\n");
                writer.write(line.toString());
            }
            System.out.println("Exported file: " + CSV_NAME_FILE);
        }
    }
}
