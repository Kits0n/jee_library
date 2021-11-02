package book.controller;

import book.dto.CreateBookRequest;
import book.dto.GetBookResponse;
import book.dto.GetBooksResponse;
import book.dto.UpdateBookRequest;
import book.entity.Book;
import book.service.BookService;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("")
public class BookController {

    private BookService bookService;

    public BookController() {
    }

    @Inject
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.ok(GetBooksResponse.entityToDtoMapper().apply(bookService.findAll())).build();
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") Long id) {
        Optional<Book> book = bookService.find(id);
        if(book.isPresent())
        {
            return Response.ok(GetBookResponse.entityToDtoMapper().apply(book.get())).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postBook(CreateBookRequest request) {
        Book book = CreateBookRequest
                .dtoToEntityMapper()
                .apply(request);
        bookService.create(book);
        return Response.created(UriBuilder.fromMethod(BookController.class, "getBook")
                .build(book.getId())).build();
    }

    @PUT
    @Path("/books/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putBook(@PathParam("id") Long id, UpdateBookRequest request) {
        Optional<Book> book = bookService.find(id);
        if(book.isPresent())
        {
            UpdateBookRequest.dtoToEntityUpdater().apply(book.get(), request);
            bookService.update(book.get());
            return Response.ok().build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/books/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        Optional<Book> book = bookService.find(id);
        if(book.isPresent())
        {
            bookService.delete(id);
        }
        return Response.ok().build();
    }
}
