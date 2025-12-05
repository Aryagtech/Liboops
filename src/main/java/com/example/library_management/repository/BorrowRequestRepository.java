package com.example.library_management.repository;

import com.example.library_management.model.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {
    List<BorrowRequest> findByBookIdAndStatus(Long bookId, String status);
    List<BorrowRequest> findByUser_Id(Long userId);
}
