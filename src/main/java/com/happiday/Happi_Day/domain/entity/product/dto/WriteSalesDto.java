package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WriteSalesDto {
    @NotBlank(message="제목을 입력해주세요.")
    private String name;

    @NotBlank(message= "내용을 입력해주세요.")
    private String description;

    private List<String> artists;
    private List<String> teams;
    private List<String> hashtag;
    private String account;

    public Sales toEntity(){
        return Sales.builder()
                .name(name)
                .description(description)
                .account(account)
                .build();
    }
}
