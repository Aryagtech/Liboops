package com.example.library_management.service;

import com.example.library_management.model.Book;
import com.example.library_management.model.BorrowRequest;
import com.example.library_management.model.User;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowRequestService {

    private final BorrowRequestRepository borrowRequestRepository;
    private final BookRepository bookRepository;

    public BorrowRequestService(BorrowRequestRepository borrowRequestRepository, BookRepository bookRepository) {
        this.borrowRequestRepository = borrowRequestRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createRequest(User user, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        List<BorrowRequest> existing = borrowRequestRepository.findByBookIdAndStatus(bookId, "PENDING");
        boolean alreadyRequested = existing.stream()
                .anyMatch(req -> req.getUser().getId().equals(user.getId()));
        if (alreadyRequested) throw new RuntimeException("Pending request already exists.");

        BorrowRequest request = new BorrowRequest(user, book);
        borrowRequestRepository.save(request);
    }

    public List<BorrowRequest> getAllRequests() { return borrowRequestRepository.findAll(); }

    @Transactional
    public void approveRequest(Long requestId) {
        BorrowRequest br = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        Book book = br.getBook();
        if (!book.isAvailable()) throw new RuntimeException("Book already borrowed");
        br.setStatus("APPROVED");
        book.setAvailable(false);
        bookRepository.save(book);
        borrowRequestRepository.save(br);
    }

    @Transactional
    public void rejectRequest(Long requestId) {
        BorrowRequest br = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        br.setStatus("REJECTED");
        borrowRequestRepository.save(br);
    }

    // --- USER RETURN ---
    @Transactional
    public void markAsReturned(Long requestId, User user) {
        BorrowRequest br = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!br.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Cannot return book not borrowed by you");
        }
        br.setStatus("RETURNED");
        Book book = br.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        borrowRequestRepository.save(br);
    }

    // --- ADMIN RETURN ---
    @Transactional
    public void markAsReturned(Long requestId) {
        BorrowRequest br = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        br.setStatus("RETURNED");
        Book book = br.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        borrowRequestRepository.save(br);
    }
}
