package com.alef.library.services;

import com.alef.library.entities.LoanEntity;

import javax.servlet.http.HttpSession;

public interface LoanService {

    void createLoan(String id, HttpSession session);

    void updateLoan(String id);

    void deleteLoan(String id);
}
