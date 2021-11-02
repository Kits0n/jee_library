package book.repository;

import book.entity.Book;
import datastore.DataStore;
import user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Dependent
public class BookRepository {

    private DataStore store;

    @Inject
    public BookRepository(DataStore store) {
        this.store = store;
    }


    public Optional<Book> find(Long id) {
        return store.findBook(id);
    }

    public List<Book> findAll() {
        return store.findAllBooks();
    }

    public void delete(Long id) {
        store.deleteBook(id);
    }

    public void create(Book book) {
        store.createBook(book);
    }

    public void update(Book book) {
        store.updateBook(book);
    }
}
