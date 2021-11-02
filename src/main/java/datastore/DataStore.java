package datastore;
import book.entity.Book;
import rental.entity.Rental;
import user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
public class DataStore {
    private Set<User> users = new HashSet<>();
    private Set<Book> books = new HashSet<>();
    private Set<Rental> rentals = new HashSet<>();
    private Long user_id = 0L;
    private Long book_id = 0L;
    private Long rental_id = 0L;

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    public synchronized List<User> findAllUsers() {
        return new ArrayList<>(users);
    }

    public synchronized void createUser(User user){
        user.setId(user_id);
        users.add(user);
        user_id++;
    }

    public synchronized byte[] findAvatar(Long id) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)){
            return Files.readAllBytes(file);
        }
        else{
            return new byte[0];
        }
    }

    public synchronized void updateAvatar(Long id, InputStream is) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)) {
            Files.write(file, is.readAllBytes());
        }else{
            throw new IllegalArgumentException(
                    String.format("Avatar with id \"%d\" does not exist", id));
        }
    }

    public synchronized void createAvatar(Long id, InputStream is) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(!Files.exists(file)) {
            Files.write(file, is.readAllBytes());
        }else {
            throw new IllegalArgumentException(
                    String.format("Avatar with id \"%d\" already exists", id));
        }
    }

    public void deleteAvatar(Long id) throws IOException, IllegalArgumentException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)) {
            Files.delete(file);
        }else{
            throw new IllegalArgumentException(
                    String.format("Avatar with id \"%d\" does not exist", id));
        }
    }

    public synchronized Optional<Book> findBook(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
    public synchronized List<Book> findAllBooks() {
        return new ArrayList<>(books);
    }

    public synchronized void deleteBook(Long id) throws IllegalArgumentException {

        Optional<Book> book = findBook(id);
        if(book.isPresent()){
            books.remove(book.get());
            rentals.removeAll(findAllRentals(id));
        } else
            throw new IllegalArgumentException(
                            String.format("The book with id \"%d\" does not exist", id));
    }

    public synchronized void createBook(Book book){
        book.setId(book_id);
        books.add(book);
        book_id++;
    }

    public void updateBook(Book book) {
        Optional<Book> book2 = findBook(book.getId());
        if(book2.isPresent()){
            books.remove(book2.get());
            books.add(book);
        } else
            throw new IllegalArgumentException(
                    String.format("The book with id \"%d\" does not exist", book.getId()));
    }

    public synchronized void createRental(Rental rental, Long book_id){
        rental.setId(rental_id);
        Optional<Book> book  = findBook(book_id);
        if(book.isPresent()){
            rental.setBook(book.get());
            rentals.add(rental);
            rental_id++;
        } else
            throw new IllegalArgumentException(
            String.format("The book with id \"%d\" does not exist", book_id));
    }

    public void updateRental(Rental rental, Long book_id) {
        Optional<Book> book = findBook(book_id);
        if(book.isPresent()){
            Optional<Rental> rental2 = findRental(rental.getId());
            if(rental2.isPresent()){
                rentals.remove(rental2.get());
                rental.setBook(book.get());
                rentals.add(rental);
            }else
                throw new IllegalArgumentException(String.format("The rental with id \"%d\" does not exist", rental.getId()));
        } else
            throw new IllegalArgumentException(String.format("The book with id \"%d\" does not exist", book_id));
    }

    public synchronized Optional<Rental> findRental(Long id) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst();
    }

    public synchronized List<Rental> findAllRentals() {
        return new ArrayList<>(rentals);
    }

    public synchronized List<Rental> findAllRentals(Long book_id) {
        return rentals.stream()
                .filter(rental -> rental.getBook().getId().equals(book_id))
                .collect(Collectors.toList());
    }

    public synchronized void deleteRental(Long id) throws IllegalArgumentException {

        Optional<Rental> rental = findRental(id);
        if(rental.isPresent()){
            System.out.println(rentals.size());
            rentals.remove(rental.get());
            System.out.println(rentals.size());
        } else
            throw new IllegalArgumentException(
                    String.format("The rental with id \"%d\" does not exist", id));
    }


    public static String getPath(){
        try (InputStream input = new FileInputStream("../../../../../../app.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
