package test.testcode.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.testcode.spring.api.service.product.response.ProductResponse;
import test.testcode.spring.domain.product.Product;
import test.testcode.spring.domain.product.ProductRepository;
import test.testcode.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> geAllSellingProducts(){
        List<Product> products = productRepository.findALlBySellingStatusIn(ProductSellingStatus.forDisplay());
        return products.stream()
               //.map(product -> ProductResponse.of(product))
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
