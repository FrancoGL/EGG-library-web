package com.alef.library.services.impl;

import com.alef.library.entities.LoanEntity;
import com.alef.library.errors.ServiceError;
import com.alef.library.repositories.LoanRepository;
import com.alef.library.services.BookService;
import com.alef.library.services.LoanService;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository lRepository;

    private final UserService userService;

    private final BookService bookService;

    @Autowired
    public LoanServiceImpl(LoanRepository lRepository, UserService userService, BookService bookService) {
        this.lRepository = lRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    // ** Create ** //

    @Override
    @Transactional
    public void createLoan(String id, HttpSession session) throws ServiceError {

        LoanEntity loan = new LoanEntity();

        try {
            loan.setBook(bookService.getBookById(id));
            loan.setUser(userService.getUserById(session.getAttribute("id").toString()));
            lRepository.save(loan);
        } catch (Exception ex) {
            throw new ServiceError(ex.getMessage());
        }
    }

    // ** Update ** //

    @Override
    @Transactional
    public void updateLoan(String id) throws ServiceError {

        LoanEntity loan = lRepository.findById(id).orElse(null);

        if(loan == null) {
            throw new ServiceError("Loan not found");
        }

        loan.setRefundDate(LocalDate.now());

        lRepository.save(loan);

        deleteLoan(id);
    }

    // ** Delete ** //

    @Override
    @Transactional
    public void deleteLoan(String id) throws ServiceError {

        LoanEntity loan = lRepository.findById(id).orElse(null);

        if(loan == null) {
            throw new ServiceError("Loan not found");
        }

        lRepository.deleteById(id);
    }

    // ** Get By Id ** //

    @Override
    public LoanEntity getLoanById(String id) {

        LoanEntity loan = lRepository.findById(id).orElse(null);

        if(loan == null) {
            throw new ServiceError("Loan not found");
        }

        return loan;
    }

    private void validation(LoanEntity loan) {

        if(loan == null) {
            throw new ServiceError("Loan empty");
        }

        if(loan.getBook() == null) {
            throw new ServiceError("Book not found");
        }

        if(bookService.getBookById(loan.getBook().getId()).getCurrentCopies() == 0) {
            throw new ServiceError("Book sold out");
        }

        if(loan.getUser() == null) {
            throw new ServiceError("User not found");
        }

    }
}
