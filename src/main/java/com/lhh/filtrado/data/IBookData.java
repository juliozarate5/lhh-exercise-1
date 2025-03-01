package com.lhh.filtrado.data;

import com.lhh.filtrado.dto.Book;

import java.io.IOException;
import java.util.List;

public interface IBookData {

    List<Book> getDataFromJson() throws IOException;

    void convertToJSONAndSaveCSV(List<Book> books) throws IOException;
}
