package rental.view;

import book.model.BooksModel;
import book.service.BookService;
import rental.model.RentalsModel;
import rental.service.RentalService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class RentalList implements Serializable {

    private final RentalService rentalService;
    private RentalsModel rentals;

    @Inject
    public RentalList(RentalService rentalService) {
        this.rentalService =rentalService;
    }

    public RentalsModel getRentals(Long id) {
        if (rentals == null) {
            rentals = RentalsModel.entityToModelMapper().apply(rentalService.findAll(id));
        }
        return rentals;
    }

    public String deleteAction(Long id, Long id2)  {
        rentalService.delete(id);
        return "book_view?id="+id2+"&faces-redirect=true";
    }


}
