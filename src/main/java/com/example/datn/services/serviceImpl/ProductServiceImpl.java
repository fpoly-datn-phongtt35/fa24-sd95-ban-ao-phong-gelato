package com.example.datn.services.serviceImpl;


import com.example.datn.dto.Product.ProductDetailDto;
import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.ProductSearchDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.entities.ProductDiscount;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.exceptions.ShopApiException;
import com.example.datn.repositories.ProductDetailRepository;
import com.example.datn.repositories.ProductDiscountRepository;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.repositories.Specification.ProductSpecification;
import com.example.datn.services.ProductService;
import com.example.datn.utils.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDiscountRepository productDiscountRepository;

    @Override
    public Page<Product> getAllProduct(Pageable pageable0) {
        return productRepository.findAll(pageable0);
    }

    @Override
    public Page<ProductSearchDto> getAll(Pageable pageable) {

        return productRepository.getAll(pageable);
    }

    @Override
    public Product save(Product product) throws IOException {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cập nhật các thuộc tính
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setMaterial(product.getMaterial());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setGender(product.getGender());
        existingProduct.setDescribe(product.getDescribe());

        // Cập nhật danh sách chi tiết sản phẩm nếu có
        if (product.getProductDetails() != null) {
            existingProduct.getProductDetails().clear();
            existingProduct.getProductDetails().addAll(product.getProductDetails());
        }

        // Cập nhật danh sách ảnh nếu có
        if (product.getImage() != null) {
            existingProduct.getImage().clear();
            existingProduct.getImage().addAll(product.getImage());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product add(Product product) throws IOException {
        if(product.getCode().trim() == "" || product.getCode() == null) {
            Product productCurrent = productRepository.findTopByOrderByIdDesc();
            Long nextCode = (productCurrent == null) ? 1 : productCurrent.getId() + 1;
            String productCode = "SP" + String.format("%04d", nextCode);
            product.setCode(productCode);
        }

        Double minPrice = Double.valueOf(1000000000);
        for (ProductDetail productDetail:
                product.getProductDetails()) {
            if(productDetail.getPrice() < minPrice) {
                minPrice = productDetail.getPrice();
            }
            QRCodeService.generateQRCode(productDetail.getBarcode(), productDetail.getBarcode());
        }

        product.setPrice(minPrice);
        product.setDeleteFlag(false);
        product.setCreateDate(LocalDateTime.now());
        product.setUpdatedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id)  {
        Product product = productRepository.findById(id).orElseThrow( () -> new NotFoundException("Product not found"));
        product.setDeleteFlag(true);
        return productRepository.save(product);
    }

    @Override
    public Product getProductByCode(String code) {
        Product product = productRepository.findByCode(code);
        if(product != null) {

            return product;
        }
        return null;
    }

    @Override
    public boolean existsByCode(String code) {
        return productRepository.existsByCode(code);
    }

    public Page<Product> search(String productName, Pageable pageable) {
        Page<Product> page = productRepository.searchProductName(productName, pageable);
        return page;
    }

    @Override
    public Page<ProductSearchDto> listSearchProduct(String maSanPham, String tenSanPham, Long nhanHang, Long chatLieu, Long theLoai, Integer trangThai, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductSearchDto> listSearchProduct(String maSanPham, String tenSanPham, Long nhanHang, Long chatLieu, Integer trangThai, Pageable pageable) {
        Page<ProductSearchDto> productSearchDtos = productRepository.listSearchProduct(maSanPham,tenSanPham,nhanHang,chatLieu,trangThai,pageable);
        return productSearchDtos;
    }

    @Override
    public Page<Product> getAllByStatus(int status, Pageable pageable) {
        return productRepository.findAllByStatusAndDeleteFlag(status, false, pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<ProductDto> searchProduct(SearchProductDto searchDto, Pageable pageable) {
        Specification<Product> spec = new ProductSpecification(searchDto);
        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(this::convertToDto);
    }

    @Override
    public Page<ProductDto> getAllProductApi(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByDeleteFlagFalse(pageable);
        return productPage.map(this::convertToDto);
    }

    @Override
    public ProductDto getProductByBarcode(String barcode) {
        ProductDetail productDetail = productDetailRepository.findByBarcodeContainingIgnoreCase(barcode);
        if(productDetail == null) {
            throw  new ShopApiException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có mã vạch: " + barcode);
        }
        Product product = productDetail.getProduct();
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setImageUrl(product.getImage().get(0).getLink());
        productDto.setDescription(product.getDescribe());
        productDto.setPriceMin(product.getProductDetails().get(0).getPrice());
        productDto.setCreateDate(product.getCreateDate());
        productDto.setUpdatedDate(product.getUpdatedDate());

        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(productDetail.getId());
        productDetailDto.setProductId(product.getId());
        productDetailDto.setColor(productDetail.getColor());
        productDetailDto.setSize(productDetail.getSize());
        productDetailDto.setPrice(productDetail.getPrice());
        productDetailDto.setQuantity(productDetail.getQuantity());
        productDetailDto.setBarcode(productDetail.getBarcode());
        productDetailDtoList.add(productDetailDto);
        ProductDiscount productDiscount = productDiscountRepository.findValidDiscountByProductDetailId(productDetail.getId());
        if(productDiscount != null) {
//            Date endDate = productDiscount.getEndDate();
//            Date currentDate = new Date();
//            if (currentDate.compareTo(endDate) > 0) {
//            }
            productDetailDto.setDiscountedPrice(productDiscount.getDiscountedAmount());

        }
        productDto.setProductDetailDtos(productDetailDtoList);

        return productDto;
    }

    @Override
    public List<ProductDto> getAllProductNoPaginationApi(SearchProductDto searchRequest) {
        Specification<Product> spec = new ProductSpecification(searchRequest);
        List<Product> products = productRepository.findAll(spec);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getByProductDetailId(Long detailId) {
        return convertToDto(productRepository.findByProductDetail_Id(detailId));
    }

    @Override
    public List<ProductDto> getTop10BestSellingProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> result = productRepository.findTop5BestSellingProducts(pageable);

        return result.stream().map(row -> {
            ProductDto dto = new ProductDto();
            dto.setName((String) row[0]);
            dto.setTotalSold((Long) row[1]);

            // Tạo imageUrl giả định dựa trên product code
            dto.setCode((String) row[2]);
            dto.setImageUrl("/images/" + (String) row[2] + ".jpg"); // URL giả định

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getTop10NewestProducts() {
        Pageable pageable = PageRequest.of(0, 10); // Lấy 10 sản phẩm mới nhất
        List<Product> products = productRepository.findTop10NewProducts(pageable);

        return products.stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setCode(product.getCode());
            dto.setName(product.getName());
            dto.setCreateDate(product.getCreateDate());
            dto.setPriceMin(product.getPrice());
            dto.setImageUrl("/images/" + product.getCode() + ".jpg");
            return dto;
        }).collect(Collectors.toList());
    }




    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setImageUrl(product.getImage().get(0).getLink());
        productDto.setDescription(product.getDescribe());
        productDto.setCreateDate(product.getCreateDate());
        productDto.setUpdatedDate(product.getUpdatedDate());

        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
        Double priceMin = Double.valueOf(100000000);
        for (ProductDetail productDetail:
                product.getProductDetails()) {
            if(productDetail.getPrice() < priceMin) {
                priceMin = productDetail.getPrice();
            }
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setId(productDetail.getId());
            productDetailDto.setProductId(product.getId());
            productDetailDto.setColor(productDetail.getColor());
            productDetailDto.setSize(productDetail.getSize());
            productDetailDto.setPrice(productDetail.getPrice());
            productDetailDto.setQuantity(productDetail.getQuantity());
            productDetailDto.setBarcode(productDetail.getBarcode());
            ProductDiscount productDiscount = productDiscountRepository.findValidDiscountByProductDetailId(productDetail.getId());
            if(productDiscount != null) {
                productDto.setDiscounted(true);
                productDetailDto.setDiscountedPrice(productDiscount.getDiscountedAmount());
                if (productDiscount.getDiscountedAmount() < priceMin) {
                    priceMin = productDiscount.getDiscountedAmount();
                }
            }
            productDetailDtoList.add(productDetailDto);
        }
        productDto.setPriceMin(priceMin);
        productDto.setProductDetailDtos(productDetailDtoList);
        return productDto;
    }

}
