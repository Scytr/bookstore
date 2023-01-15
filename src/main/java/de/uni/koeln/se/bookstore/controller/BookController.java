package de.uni.koeln.se.bookstore.controller;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bookStore")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return new ResponseEntity<>(bookService.fetchBook(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> removeBookById(@PathVariable Integer id) {
        Book book = bookService.fetchBook(id).get();
        if (bookService.deleteBook(id)) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/oldest")
    public ResponseEntity<Book> getOldestBook() {
        Book oldestBook = null;
        for (Book book : bookService.findBooks()) {
            if (oldestBook == null || oldestBook.getYear() > book.getYear()) {
                oldestBook = book;
            }
        }
        return new ResponseEntity<>(oldestBook, HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<Book> getLatestBook() {
        Book latestBook = null;
        for (Book book : bookService.findBooks()) {
            if (latestBook == null || latestBook.getYear() < book.getYear()) {
                latestBook = book;
            }
        }
        return new ResponseEntity<>(latestBook, HttpStatus.OK);
    }
}
