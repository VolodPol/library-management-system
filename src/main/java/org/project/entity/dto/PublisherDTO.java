package org.project.entity.dto;

import org.project.entity.Publisher;

public class PublisherDTO {
    private int id;
    private String name;

    public PublisherDTO() {}

    public PublisherDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
