package test.testcode.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.testcode.spring.api.controller.product.dto.request.ProductCreateRequest;
import test.testcode.spring.api.service.product.request.ProductCreateServiceRequest;
import test.testcode.spring.api.service.product.response.ProductResponse;
import test.testcode.spring.domain.product.Product;
import test.testcode.spring.domain.product.ProductRepository;
import test.testcode.spring.domain.product.ProductSellingStatus;
import test.testcode.spring.domain.product.ProductType;

import java.util.List;
import java.util.stream.Collectors;

import static test.testcode.spring.domain.product.ProductSellingStatus.SELLING;
import static test.testcode.spring.domain.product.ProductType.HANDMADE;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product savedProduct = request.toEntity(nextProductNumber);
        productRepository.save(savedProduct);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> geAllSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return products.stream()
               //.map(product -> ProductResponse.of(product))
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if(latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumber);
    }
}
