package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.product.*;
import com.happiday.Happi_Day.domain.entity.product.dto.*;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ProductRepository;
import com.happiday.Happi_Day.domain.repository.SalesCategoryRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesService {
    private final UserRepository userRepository;
    private final SalesCategoryRepository salesCategoryRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final FileUtils fileUtils;

    @Transactional
    public ReadOneSalesDto createSales(Long categoryId, WriteSalesDto dto, MultipartFile thumbnailImage,List<MultipartFile> imageFile, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 판매글 카테고리
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Product> productList = new ArrayList<>();

        Sales newSales = Sales.builder()
                .users(user)
                .salesStatus(SalesStatus.ON_SALE)
                .salesCategory(category)
                .name(dto.getName())
                .description(dto.getDescription())
                .products(productList)
                .salesLikesUsers(new ArrayList<>())
                .imageUrl(new ArrayList<>())
                .build();

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

        Sales newSalesArticle = salesRepository.save(newSales);

        // products 저장
        dto.getProducts().forEach((key, value)->{
            Product newProduct = Product.createProduct(key, value, newSalesArticle);
            productList.add(newProduct);
            productRepository.save(newProduct);
        });

        // 판매글에 products 등록
        newSales.updateProduct(productList);
        salesRepository.save(newSales);

        List<ReadProductDto> dtoList = new ArrayList<>();
        for (Product product: newSales.getProducts()) {
            dtoList.add(ReadProductDto.fromEntity(product));
        }

        ReadOneSalesDto response = ReadOneSalesDto.fromEntity(newSales,dtoList);
        return response;
    }

    public List<ReadListSalesDto> readSalesList(Long categoryId){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Sales> salesList = salesRepository.findAllBySalesCategory(category);
        List<ReadListSalesDto> responseSalesList = new ArrayList<>();

        for (Sales sales: salesList) {
            responseSalesList.add(ReadListSalesDto.fromEntity(sales));
        }

        return responseSalesList;
    }

    public ReadOneSalesDto readSalesOne(Long categoryId, Long salesId){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<ReadProductDto> dtoList = new ArrayList<>();
        for (Product product: sales.getProducts()) {
            dtoList.add(ReadProductDto.fromEntity(product));
        }

        return ReadOneSalesDto.fromEntity(sales, dtoList);
    }

    @Transactional
    public ReadOneSalesDto updateSales(Long salesId, UpdateSalesDto dto, MultipartFile thumbnailImage, List<MultipartFile> imageFile, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // user 확인
        if(!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

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

        // TODO 아티스트 추가예정

        sales.updateSales(dto.toEntity());
//        salesRepository.save(sales);

        List<ReadProductDto> dtoList = new ArrayList<>();
        for (Product product: sales.getProducts()) {
            dtoList.add(ReadProductDto.fromEntity(product));
        }
        return ReadOneSalesDto.fromEntity(sales, dtoList);
    }

    @Transactional
    public void deleteSales(Long categoryId, Long salesId, String username){
        SalesCategory category = salesCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // 이미지 삭제
        for(String imageUrl: sales.getImageUrl()){
            fileUtils.deleteFile(imageUrl);
        }
        fileUtils.deleteFile(sales.getThumbnailImage());

        productRepository.deleteAllBySales(sales);
        salesRepository.deleteById(salesId);
    }

    @Transactional
    public String likeSales(Long salesId, String username) {
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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
//        salesRepository.save(sales)
        return resposne;
    }
}
