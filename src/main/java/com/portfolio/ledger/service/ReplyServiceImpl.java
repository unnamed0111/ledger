package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.ReplyDTO;
import com.portfolio.ledger.exception.NotOwnerException;
import com.portfolio.ledger.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfAccount(Long ano, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : (pageRequestDTO.getPage() - 1),
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending()
        );

        Page<Reply> result = replyRepository.listOfAccount(ano, pageable);

        List<ReplyDTO> dtoList = result.getContent()
                .stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO pageResponseDTO = PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

        return pageResponseDTO;
    }

    @Override
    public void modify(ReplyDTO replyDTO) throws NotOwnerException {
        Reply reply = replyRepository.findById(replyDTO.getRno()).orElseThrow();

        if(!reply.getWriter().equals(replyDTO.getWriter())) throw new NotOwnerException("not owner");

        reply.changeContent(replyDTO.getContent());

        replyRepository.save(reply);
    }

    @Override
    public void remove(ReplyDTO replyDTO) throws NotOwnerException {
        Reply reply = replyRepository.findById(replyDTO.getRno()).orElseThrow();

        if(!reply.getWriter().equals(replyDTO.getWriter())) throw new NotOwnerException("not owner");

        replyRepository.deleteById(replyDTO.getRno());
    }
}
