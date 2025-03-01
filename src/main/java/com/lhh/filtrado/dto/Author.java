package com.lhh.filtrado.dto;

import java.util.Objects;

public record Author(String name, String firstSurname, String bio) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
                Objects.equals(firstSurname, author.firstSurname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstSurname);
    }
}
