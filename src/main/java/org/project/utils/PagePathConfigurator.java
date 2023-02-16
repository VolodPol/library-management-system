package org.project.utils;

import java.util.ResourceBundle;

public class PagePathConfigurator {
    private final static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("page_resources");
    }

    public static String getPath(String key) {
        return bundle.getString("path.".concat(key));
    }
}
