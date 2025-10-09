package org.bank.loansservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.bank.loansservice.exception.GlobalExceptionHandler;
import org.bank.loansservice.model.dto.LoanDTO;
import org.bank.loansservice.model.entity.Loan;
import org.bank.loansservice.repository.LoansRepository;
import org.bank.loansservice.service.LoansService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements LoansService {

    private final LoansRepository loansRepository;
    private final ModelMapper modelMapper;

    @Override
    public LoanDTO createLoan(String email, LoanDTO loanDTO) {
        if (loansRepository.existsByCardNumber(loanDTO.getCardNumber())) {
            throw new GlobalExceptionHandler.ConflictException("You have active loans");
        }
        Loan loan = new Loan();
        loan.setCardHolder(email);
        loan.setCardNumber(loanDTO.getCardNumber());
        loan.setAmount(loanDTO.getAmount());
        loan.setTermInMonths(loanDTO.getTermInMonths());
        loansRepository.save(loan);
        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    public LoanDTO getLoan(String email) {
        return null;
    }
}
