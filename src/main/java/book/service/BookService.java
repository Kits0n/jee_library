package book.service;

import book.entity.Book;
import book.repository.BookRepository;
import lombok.NoArgsConstructor;
import user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    @Inject
    public BookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> find(Long id) {
        return bookRepository.find(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(Long id) {
        bookRepository.delete(id);
    }

    public void create(Book book) {
        bookRepository.create(book);
    }
}
