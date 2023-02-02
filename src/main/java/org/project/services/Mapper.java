package org.project.services;

import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.User;
import org.project.entity.dto.BookDTO;
import org.project.entity.dto.CheckoutDTO;
import org.project.entity.dto.LibrarianDTO;


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

    public static CheckoutDTO checkoutToDTO(Checkout checkout) {
        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setId(checkout.getId());
        checkoutDTO.setStartTime(checkout.getStartTime());
        checkoutDTO.setEndTime(checkout.getEndTime());
        checkoutDTO.setUsername(checkout.getUser().getLogin());
        checkoutDTO.setBookTitle(checkout.getBook().getTitle());
        checkoutDTO.setOrderStatus(checkout.getStatus());
        checkoutDTO.setBookId(checkout.getBook().getId());
        return checkoutDTO;
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
}
