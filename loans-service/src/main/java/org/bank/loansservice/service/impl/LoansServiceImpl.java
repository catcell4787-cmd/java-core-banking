package org.bank.loansservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.bank.loansservice.exception.GlobalExceptionHandler;
import org.bank.loansservice.model.dto.LoanDTO;
import org.bank.loansservice.model.entity.Loan;
import org.bank.loansservice.repository.LoansRepository;
import org.bank.loansservice.service.LoansService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements LoansService {

    private final LoansRepository loansRepository;
    private final ModelMapper modelMapper;

    @Override
    public LoanDTO createLoan(String email, LoanDTO loanDTO) {
        if (loansRepository.existsByCardHolder(email)) {
            throw new GlobalExceptionHandler.ConflictException("You have active loans");
        }
        Loan loan = new Loan();
        loan.setCardHolder(email);
        loan.setAmount(loanDTO.getAmount());
        loan.setTermInMonths(loanDTO.getTermInMonths());
        loansRepository.save(loan);
        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    public LoanDTO getLoan(String email) {
        Optional<Loan> loan = loansRepository.findByCardHolder(email);
        if (loan.isPresent()) {
            return modelMapper.map(loan.get(), LoanDTO.class);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("You have no any loans registered");
    }
}
