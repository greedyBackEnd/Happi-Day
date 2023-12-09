package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.product.SalesCategory;
import com.happiday.Happi_Day.domain.entity.product.SalesStatus;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.*;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesInitService {

    private final SalesRepository salesRepository;
    private final UserRepository userRepository;
    private final SalesCategoryRepository salesCategoryRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;

    public void initSales() {
        User seller = userRepository.findById(2L).orElse(null);
        SalesCategory category1 = salesCategoryRepository.findById(1L).orElse(null);
        SalesCategory category2 = salesCategoryRepository.findById(1L).orElse(null);
        Artist artist1 = artistRepository.findById(1L).orElse(null);
        Artist artist2 = artistRepository.findById(2L).orElse(null);
        Artist artist3 = artistRepository.findById(3L).orElse(null);
        Artist artist4 = artistRepository.findById(4L).orElse(null);
        Team team1 = teamRepository.findById(1L).orElse(null);
        Team team2 = teamRepository.findById(1L).orElse(null);


        List<Sales> salesList = List.of(
                Sales.builder()
                        .users(seller)
                        .salesCategory(category1)
                        .name("동방신기 티셔츠 팔아요.")
                        .description("동방신기 콘서트 티셔츠 굿즈, 거의 새 것...")
                        .salesStatus(SalesStatus.ON_SALE)
                        .account("1234567890")
                        .artists(List.of(artist1, artist2))
                        .teams(List.of(team1))
                        .ectArtists("")
                        .ectTeams("")
                        .build(),
                Sales.builder()
                        .users(seller)
                        .salesCategory(category2)
                        .name("god 자켓 팔아요.")
                        .description("god 콘서트 자켓 굿즈, 거의 새 것...")
                        .salesStatus(SalesStatus.ON_SALE)
                        .account("1234567890")
                        .artists(List.of(artist3, artist4))
                        .teams(List.of(team2))
                        .ectArtists("")
                        .ectTeams("")
                        .build()
        );

        salesList.forEach(sales -> {
            try {
                if (!salesRepository.existsByName(sales.getName())) {
                    salesRepository.save(sales);
                }
            } catch (Exception e) {
                log.error("DB Seeder 판매글 저장 중 예외 발생 - 판매글명: {}", sales.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_SALES_SAVE_ERROR);
            }
        });
    }
}
