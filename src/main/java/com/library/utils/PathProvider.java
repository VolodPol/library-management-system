package com.library.utils;

import java.util.ResourceBundle;

/**
 * Utility class to provide the path of the resource.
 */
public class PathProvider {
    private final static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("page_resources");
    }

    public static String getPath(String key) {
        return bundle.getString("path.".concat(key));
    }
}
