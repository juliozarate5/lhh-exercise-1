package com.lhh.filtrado.business;

import com.lhh.filtrado.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IOperationBook {

    /**
     * 1. Filtra los libros con más de 400 páginas y aquellos cuyo título contenga "Harry".
     * @return
     */
    List<Book> filterBooksMoreThan400PagesTitleContainingHarry();

    /**
     * 2. Obtén los libros escritos por "J.K. Rowling".
     * @return
     */
    List<Book> filterBooksWrittenByRowling();

    /**
     * 3. Lista los títulos ordenados alfabéticamente y cuenta cuántos libros ha escrito cada autor.
     * @return
     */
    List<String> listBookTitlesOrderByName();

    Map<Author, Long> listNumberOfBooksByAuthor();

    /**
     * 4. Convierte publicationTimestamp a formato AAAA-MM-DD
     * @return
     */
    List<Book> booksWithFormatUnixToDate();

    /**+
     * 5. Calcula el promedio de páginas y encuentra el libro con más y menos páginas.
     * @return
     */
    ResultFour pagesAverageAndFindBookMorePagesAndLeastPages();

    /**
     * 6. Añade un campo wordCount (250 palabras por página) y agrupa los libros por autor.
     * @return
     */
    Map<Author, List<BookNew>> groupBooksByAuthor();

    /**
     * 7. (Opcional) Verifica si hay autores duplicados y encuentra los libros sin
     * publicationTimestamp
     * @return
     */
    ResultSeven checkDuplicateAuthorsAndFindBooksNotPublicationDate();

    /**
     * 8. (Opcional) Identifica los libros más recientes.
     * @return
     */
    List<Book> mostRecentBooks();

    /**
     * 9. (Opcional) Genera un JSON con títulos y autores y exporta la lista a CSV
     * (id, title, author_name, pages).
     */
    void generateJSONWithTitlesAndAuthorsAndExportToCSV();
}
