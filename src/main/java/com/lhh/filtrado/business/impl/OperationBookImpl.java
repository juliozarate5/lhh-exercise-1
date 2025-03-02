package com.lhh.filtrado.business.impl;

import com.lhh.filtrado.business.IOperationBook;
import com.lhh.filtrado.data.IBookData;
import com.lhh.filtrado.data.impl.BookDataImpl;
import com.lhh.filtrado.dto.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author JULIO MARTINEZ
 */
public class OperationBookImpl implements IOperationBook {

    private static final Integer PAGES = 400;

    private static final String HARRY_NAME = "Harry";

    private static final String ROWLING_NAME= "J.K. Rowling";

    private List<Book> BOOKS;

    private IBookData bookData;

    public OperationBookImpl() {
        bookData = new BookDataImpl();
        try {
            BOOKS = bookData.getDataFromJson();
        } catch (IOException e) {
            BOOKS = Collections.emptyList();
            throw new RuntimeException("Error reading Data...");
        }
    }

    @Override
    public List<Book> filterBooksMoreThan400PagesTitleContainingHarry() {
        return BOOKS.stream()
                .filter(book -> book.pages() > PAGES && book.title().contains(HARRY_NAME))
                .toList();
    }

    @Override
    public List<Book> filterBooksWrittenByRowling() {
        return BOOKS.stream()
                .filter(book -> ROWLING_NAME.equals(book.author().name()))
                .toList();
    }

    @Override
    public List<String> listBookTitlesOrderByName() {
        return BOOKS.stream()
                .map(Book::title)
                .sorted()
                .toList();
    }

    @Override
    public Map<Author, Long> listNumberOfBooksByAuthor(){
        return BOOKS.stream()
                .collect(Collectors.groupingBy(Book::author, Collectors.counting()));
    }
    @Override
    public List<Book> booksWithFormatUnixToDate(){
        return BOOKS.stream()
                .map(book -> {
                    String publicationTimestampString = "N/A";
                    if(book.publicationTimestamp() != null) {
                        final long unixTimestamp = Long.parseLong(book.publicationTimestamp());
                        final LocalDate date = toLocalDate(unixTimestamp);
                        publicationTimestampString = date.toString();
                    }
                    return new Book(
                            book.id(),
                            book.title(),
                            publicationTimestampString,
                            book.pages(),
                            book.summary(),
                            book.author()
                    );
                })
                .toList();
    }

    @Override
    public ResultFour pagesAverageAndFindBookMorePagesAndLeastPages() {
        Double averagePages = BOOKS.stream()
                .mapToDouble(Book::pages)
                .average()
                .orElse(0);
        Book bookMorePages =  BOOKS.stream()
                .max(Comparator.comparingLong(Book::pages))
                .orElseThrow(RuntimeException::new);
        Book bookLessPages = BOOKS.stream()
                .min(Comparator.comparingLong(Book::pages))
                .orElseThrow(RuntimeException::new);
        return new ResultFour(
                averagePages, bookMorePages, bookLessPages
        );
    }

    @Override
    public Map<Author, List<BookNew>> groupBooksByAuthor() {
        return BOOKS.stream()
                .map(book -> new BookNew(
                                book.id(),
                                book.title(),
                                book.publicationTimestamp(),
                                book.pages(),
                                book.summary(),
                                book.author(),
                                book.pages() * 250
                            )
                )
                .collect(Collectors.groupingBy(BookNew::author));
    }

    @Override
    public ResultSeven checkDuplicateAuthorsAndFindBooksNotPublicationDate() {
        final Set<String> authorsSet = new HashSet<>();
        final List<String> duplicatedAuthors = BOOKS.stream()
                .map(book -> book.author().name())
                .filter(author -> !authorsSet.add(author))
                .distinct()
                .toList();
        final List<Book> booksNoPublicationsTimestamp = BOOKS.stream()
                .filter(book -> StringUtils.isEmpty(book.publicationTimestamp()))
                .toList();
        return new ResultSeven(duplicatedAuthors, booksNoPublicationsTimestamp);
    }

    @Override
    public List<Book> mostRecentBooks() {
        return booksWithFormatUnixToDate().stream()
                .filter(book -> !"N/A".equals(book.publicationTimestamp()))
                .collect(Collectors.groupingBy(book -> stringToLocalDate(book.publicationTimestamp())))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .orElse(Collections.emptyList());
    }

    @Override
    public void generateJSONWithTitlesAndAuthorsAndExportToCSV() {
        try {
            bookData.convertToJSONAndSaveCSV(BOOKS);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private LocalDate toLocalDate(Long dateUnix) {
        return Instant.ofEpochSecond(dateUnix)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
