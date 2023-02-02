package org.project.commands.get;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.dto.BookDTO;
import org.project.services.Mapper;

import java.util.List;

public class FindBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        String criteria = content.getParameter("filter");
        String textInput = content.getParameter("text-input");

        BookDao finder = new BookDao();
        List<Book> books = finder.searchForBook(criteria, textInput);
        List<BookDTO> dtoList;

        dtoList = books.stream()
                .map(Mapper::bookToDTO)
                .toList();


        content.setRequestAttribute("bookList", dtoList);
        return new CommandResult("main.jsp", false);
    }
}
