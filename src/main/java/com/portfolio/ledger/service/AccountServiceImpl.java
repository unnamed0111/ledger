package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        Pageable pageable = pageRequestDTO.getPageable("ano");

        Page<Account> result = accountRepository.searchList(pageable);
        List<AccountDTO> dtoList = result.getContent().stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());

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
}
