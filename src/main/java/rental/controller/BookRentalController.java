package rental.controller;

import book.entity.Book;
import book.service.BookService;

import rental.dto.CreateRentalRequest;
import rental.dto.GetRentalResponse;
import rental.dto.GetRentalsResponse;
import rental.dto.UpdateRentalRequest;
import rental.entity.Rental;
import rental.service.RentalService;
import rental.service.RentalService;
import user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("")
public class BookRentalController {

    private RentalService rentalService;
    private BookService bookService;
    private UserService userService;

    public BookRentalController() {
    }

    @Inject
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Inject
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/rentals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentals() {
        return Response.ok(GetRentalsResponse.entityToDtoMapper().apply(rentalService.findAll())).build();
    }

    @GET
    @Path("/books/{id}/rentals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentals(@PathParam("id") Long book_id) {
        Optional<Book> book = bookService.find(book_id);
        if(book.isPresent())
        {
            return Response.ok(GetRentalsResponse.entityToDtoMapper().apply(rentalService.findAll(book_id))).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/rentals/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRental(@PathParam("id") Long id) {
        Optional<Rental> rental = rentalService.find(id);
        if(rental.isPresent())
        {
            return Response.ok(GetRentalResponse.entityToDtoMapper().apply(rental.get())).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/books/{id}/rentals")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRental(@PathParam("id") Long book_id, CreateRentalRequest request) {
        Optional<Book> book = bookService.find(book_id);
        if(book.isPresent())
        {
            Rental rental = CreateRentalRequest
                    .dtoToEntityMapper(userId-> userService.find(userId).orElse(null))
                    .apply(request);
            rental.setBook(book.get());
            rentalService.create(rental);
            return Response.created(UriBuilder.fromMethod(BookRentalController.class, "getRental")
                    .build(rental.getId())).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/books/{book_id}/rentals/{rental_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putRental(@PathParam("book_id") Long book_id, @PathParam("rental_id") Long rental_id, UpdateRentalRequest request) {
        Optional<Rental> rental = rentalService.find(rental_id);
        Optional<Book> book = bookService.find(book_id);
        if(rental.isPresent() && book.isPresent())
        {
            if(request != null){
                UpdateRentalRequest.dtoToEntityUpdater(userId-> userService.find(userId).orElse(null)).apply(rental.get(), request);
                rental.get().setBook(book.get());
                rentalService.update(rental.get());
                return Response.ok().build();
            }
            return Response.noContent().build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/rentals/{id}")
    public Response deleteRental(@PathParam("id") Long id) {
        Optional<Rental> rental = rentalService.find(id);
        if(rental.isPresent())
        {
            rentalService.delete(id);
        }
        return Response.ok().build();
    }

}
