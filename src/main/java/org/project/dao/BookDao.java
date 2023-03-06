package org.project.dao;

import org.project.connection.ConnectionManager;
import org.project.entity.Book;
import org.project.entity.Publisher;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.project.dao.constants.Queries.*;

public class BookDao {
    private final static Logger log = LoggerFactory.getLogger(BookDao.class);
    private int numOfRecs;
    public List<Book> findAll(int offSet, int total, Sorting sorting, OrderType type) throws DaoException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = buildFindQuery(offSet, total, sorting, type);

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Book currentBook;
            while (rs.next()) {
                currentBook = retrieveBook(rs);
                books.add(currentBook);
            }
            rs.close();
            ResultSet rs1 = ps.executeQuery("SELECT FOUND_ROWS()");
            if (rs1.next()){
                this.numOfRecs = rs1.getInt(1);
            }
            rs1.close();
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return books;
    }
    public int getNumOfRecs() {
        return this.numOfRecs;
    }

    public Optional<Book> findByIsbn(String isbn) throws DaoException {
        Optional<Book> result;
        Book book = null;
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_BOOK_BY_ISBN);
            ps.setString(1, isbn);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                book = retrieveBook(resultSet);
            }
            result = Optional.ofNullable(book);

        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
        return result;
    }

    public List<Book> searchFor(String category, String searchedAs) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement statement;
            if (category.equals("title"))
                statement = connection.prepareStatement(SEARCH_FOR_BOOK_BY_TITLE);
            else
                statement = connection.prepareStatement(SEARCH_FOR_BOOK_BY_AUTHOR);

            statement.setString(1, "%".concat(searchedAs).concat("%"));

            ResultSet rs = statement.executeQuery();
            Book book;
            while (rs.next()) {
                book = retrieveBook(rs);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return foundBooks;
    }

    public boolean changeCopiesNum(int bookId, boolean toIncrease) throws DaoException {
        boolean result = false;
        String query = buildCopyQuery(bookId, toIncrease);

        try (Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("SavePoint");

            try (PreparedStatement ps = con.prepareStatement(query)) {
                int update = ps.executeUpdate();
                result = update != 0;
                con.commit();

            } catch (SQLException exception) {
                con.rollback(save);
            }

        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return result;
    }

    public boolean update(Book newBook, String isbn) throws DaoException {
        boolean result = false;
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("Save");

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK)) {
                fillPreparedStatement(ps, newBook);
                ps.setString(7, isbn);

                int update = ps.executeUpdate();
                result = update != 0;
                connection.commit();
            } catch (SQLException exception) {
                ConnectionManager.rollback(connection, savepoint);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return result;
    }

    public boolean insert(Book book) throws DaoException {
        boolean result = false;
        try (Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("SavePoint");

            try (PreparedStatement statement = con.prepareStatement(CREATE_BOOK)) {
                fillPreparedStatement(statement, book);

                int update = statement.executeUpdate();
                result = update != 0;
                con.commit();

            } catch (SQLException exception) {
                ConnectionManager.rollback(con, sp);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return result;
    }
    public boolean delete(String isbn) throws DaoException {
        boolean result = false;
        try(Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false);
            Savepoint savepoint = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(DELETE_BOOK)) {
                ps.setString(1, isbn);
                int update = ps.executeUpdate();
                result = update != 0;

                con.commit();
            } catch (SQLException e) {
                ConnectionManager.rollback(con, savepoint);
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
        return result;
    }

    public boolean isIsbnPresent(String isbn) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(ISBN_PRESENT);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
        return false;
    }

    private static String buildFindQuery(int offSet, int total, Sorting sorting, OrderType type) {
        StringBuilder query = new StringBuilder(GET_ALL_BOOKS);

        if (!type.equals(OrderType.DEFAULT))
            query.append(String.format(" ORDER BY %s ", type.getValue()));
        if (sorting.equals(Sorting.DESC))
            query.append(sorting.getValue());

        query.append(String.format(" LIMIT %d, %d", offSet, total));
        return query.toString();
    }

    private static String buildCopyQuery(int bookId, boolean toIncrease) {
        //"UPDATE book SET copies_number = copies_number - 1 WHERE id = ? and copies_number > 0"
        return "UPDATE book SET copies_number = copies_number " +
                (toIncrease ? "+ 1 " : "- 1 ") +
                "WHERE id = " + bookId +
                " AND copies_number > 0";
    }

    private static Book retrieveBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt(1));
        book.setTitle(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setIsbn(resultSet.getString(4));
        book.setCopiesNumber(resultSet.getInt(5));
        book.setDateOfPublication(resultSet.getDate(6));
        book.setPublisher(new Publisher(
                resultSet.getInt(7),
                resultSet.getString(8)));
        return book;
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