package com.example.library_management.service;

import com.example.library_management.model.Book;
import com.example.library_management.model.BorrowRequest;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowRequestRepository borrowRequestRepository;

    public BookService(BookRepository bookRepository,
                       BorrowRequestRepository borrowRequestRepository) {
        this.bookRepository = bookRepository;
        this.borrowRequestRepository = borrowRequestRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        List<BorrowRequest> activeRequests = borrowRequestRepository.findByBookIdAndStatus(id, "APPROVED");
        if (!activeRequests.isEmpty()) {
            throw new RuntimeException("Cannot delete book. There are active borrow requests.");
        }
        bookRepository.deleteById(id);
    }

    public Book borrowBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return bookRepository.save(book);
        }
        return null;
    }

    public Book returnBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setAvailable(true);
            return bookRepository.save(book);
        }
        return null;
    }
}
