package org.project.services.validation.dataset.impl;

import org.project.services.validation.dataset.DataSet;
import org.project.utils.UtilProvider;

/**
 * The class contains publisher data to validate
 */
public class PublisherDataSet implements DataSet {
    private String publisherName;

    public PublisherDataSet(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(publisherName);
    }

    public String getPublisherName() {
        return publisherName;
    }
    @SuppressWarnings("unused")
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
