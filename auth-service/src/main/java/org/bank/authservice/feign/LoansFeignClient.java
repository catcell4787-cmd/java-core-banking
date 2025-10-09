package org.bank.authservice.feign;

import org.bank.authservice.common.loans.dto.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "loans-service", url = "http://localhost:8083")
public interface LoansFeignClient {

    @PostMapping("/loans/{email}/createLoan")
    LoanDTO createLoan(@PathVariable String email, @RequestBody LoanDTO loanDTO);
}
