package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.services.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ShowBooksCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        BookDao dao = new BookDao();

        List<Book> books = dao.getAllBooks();
        for (Book book : books) {
            bookDTOs.add(Mapper.bookToDTO(book));
        }
        content.setRequestAttribute("bookList", bookDTOs);
//        return "main.jsp";
        return new CommandResult("main.jsp", false);
    }
}
