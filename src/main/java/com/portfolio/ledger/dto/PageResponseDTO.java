package com.portfolio.ledger.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class PageResponseDTO<E> {
    static final int navSize = 10; // 화면 당 페이지 표시 갯수

    // 클라이언트에서 이전 페이지 요청을 기억할때 필요한 PageRequestDTO 정보
    private int page;       // 현재 페이지
    private int size;       // 페이지 당 표시갯수

    // 클라이언가 응답 받을 페이지 정보
    private int     total;  // 총 갯수
    private int     start;  // 시작 페이지 번호
    private int     end;    // 끝 페이지 번호
    private boolean prev;   // 이전 페이지 존재 여부
    private boolean next;   // 다음 페이지 존재 여부

    private List<E> dtoList;

    private Map<String, Double> totalPrice;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) { // total은 총 갯수

        if(total <= 0) return;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        int last = (int) Math.ceil(total/(double)size); // 마지막 페이지
        this.dtoList = dtoList;

        this.start = (((page - 1)/navSize) * navSize) + 1;
        this.end = start + navSize - 1;
        this.end = last < end ? last : end;

        this.prev = (start - 1) > 1;
        this.next = (end + 1) <= last;
    }
}
