package book.view;

import book.entity.Book;
import book.model.BookModel;
import book.service.BookService;
import lombok.Getter;
import lombok.Setter;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class BookView implements Serializable {


    private final BookService bookService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private BookModel book;

    @Inject
    public BookView(BookService bookService) {
        this.bookService = bookService;
    }

    public void init() throws IOException {
        Optional<Book> book = bookService.find(id);
        if (book.isPresent()) {
            this.book = BookModel.entityToModelMapper().apply(book.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }

    
}
