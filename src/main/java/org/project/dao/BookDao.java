package org.project.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
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
            extractBook(book, ps);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return book;
    }
    
    public Book findBook(int id) {
        Book book = new Book();
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_BOOK_BY_ID);
            ps.setInt(1, id);
            extractBook(book, ps);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return book;
    }



    public void decreaseCopiesNum (int bookId) {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("SavePoint");

            try (PreparedStatement ps = con.prepareStatement(DECREASE_BY_ID)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException exception) {
                con.rollback(save);
                exception.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void extractBook(Book book, PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            book.setId(resultSet.getInt("b.id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setCopiesNumber(resultSet.getInt("copies_number"));
            book.setDateOfPublication(resultSet.getDate("date_of_publication"));
            book.setPublisher(new Publisher(
                    resultSet.getInt("p.id"),
                    resultSet.getString("name")
            ));
        }
    }

}
