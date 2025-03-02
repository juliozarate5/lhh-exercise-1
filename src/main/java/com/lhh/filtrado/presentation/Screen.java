package com.lhh.filtrado.presentation;

import com.lhh.filtrado.business.IOperationBook;
import com.lhh.filtrado.business.impl.OperationBookImpl;
import com.lhh.filtrado.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author JULIO MARTINEZ
 */
public class Screen {

    private IOperationBook operationBook;

    public void getMenu() {
        Scanner keyBoard = new Scanner(System.in);
        operationBook = new OperationBookImpl();
        System.out.println("******************* Welcome Filtering and Processing Books *******************");
        int option = 0;
        do {
            System.out.println("******************* Seleccion a option *******************");
            System.out.println("1. Filtra los libros con más de 400 páginas y aquellos cuyo título contenga \"Harry\"." +
                    "\n2. Obtén los libros escritos por \"J.K. Rowling\"." +
                    "\n3. Lista los títulos ordenados alfabéticamente y cuenta cuántos libros ha escrito cada autor." +
                    "\n4. Convierte publicationTimestamp a formato AAAA-MM-DD." +
                    "\n5. Calcula el promedio de páginas y encuentra el libro con más y menos páginas." +
                    "\n6. Añade un campo wordCount (250 palabras por página) y agrupa los libros por autor" +
                    "\n7. Verifica si hay autores duplicados y encuentra los libros sin publicationTimestamp." +
                    "\n8. Identifica los libros más recientes." +
                    "\n9. Genera un JSON con títulos y autores y exporta la lista a CSV (" +
                    "id, title, author_name, pages)." +
                    "\nOther. Exit.\n");

            option = keyBoard.nextInt();

            switch (option) {
              case 1 -> showFilterBooksMoreThan400PagesTitleContainingHarry();
              case 2 -> showFilterBooksWrittenByRowling();
              case 3 -> showListBookTitlesOrderByNameAndNumbersByAuthor();
              case 4 -> showBooksWithFormatUnixToDate();
              case 5 -> showPagesAverageAndFindBookMorePagesAndLeastPages();
              case 6 -> showGroupBooksByAuthor();
              case 7 -> showCheckDuplicateAuthorsAndFindBooksNotPublicationDate();
              case 8 -> showMostRecentBooks();
              case 9 -> operationBook.generateJSONWithTitlesAndAuthorsAndExportToCSV();
              default -> {
                  System.out.println("Exiting...");
              }
            }
        } while (option >= 1 && option <= 9);

        System.out.println("******************* Good Bye! *******************");
    }


    private void showFilterBooksMoreThan400PagesTitleContainingHarry() {
        final List<Book> books = operationBook.filterBooksMoreThan400PagesTitleContainingHarry();
        showHeader("Libros con más de 400 páginas y aquellos cuyo título contenga \"Harry\". ");
        showBodyIterable(books);
    }

    private void showFilterBooksWrittenByRowling() {
        final List<Book> books = operationBook.filterBooksWrittenByRowling();
        showHeader("Libros escritos por \"J.K. Rowling\".");
        showBodyIterable(books);
    }

    private void showListBookTitlesOrderByNameAndNumbersByAuthor() {
        final List<String> titles = operationBook.listBookTitlesOrderByName();
        final Map<Author, Long> booksByAuthor = operationBook.listNumberOfBooksByAuthor();
        System.out.println("títulos ordenados alfabéticamente y cuenta cuántos libros ha escrito cada autor");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println(titles.toString());
        System.out.println("\n");
        booksByAuthor.entrySet().stream().forEach(entry -> {
            System.out.println("Autor: " + entry.getKey().name() + ", Libros escritos: " + entry.getValue());
        });
        System.out.println("---------------------------------------------------------------------------------------------");
    }


    private void showBooksWithFormatUnixToDate() {
        final List<Book> books = operationBook.booksWithFormatUnixToDate();
        showHeader("Convierte publicationTimestamp a formato AAAA-MM-DD.");
        showBodyIterable(books);
    }

    public void showPagesAverageAndFindBookMorePagesAndLeastPages(){
        ResultFour result = operationBook.pagesAverageAndFindBookMorePagesAndLeastPages();
        System.out.println("Calcula el promedio de páginas y encuentra el libro con más y menos páginas.");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-35s | %-35s | %-35s |\n",
                "Promedio de páginas", "Libro con más páginas", "Libro con menos páginas");
        System.out.printf("| %-35s | %-35s | %-10s |\n",
                result.averagePages(),
                result.bookMorePages().title() + " ("+ result.bookMorePages().pages() +")",
                result.bookLessPages().title() + " ("+ result.bookLessPages().pages() +")");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void showGroupBooksByAuthor() {
        final Map<Author, List<BookNew>> booksByAuthor = operationBook.groupBooksByAuthor();
        System.out.println("Añade un campo wordCount (250 palabras por página) y agrupa los libros por autor.");
        System.out.println("---------------------------------------------------------------------------------------------");
        booksByAuthor.forEach((author, authorBooks) -> {
            System.out.println("Autor: " + author.name());
            authorBooks.forEach(book -> System.out.println("  - " + book.toString()));
            System.out.println();
        });
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void showCheckDuplicateAuthorsAndFindBooksNotPublicationDate() {
        final ResultSeven result = operationBook.checkDuplicateAuthorsAndFindBooksNotPublicationDate();
        System.out.println("(Opcional) Verifica si hay autores duplicados y encuentra los libros sin publicationTimestamp.");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Autores duplicados: " + result.duplicatedAuthors().toString());
        System.out.println();
        System.out.println("Libros sin publicationTimestamp:");
        result.booksNoPublicationsTimestamp()
                .forEach(book -> System.out.println("  - " + book.title()));
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void showMostRecentBooks() {
        final List<Book> books = operationBook.mostRecentBooks();
        showHeader("(Opcional) Identifica los libros más recientes.");
        showBodyIterable(books);
    }


    // *********************************************************************************************************************
    private void showBodyIterable(final List<Book> books) {
        System.out.println("---------------------------------------------------------------------------------------------");
        books.stream().forEach(book -> this.showBody(book));
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void showHeader(String headerTitle) {
        System.out.println("*** " + headerTitle + " ***");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-35s | %-10s | %-5s | %-20s |\n",
                "ID", "Title", "Published", "Pages", "Author");
    }

    private void showBody(final Book book) {
        final String published = (book.publicationTimestamp() == null) ? "N/A" : book.publicationTimestamp();
        System.out.printf("| %-3d | %-35s | %-10s | %-5d | %-20s |\n",
                book.id(), book.title(), published, book.pages(), book.author().name());
    }
}
