package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.exception.NotOwnerException;
import com.portfolio.ledger.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(AccountDTO accountDTO) {
       Account account = modelMapper.map(accountDTO, Account.class);

       return accountRepository.save(account).getAno();
    }

    @Override
    public PageResponseDTO<AccountDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("date");

        Page<AccountDTO> result = accountRepository.searchList(pageable, null);
        List<AccountDTO> dtoList = result.getContent();

        return PageResponseDTO.<AccountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public AccountDTO get(Long ano) {
        Optional<Account> result = accountRepository.findById(ano);
        Account account = result.orElseThrow();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return accountDTO;
    }

    @Override
    public void modify(AccountDTO accountDTO) throws NotOwnerException {
        Optional<Account> result = accountRepository.findById(accountDTO.getAno());
        Account account = result.orElseThrow();

        if(!account.getWriter().equals(accountDTO.getWriter())) throw new NotOwnerException("not owner");

        account.change(
                accountDTO.getDate(),
                accountDTO.getTitle(),
                accountDTO.getContent(),
                accountDTO.getAmount(),
                accountDTO.getPrice(),
                accountDTO.isSnp(),
                accountDTO.getWriter()
        );

        accountRepository.save(account);
    }

    @Override
    public void remove(AccountDTO accountDTO) throws NotOwnerException
    {
        Optional<Account> result = accountRepository.findById(accountDTO.getAno());
        Account account = result.orElseThrow();

        if(!account.getWriter().equals(accountDTO.getWriter())) throw new NotOwnerException("not owner");

        accountRepository.deleteById(accountDTO.getAno());
    }

    @Override
    public Map<String, Double> getTotalPrice() {
        return accountRepository.searchTotalPrice();
    }
}
