package org.project.entity.mapper;

import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.dto.BookDTO;
import org.project.entity.dto.CheckoutDTO;


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
        CheckoutDTO dto = new CheckoutDTO();
        dto.setStartTime(checkout.getStartTime());
        dto.setEndTime(checkout.getEndTime());
        dto.setUsername(checkout.getUser().getLogin());
        dto.setBookTitle(checkout.getBook().getTitle());
        return dto;
    }
}
