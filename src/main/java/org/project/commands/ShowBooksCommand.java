package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.entity.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        BookDao dao = new BookDao();

        List<Book> books = dao.getAllBooks();
        for (Book book : books) {
            bookDTOs.add(Mapper.bookToDTO(book));
        }
        request.setAttribute("bookList", bookDTOs);
        return "main.jsp";
    }
}
