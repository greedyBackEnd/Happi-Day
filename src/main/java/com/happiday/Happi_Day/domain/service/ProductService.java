package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.product.dto.CreateProductDto;
import com.happiday.Happi_Day.domain.entity.product.dto.ReadProductDto;
import com.happiday.Happi_Day.domain.entity.product.dto.UpdateProductDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ProductRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final UserRepository userRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ReadProductDto createProduct(Long salesId, CreateProductDto productDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // user 확인
        if(!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Product newProduct = Product.createProduct(productDto.getName(),productDto.getPrice(),sales, productDto.getStock());
        productRepository.save(newProduct);
        return ReadProductDto.fromEntity(newProduct);
    }

    @Transactional
    public ReadProductDto updateProduct(Long salesId, Long productId, UpdateProductDto productDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!sales.getProducts().contains(product)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // user 확인
        if(!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        product.update(productDto.toEntity());
        productRepository.save(product);

        return ReadProductDto.fromEntity(product);
    }

    @Transactional
    public void deleteProduct(Long salesId, Long productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!sales.getProducts().contains(product)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // user 확인
        if(!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        productRepository.deleteById(productId);
    }
}
