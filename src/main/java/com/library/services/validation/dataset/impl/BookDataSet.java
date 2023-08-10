package com.library.services.validation.dataset.impl;

import com.library.services.validation.dataset.DataSet;
import com.library.utils.UtilProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class contains book data to validate
 */
@Getter
@Setter
@AllArgsConstructor
public class BookDataSet implements DataSet {
    private String title;
    private String author;
    private String isbn;
    private String copies;
    private String dateOfPublication;
    private String publisher;

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(title) && UtilProvider.isEmpty(author)
                && UtilProvider.isEmpty(isbn) && UtilProvider.isEmpty(copies)
                && UtilProvider.isEmpty(dateOfPublication) && UtilProvider.isEmpty(publisher);
    }
}
