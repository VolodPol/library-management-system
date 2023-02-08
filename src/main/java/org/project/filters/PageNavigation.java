package org.project.filters;

public enum PageNavigation {
    UNREGISTERED(
            new String[]{"login.jsp", "register.jsp", "index.jsp", "main.jsp"}
    ),
    USER(
            new String[]{"login.jsp", "register.jsp", "index.jsp", "main.jsp", "new_order.jsp", "profile.jsp", "user_books.jsp"}
    ),
    ADMIN(
            new String[]{"login.jsp", "register.jsp", "index.jsp", "main.jsp", "edit_book.jsp", "librarians.jsp", "new_book.jsp", "new_librarian.jsp", "new_publisher.jsp", "profile.jsp", "users.jsp"}
    ),
    LIBRARIAN(new String[]{"login.jsp", "register.jsp", "index.jsp", "main.jsp", "order_list.jsp"});

    private final String[] routes;
    PageNavigation (String[] value) {
        this.routes = value;
    }

    public String[] getRoutes() {
        return routes;
    }
}
