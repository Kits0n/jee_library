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

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    public synchronized List<User> findAllUsers() {
        return new ArrayList<>(users);
    }

    public synchronized void createUser(User user){
        user.setId((long) users.size());
        users.add(user);
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
        book.setId((long) books.size());
        books.add(book);
    }

    public synchronized void createRental(Rental rental){
        rental.setId((long) rentals.size());
        rentals.add(rental);
    }

    public synchronized Optional<Rental> findRental(Long id) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst();
    }
    public synchronized List<Rental> findAllRentals(Long id) {
        //return new ArrayList<>(rentals);
        return rentals.stream()
                .filter(rental -> rental.getBook().getId().equals(id))
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
