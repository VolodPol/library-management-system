package org.project.dao;

import org.project.connection.ConnectionManager;
import org.project.entity.Book;
import org.project.entity.Publisher;
import org.project.entity.sorting.SortBy;
import org.project.entity.sorting.SortOrder;
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
    public List<Book> findAll(int offSet, int total, SortOrder sortOrder, SortBy type) throws DaoException {
        List<Book> books = new ArrayList<>();
        String query = buildFindQuery(offSet, total, sortOrder, type);

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                Book currentBook;
                while (rs.next()) {
                    currentBook = retrieveBook(rs);
                    books.add(currentBook);
                }
            }

            try (ResultSet rs1 = ps.executeQuery("SELECT FOUND_ROWS()")) {
                if (rs1.next())
                    this.numOfRecs = rs1.getInt(1);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", e);
        }
        return books;
    }
    public int getNumOfRecs() {
        return this.numOfRecs;
    }

    public Optional<Book> findByIsbn(String isbn) throws DaoException {
        Optional<Book> result;
        Book book = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(FIND_BOOK_BY_ISBN)) {
            ps.setString(1, isbn);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    book = retrieveBook(resultSet);
                }
            }
            result = Optional.ofNullable(book);

        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", exception);
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

            Book book;
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    book = retrieveBook(rs);
                    foundBooks.add(book);
                }
            } finally {
                statement.close();
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", e);
        }
        return foundBooks;
    }

    public void changeCopiesNum(int bookId, boolean toIncrease) throws DaoException {
        String query = buildCopyQuery(bookId, toIncrease);

        try (Connection con = ConnectionManager.getConnection()) {
            boolean auto = con.getAutoCommit();
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("SavePoint");

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.executeUpdate();
                ConnectionManager.commit(con);

            } catch (SQLException exception) {
                ConnectionManager.rollback(con, save);
            } finally {
                con.setAutoCommit(auto);
            }

        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", e);
        }
    }

    public void update(Book newBook, String isbn) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean auto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("Save");

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK)) {
                fillPreparedStatement(ps, newBook);
                ps.setString(7, isbn);

                ps.executeUpdate();
                ConnectionManager.commit(connection);
            } catch (SQLException exception) {
                ConnectionManager.rollback(connection, savepoint);
            } finally {
                connection.setAutoCommit(auto);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", e);
        }
    }

    public void insert(Book book) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            boolean auto = con.getAutoCommit();
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("SavePoint");

            try (PreparedStatement statement = con.prepareStatement(CREATE_BOOK)) {
                fillPreparedStatement(statement, book);

                statement.executeUpdate();
                ConnectionManager.commit(con);
            } catch (SQLException exception) {
                ConnectionManager.rollback(con, sp);
            } finally {
                con.setAutoCommit(auto);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", e);
        }
    }
    public void delete(String isbn) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            boolean auto = con.getAutoCommit();
            con.setAutoCommit(false);
            Savepoint savepoint = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(DELETE_BOOK)) {
                ps.setString(1, isbn);
                ps.executeUpdate();

                ConnectionManager.commit(con);
            } catch (SQLException e) {
                ConnectionManager.rollback(con, savepoint);
            } finally {
                con.setAutoCommit(auto);
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", exception);
        }
    }

    public boolean isIsbnPresent(String isbn) throws DaoException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(ISBN_PRESENT)) {
            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException("DaoException occurred in BookDao class", exception);
        }
        return false;
    }

    private static String buildFindQuery(int offSet, int total, SortOrder sortOrder, SortBy type) {
        StringBuilder query = new StringBuilder(GET_ALL_BOOKS);

        if (!type.equals(SortBy.DEFAULT))
            query.append(String.format(" ORDER BY %s ", type.getValue()));
        if (sortOrder.equals(SortOrder.DESC))
            query.append(sortOrder.getValue());

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