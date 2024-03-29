package com.library.filters.security;

import lombok.Getter;

/**
 * Enum that contains all possible action separated by access rights
 */
@Getter
public enum PageNavigation {
    UNREGISTERED(
            new String[]{
                    "/index.jsp",
                    "login",
                    "/login.jsp",
                    "logout",
                    "/register.jsp",
                    "register",
                    "books"
            }
    ),
    USER(
            new String[]{
                    "/index.jsp",
                    "login",
                    "/login.jsp",
                    "logout",
                    "/register.jsp",
                    "register",
                    "books",
                    "/new_order.jsp",
                    "order",
                    "return",
                    "profile",
                    "my_books",
                    "find_book",
                    "upgrade"
            }
    ),
    ADMIN(
            new String[]{
                    "/index.jsp",
                    "login",
                    "/login.jsp",
                    "logout",
                    "/register.jsp",
                    "books",
                    "/edit_book.jsp",
                    "edit_book",
                    "show_librarians",
                    "/new_book.jsp",
                    "create_book",
                    "delete_book",
                    "delete_librarian",
                    "/new_librarian.jsp",
                    "create_librarian",
                    "/new_publisher.jsp",
                    "create_publisher",
                    "profile",
                    "show_users",
                    "find_book",
                    "block_user"
            }
    ),
    LIBRARIAN(
            new String[]{
                "/index.jsp",
                "login",
                "/login.jsp",
                "logout",
                "/register.jsp",
                "books",
                "show_orders",
                "find_book",
                "profile",
                "confirm",
                "show_users"
            }
    );

    private final String[] routes;
    PageNavigation (String[] value) {
        this.routes = value;
    }
}