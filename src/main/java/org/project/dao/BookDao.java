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
    private int numOfRecs;
    public List<Book> getAllBooks(int offSet, int total) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_BOOKS_LIMIT);
            ps.setInt(1, offSet);
            ps.setInt(2, total);
            ResultSet rs = ps.executeQuery();

            Book currentBook;
            while (rs.next()) {
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
            rs.close();
            ResultSet rs1 = ps.executeQuery("SELECT FOUND_ROWS()");
            if (rs1.next()) this.numOfRecs = rs1.getInt(1);
            rs1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int getNumOfRecs() {
        return this.numOfRecs;
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

    public List<Book> searchForBook(String category, String searchedAs){
        List<Book> foundBooks = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement statement;
            if (category.equals("title"))
                statement = connection.prepareStatement(SEARCH_FOR_BOOK_BY_TITLE);
            else
                statement = connection.prepareStatement(SEARCH_FOR_BOOK_BY_AUTHOR);

            statement.setString(1, "%".concat(searchedAs).concat("%"));

            ResultSet rs = statement.executeQuery();
            Book book;
            while (rs.next()) {
                book = new Book();

                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setCopiesNumber(rs.getInt(5));
                book.setDateOfPublication(rs.getDate(6));
                book.setPublisher(new Publisher(
                        rs.getInt(7),
                        rs.getString(8)
                ));

                foundBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBooks;
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

    public void updateBook (Book newBook, String isbn) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("Save");

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK)) {
                fillPreparedStatement(ps, newBook);
                ps.setString(7, isbn);

                ps.executeUpdate();
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback(savepoint);
                exception.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertBook(Book book) {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("SavePoint");

            try (PreparedStatement statement = con.prepareStatement(CREATE_BOOK)) {
                fillPreparedStatement(statement, book);

                statement.executeUpdate();
                con.commit();

            } catch (SQLException exception) {
                con.rollback(sp);
                exception.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteBook(String isbn) {
        try(Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint savepoint = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(DELETE_BOOK)) {
                ps.setString(1, isbn);
                ps.execute();

                con.commit();
            } catch (SQLException e) {
                con.rollback(savepoint);
                e.printStackTrace();
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public boolean isIsbnPresent(String isbn) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(ISBN_PRESENT);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()){
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
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

    private void fillPreparedStatement(PreparedStatement statement, Book newBook) throws SQLException {
        statement.setString(1, newBook.getTitle());
        statement.setString(2, newBook.getAuthor());
        statement.setString(3, newBook.getIsbn());
        statement.setInt(4, newBook.getCopiesNumber());
        statement.setDate(5, newBook.getDateOfPublication());
        statement.setInt(6, newBook.getPublisher().getId());
    }
}
