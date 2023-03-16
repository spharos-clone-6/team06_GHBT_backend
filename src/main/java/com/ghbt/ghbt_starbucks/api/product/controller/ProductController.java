package com.ghbt.ghbt_starbucks.api.product.controller;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.api.product.service.IProductService;
import com.ghbt.ghbt_starbucks.api.product_and_category.model.ProductAndCategory;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.awt.Menu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")

public class ProductController {

    private final IProductService iProductService;
    private final IProductRepository iProductRepository;

    @PostMapping // 상품 추가
    public ResponseEntity addProduct(@RequestBody List<RequestProduct> requestProductList) {
        for (RequestProduct requestProduct : requestProductList) {
            iProductService.addProduct(requestProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestProductList);
    }

    @GetMapping("/{productId}") // 단건 조회
    public ResponseProduct getProduct(@PathVariable Long productId) {
        return iProductService.getProduct(productId);
    }

    @GetMapping("/not/page") // 전체 상품 출력
    public List<ResponseProduct> getAllProduct() {
        return iProductService.getAllProduct();
    }

    @GetMapping("/search-category") // 카테고리별 상품 조회
    public ResponseEntity findAllProductType(@Param("name") String name) {
        List<IProductListByCategory> searchProduct = iProductService.getProductForCategory(name);
        return ResponseEntity.status(HttpStatus.OK)
            .body(searchProduct);
    }

    @GetMapping("/search/{name}") // 검색어로 상품 검색
    public ResponseEntity findProduct(@PathVariable String name) {
        List<IProductSearch> searchProduct = iProductService.getSearchProduct(name);
        return new ResponseEntity<>(searchProduct, HttpStatus.OK);
    }

    @GetMapping // 전체 상품 조회 페이지
    public Page<Product> productPaging(final Pageable pageable) {
        return iProductService.getList(pageable);
    }

    @GetMapping("/search/type/{name}") // 검색 상품의 대분류 카테고리 갯수
    public List<IMenubar> prod(@PathVariable("name") String name) {
        return iProductService.menubarList(name);
    }
//    @GetMapping("/product/{keyWord}") // 검색 상품 조회 페이지
//    public Page<Product> getAllProductWithPageByQueryMethod(@PathVariable String keyWord) {
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        return iProductRepository.findByNameContains(keyWord, pageRequest);
//    }
//
//    @GetMapping("/searching/{keyWord}")
//    public ResponseEntity searchingCategoryList(@PathVariable String keyWord) {
//        List<List<ProductAndCategory>> searchingList = iProductService.searchingCategoryList(keyWord);
//        return ResponseEntity.status(HttpStatus.OK).body(searchingList);
//    }

//    @GetMapping("/searching/{name}") // 검색어로 상품 조회(상품+카테고리)
//    public ResponseEntity searchingCategoryList(@PathVariable String name) {
//        List<List<Product>> searching = iProductService.searchingCategoryList(name);
//        List<IMenubar> menubar = iProductRepository.findByMenubarList(searching);
//        return ResponseEntity.status(HttpStatus.OK).body(menubar);
//    }

    @PutMapping("/{product_id}") // 상품 업데이트
    public ResponseEntity updateProduct(
        @PathVariable("product_id") Long productId,
        @RequestBody RequestProduct requestProduct) {
        iProductService.updateProduct(productId, requestProduct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{product_id}") // 상품 삭제
    public ResponseEntity deleteProduct(
        @PathVariable("product_id") Long ProductId) {
        iProductService.deleteProduct(ProductId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
