package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.Book;
import org.project.entity.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.project.dao.Constants.*;

public class BookDao {
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_BOOKS);
            while (rs.next()) {
                Book currentBook;
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String isbn = rs.getString(4);
                int copiesNumber = rs.getInt(5);
                Date dateOfPublication = rs.getDate(6);

                Publisher currentPublisher = new Publisher();
                currentPublisher.setId(rs.getInt(7));
                currentPublisher.setName(rs.getString(8));

                currentBook = new Book(id, title, author, isbn, copiesNumber, dateOfPublication, currentPublisher);
                books.add(currentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book findBook(String isbn) {
        Book book = new Book();
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_BOOK_BY_ISBN);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book.setId(rs.getInt("b.id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCopiesNumber(rs.getInt("copies_number"));
                book.setDateOfPublication(rs.getDate("date_of_publication"));
                book.setPublisher(new Publisher(
                        rs.getInt("p.id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return book;
    }
}
