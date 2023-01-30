package com.portfolio.ledger.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequestDTO {
    @Builder.Default // 빌드 세션 중에 필드를 채우지 않으면 자동으로 0, false, null 로 지정되기 때문에 기본값을 지정해줌
    private int page = 1;
    @Builder.Default
    private int size = 5;

    private String link;

    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        if(link == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            link = builder.toString();
        }

        return link;
    }

    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();

        info.put("page", Integer.toString(page));
        info.put("size", Integer.toString(size));

        return info;
    }
}
