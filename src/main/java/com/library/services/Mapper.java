package com.library.services;

import com.library.entity.dto.*;
import com.library.entity.Book;
import com.library.entity.Checkout;
import com.library.entity.Publisher;
import com.library.entity.User;
import com.library.utils.UtilProvider;

import java.util.List;

/**
 * Class for mapping entities to their DTO counterparts.
 */
public class Mapper {
    public static BookDTO bookToDTO(Book book){
        BookDTO bookDTO = new BookDTO();

        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setCopiesNumber(book.getCopiesNumber());
        bookDTO.setDateOfPublication(book.getDateOfPublication());
        bookDTO.setPublisher(book.getPublisher().getName());

        return bookDTO;
    }

    public static List<BookDTO> booksToDTO(List<Book> books) {
        return books.stream()
                .map(Mapper::bookToDTO)
                .toList();
    }

    public static CheckoutDTO checkoutToDTO(Checkout checkout) {
        CheckoutDTO checkoutDTO = new CheckoutDTO();

        checkoutDTO.setId(checkout.getId());
        checkoutDTO.setStartTime(checkout.getStartTime());
        checkoutDTO.setEndTime(checkout.getEndTime());
        checkoutDTO.setUsername(checkout.getUser().getLogin());
        checkoutDTO.setBookTitle(checkout.getBook().getTitle());
        checkoutDTO.setOrderStatus(checkout.getOrderStatus());
        checkoutDTO.setType(checkout.getType().getValue());
        checkoutDTO.setFinedStatus(UtilProvider.isFined(checkout.getFinedStatus()));
        checkoutDTO.setBookId(checkout.getBook().getId());

        return checkoutDTO;
    }

    public static List<CheckoutDTO> checkoutsToDTO(List<Checkout> checkouts) {
        return checkouts.stream()
                .map(Mapper::checkoutToDTO)
                .toList();
    }

    public static LibrarianDTO userToLibrarianDTO(User user) {
        LibrarianDTO librarian = new LibrarianDTO();

        librarian.setId(user.getId());
        librarian.setLogin(user.getLogin());
        librarian.setFullName(user.getFirstName().concat(String.format(" %s", user.getSurname())));
        librarian.setEmail(user.getEmail());
        librarian.setPhone(user.getPhoneNumber());
        librarian.setAge(user.getAge());

        return librarian;
    }

    public static List<LibrarianDTO> usersToLibrariansDTO(List<User> users) {
        return users.stream()
                .map(Mapper::userToLibrarianDTO)
                .toList();
    }

    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFirstName() + " " + user.getSurname());
        userDTO.setPhone(user.getPhoneNumber());
        userDTO.setFineAmount(
                String.valueOf(user.getFineAmount())
        );
        userDTO.setStatus(user.getStatus() == 0 ? "active" : "blocked");
        userDTO.setRole(user.getRole().getRoleValue());
        userDTO.setSubscription(user.getSubscription().getValue());

        return userDTO;
    }

    public static List<UserDTO> usersToUsersDTO(List<User> users) {
        return users.stream()
                .map(Mapper::userToUserDTO)
                .toList();
    }

    public static PublisherDTO publisherToDTO(Publisher publisher) {
        return new PublisherDTO(publisher.getId(), publisher.getName());
    }
    @SuppressWarnings("unused")
    public static List<PublisherDTO> publishersToPublishersDTO(List<Publisher> publishers) {
        return publishers.stream()
                .map(Mapper::publisherToDTO)
                .toList();
    }
}
