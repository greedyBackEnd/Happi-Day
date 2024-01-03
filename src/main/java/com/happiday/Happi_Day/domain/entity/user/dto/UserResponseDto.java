package com.happiday.Happi_Day.domain.entity.user.dto;

import com.happiday.Happi_Day.domain.entity.user.RoleType;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String realname;
    private String phone;
    private RoleType role;
    private String imageUrl;

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .realname(user.getRealname())
                .phone(user.getPhone())
                .role(user.getRole())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
