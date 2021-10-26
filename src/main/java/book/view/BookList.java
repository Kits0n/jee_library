package book.view;

import book.model.BooksModel;
import book.service.BookService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@RequestScoped
@Named
public class BookList implements Serializable {

    private final BookService bookService;
    private BooksModel books;

    @Inject
    public BookList(BookService bookService) {
        this.bookService = bookService;
    }

    public BooksModel getBooks() {
        if (books == null) {
            books = BooksModel.entityToModelMapper().apply(bookService.findAll());
        }
        return books;
    }

    public String deleteAction(Long id)  {
        bookService.delete(id);
        return "book_list?faces-redirect=true";
    }
}
