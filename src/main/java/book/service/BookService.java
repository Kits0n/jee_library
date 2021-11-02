package book.service;

import book.entity.Book;
import book.repository.BookRepository;
import lombok.NoArgsConstructor;
import user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @Transactional
    public void delete(Long id) {
        bookRepository.delete(id);
    }
    @Transactional
    public void create(Book book) {
        bookRepository.create(book);
    }
    @Transactional
    public void update(Book book) {
        bookRepository.update(book);
    }
}
