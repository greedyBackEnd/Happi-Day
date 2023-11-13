package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.article.Hashtag;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.product.*;
import com.happiday.Happi_Day.domain.entity.product.dto.*;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.*;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesService {
    private final UserRepository userRepository;
    private final SalesCategoryRepository salesCategoryRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;
    private final FileUtils fileUtils;

    @Transactional
    public ReadOneSalesDto createSales(Long categoryId, WriteSalesDto dto, MultipartFile thumbnailImage,List<MultipartFile> imageFile, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 판매글 카테고리
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        List<Product> productList = new ArrayList<>();

        // 해시태그
        List<Hashtag> hashtagList = new ArrayList<>();
        if(dto.getHashtag() != null){
            for (String hashtag: dto.getHashtag()) {
                Hashtag newHashtag = Hashtag.builder()
                        .tag(hashtag)
                        .build();
                hashtagList.add(newHashtag);
            }
        }

        // 아티스트
        List<Artist> artists = new ArrayList<>();
        List<String> ectArtists = new ArrayList<>();
        for (String artist: dto.getArtists()) {
            Optional<Artist> existingArtist = artistRepository.findByName(artist);
            if(existingArtist.isPresent()){
                artists.add(existingArtist.get());
            }else{
                ectArtists.add(artist);
            }
        }
        String ectArtist = String.join(", ", ectArtists);

        // 팀
        List<Team> teams = new ArrayList<>();
        List<String> ectTeams = new ArrayList<>();
        for (String team : dto.getTeams()) {
            Optional<Team> existingTeam = teamRepository.findByName(team);
            if (existingTeam.isPresent()) {
                teams.add(existingTeam.get());
            } else {
                ectTeams.add(team);
            }
        }
        String ectTeam = String.join(", ", ectTeams);

        Sales newSales = Sales.builder()
                .users(user)
                .salesStatus(SalesStatus.ON_SALE)
                .salesCategory(category)
                .name(dto.getName())
                .description(dto.getDescription())
                .products(productList)
                .hashtags(hashtagList)
                .artists(artists)
                .teams(teams)
                .salesLikesUsers(new ArrayList<>())
                .imageUrl(new ArrayList<>())
                .account(dto.getAccount())
                .build();

        if(!ectArtists.isEmpty()){
            newSales = newSales.toBuilder()
                    .ectArtists(ectArtist)
                    .build();
        }
        if(!ectTeams.isEmpty()){
            newSales = newSales.toBuilder()
                    .ectTeams(ectTeam)
                    .build();
        }

        // 이미지 저장
        if(thumbnailImage != null && !thumbnailImage.isEmpty()){
            String saveThumbnailImage = fileUtils.uploadFile(thumbnailImage);
            newSales.setThumbnailImage(saveThumbnailImage);
        }if(imageFile != null && !imageFile.isEmpty()){
            List<String> imageList = new ArrayList<>();
            for(MultipartFile image: imageFile){
                String imageUrl = fileUtils.uploadFile(image);
                imageList.add(imageUrl);
            }
            newSales.setImageUrl(imageList);
        }

        salesRepository.save(newSales);
        ReadOneSalesDto response = ReadOneSalesDto.fromEntity(newSales,new ArrayList<>());
        return response;
    }

    public Page<ReadListSalesDto> readSalesList(Long categoryId, Pageable pageable){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Page<Sales> salesList = salesRepository.findAllBySalesCategory(category, pageable);
        return salesList.map(ReadListSalesDto::fromEntity);
    }

    public ReadOneSalesDto readSalesOne(Long categoryId, Long salesId){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new CustomException(ErrorCode.SALES_NOT_FOUND));

        List<ReadProductDto> dtoList = new ArrayList<>();
        for (Product product: sales.getProducts()) {
            dtoList.add(ReadProductDto.fromEntity(product));
        }

        return ReadOneSalesDto.fromEntity(sales, dtoList);
    }

    @Transactional
    public ReadOneSalesDto updateSales(Long salesId, UpdateSalesDto dto, MultipartFile thumbnailImage, List<MultipartFile> imageFile, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new CustomException(ErrorCode.SALES_NOT_FOUND));

        // user 확인
        if(!user.equals(sales.getUsers())) throw new CustomException(ErrorCode.FORBIDDEN);

        List<Hashtag> hashtagList = new ArrayList<>();
        for (String hashtag : dto.getHashtag()) {
            Hashtag newHashtag = Hashtag.builder()
                    .tag(hashtag)
                    .build();
            hashtagList.add(newHashtag);
        }

        // 아티스트
        List<Artist> artists = new ArrayList<>();
        List<String> ectArtists = new ArrayList<>();
        for (String artist : dto.getArtists()) {
            Optional<Artist> existingArtist = artistRepository.findByName(artist);
            if (existingArtist.isPresent()) {
                artists.add(existingArtist.get());
            } else {
                ectArtists.add(artist);
            }
        }
        String ectArtist = String.join(", ", ectArtists);

        // 팀
        List<Team> teams = new ArrayList<>();
        List<String> ectTeams = new ArrayList<>();
        for (String team : dto.getTeams()) {
            Optional<Team> existingTeam = teamRepository.findByName(team);
            if (existingTeam.isPresent()) {
                teams.add(existingTeam.get());
            } else {
                ectTeams.add(team);
            }
        }
        String ectTeam = String.join(", ", ectTeams);

        if(thumbnailImage != null && !thumbnailImage.isEmpty()){
            if(sales.getThumbnailImage() != null && !sales.getThumbnailImage().isEmpty()){
                try{
                    fileUtils.deleteFile(sales.getThumbnailImage());
                    log.info("썸네일 이미지 삭제완료");
                }catch(Exception e){
                    log.error("썸네일 삭제 실패");
                }
            }
            String thumbnailImageUrl = fileUtils.uploadFile(thumbnailImage);
            sales.setThumbnailImage(thumbnailImageUrl);
        }

        if(imageFile != null && !imageFile.isEmpty()){
            if(sales.getImageUrl() != null && !sales.getImageUrl().isEmpty()){
                try{
                    for(String url: sales.getImageUrl()){
                        fileUtils.deleteFile(url);
                        log.info("판매글 이미지 삭제완료");
                    }
                }catch(Exception e){
                    log.error("판매글 이미지 삭제 실패");
                }
            }
            List<String> imageList = new ArrayList<>();
            for(MultipartFile image: imageFile){
                String imageUrl = fileUtils.uploadFile(image);
                imageList.add(imageUrl);
            }
            sales.setImageUrl(imageList);
        }

        sales.updateSales(Sales.builder()
                .users(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .salesStatus(dto.getStatus() != null ?SalesStatus.valueOf(dto.getStatus()) : sales.getSalesStatus())
                .artists(artists)
                .teams(teams)
                .hashtags(hashtagList)
                .ectArtists(ectArtist.isEmpty() ? sales.getEctArtists() : ectArtist)
                .ectTeams(ectTeam.isEmpty() ? sales.getEctTeams() : ectTeam)
                .build()
        );

        salesRepository.save(sales);

        List<ReadProductDto> dtoList = new ArrayList<>();
        for (Product product: sales.getProducts()) {
            dtoList.add(ReadProductDto.fromEntity(product));
        }
        return ReadOneSalesDto.fromEntity(sales, dtoList);
    }

    @Transactional
    public void deleteSales(Long categoryId, Long salesId, String username){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new CustomException(ErrorCode.SALES_NOT_FOUND));

        if(!user.equals(sales.getUsers())) throw new CustomException(ErrorCode.FORBIDDEN);

        // 이미지 삭제
        if(sales.getImageUrl() != null){
            for(String imageUrl: sales.getImageUrl()){
                fileUtils.deleteFile(imageUrl);
            }
        }
        if(sales.getThumbnailImage() != null){
            fileUtils.deleteFile(sales.getThumbnailImage());
        }

        productRepository.deleteAllBySales(sales);
        salesRepository.deleteById(salesId);
    }

    @Transactional
    public String likeSales(Long salesId, String username) {
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new CustomException(ErrorCode.SALES_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String resposne = "";
        if(sales.getSalesLikesUsers().contains(user)){
            sales.getSalesLikesUsers().remove(user);
            user.getSalesLikes().remove(sales);
            resposne="찜하기가 취소되었습니다. 현재 찜하기 수 : "+ sales.getSalesLikesUsers().size();
        }else{
            sales.getSalesLikesUsers().add(user);
            user.getSalesLikes().add(sales);
            resposne = "찜하기를 눌렀습니다. 현재 찜하기 수 : "+sales.getSalesLikesUsers().size();
        }
        return resposne;
    }
}
