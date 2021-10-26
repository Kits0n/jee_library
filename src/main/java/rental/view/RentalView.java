package rental.view;

import book.entity.Book;
import book.model.BookModel;
import book.service.BookService;
import lombok.Getter;
import lombok.Setter;
import rental.entity.Rental;
import rental.model.RentalModel;
import rental.service.RentalService;

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
public class RentalView implements Serializable {


    private final RentalService rentalService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private RentalModel rental;

    @Inject
    public RentalView(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public void init() throws IOException {
        Optional<Rental> rental = rentalService.find(id);
        if (rental.isPresent()) {
            this.rental = RentalModel.entityToModelMapper().apply(rental.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
        }
    }

    
}
