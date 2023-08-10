package com.library.services.validation.dataset.impl;

import com.library.services.validation.dataset.DataSet;
import com.library.utils.UtilProvider;
import lombok.Getter;
import lombok.Setter;

/**
 * The class contains publisher data to validate
 */
@Getter
@Setter
public class PublisherDataSet implements DataSet {
    private String publisherName;

    public PublisherDataSet(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(publisherName);
    }
}
