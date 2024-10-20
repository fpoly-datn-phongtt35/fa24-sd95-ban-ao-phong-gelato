package com.example.datn.services.ServiceImpl;

import com.example.datn.dto.Product.ProductDetailDto;
import com.example.datn.dto.Product.ProductDto;
import com.example.datn.dto.Product.ProductSearchResultDto;
import com.example.datn.dto.Product.SearchProductDto;
import com.example.datn.entities.DiscountProduct;
import com.example.datn.entities.Product;
import com.example.datn.entities.ProductDetail;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.exceptions.ShopApiException;
import com.example.datn.repositories.ProductDetailRepo;
import com.example.datn.repositories.ProductDiscountRepo;
import com.example.datn.repositories.ProductRepo;
import com.example.datn.repositories.Specification.ProductSpecification;
import com.example.datn.services.ProductService;
import com.example.datn.utils.QrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private ProductRepo productRepo;

    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ProductDiscountRepo productDiscountRepo;

    @Override
    public Page<Product> getAllProduct(Pageable pageable0) {
        return productRepo.findAll(pageable0);
    }

    @Override
    public Page<ProductSearchResultDto> getAll(Pageable pageable) {

        return productRepo.getAll(pageable);
    }

    @Override
    public Product save(Product product) throws IOException {

        if(product.getCode().trim() == "" || product.getCode() == null) {
            Product productCurrent = productRepo.findTopByOrderByIdDesc();
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
            QrcodeService.generateQrcode(productDetail.getBarcode(), productDetail.getBarcode());
        }

        product.setPrice(minPrice);
        product.setDeleteFlag(false);
        product.setCreateDate(LocalDateTime.now());
        product.setUpdatedDate(LocalDateTime.now());
        return productRepo.save(product);
    }

    @Override
    public Product delete(Long id)  {
        Product product = productRepo.findById(id).orElseThrow( () -> new NotFoundException("Product not found"));
        product.setDeleteFlag(true);
        return productRepo.save(product);
    }

    @Override
    public Product getProductByCode(String code) {
        Product product = productRepo.findByCode(code);
        if(product != null) {

            return product;
        }
        return null;
    }

    @Override
    public boolean existsByCode(String code) {
        return productRepo.existsByCode(code);
    }

    public Page<Product> search(String productName, Pageable pageable) {
        Page<Product> page = productRepo.searchProductName(productName, pageable);
        return page;
    }

    @Override
    public Page<ProductSearchResultDto> listSearchProduct(String maSanPham, String tenSanPham, Long nhanHang, Long chatLieu, Long theLoai,Integer trangThai, Pageable pageable) {
        Page<ProductSearchResultDto> productSearchResultDtos = productRepo.listSearchProduct(maSanPham,tenSanPham,nhanHang,chatLieu,theLoai,trangThai,pageable);
        return productSearchResultDtos;
    }

    @Override
    public Page<Product> getAllByStatus(int status, Pageable pageable) {
        return productRepo.findAllByStatusAndDeleteFlag(status, false, pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Page<ProductDto> searchProduct(SearchProductDto searchDto, Pageable pageable) {
        Specification<Product> spec = new ProductSpecification(searchDto);
        Page<Product> products = productRepo.findAll(spec, pageable);
        return products.map(this::convertToDto);
    }

    @Override
    public Page<ProductDto> getAllProductApi(Pageable pageable) {
        Page<Product> productPage = productRepo.findAllByDeleteFlagFalse(pageable);
        return productPage.map(this::convertToDto);
    }

    @Override
    public ProductDto getProductByBarcode(String barcode) {
        ProductDetail productDetail = productDetailRepo.findByBarcodeContainingIgnoreCase(barcode);
        if(productDetail == null) {
            throw  new ShopApiException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có mã vạch: " + barcode);
        }
        Product product = productDetail.getProduct();
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setCategoryName(product.getCategory().getName());
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
        DiscountProduct discountProduct = productDiscountRepo.findValidDiscountByProductDetailId(productDetail.getId());
        if(discountProduct != null) {
//            Date endDate = productDiscount.getEndDate();
//            Date currentDate = new Date();
//            if (currentDate.compareTo(endDate) > 0) {
//            }
            productDetailDto.setDiscountedPrice(discountProduct.getDiscountedAmount());

        }
        productDto.setProductDetailDto(productDetailDtoList);

        return productDto;
    }

    @Override
    public List<ProductDto> getAllProductNoPaginationApi(SearchProductDto searchRequest) {
        Specification<Product> spec = new ProductSpecification(searchRequest);
        List<Product> products = productRepo.findAll(spec);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getByProductDetailId(Long detailId) {
        return convertToDto(productRepo.findByProductDetail_Id(detailId));
    }


    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setCategoryName(product.getCategory().getName());
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
            DiscountProduct discountProduct = productDiscountRepo.findValidDiscountByProductDetailId(productDetail.getId());
            if(discountProduct != null) {
                productDto.setDiscounted(true);
                productDetailDto.setDiscountedPrice(discountProduct.getDiscountedAmount());
                if (discountProduct.getDiscountedAmount() < priceMin) {
                    priceMin = discountProduct.getDiscountedAmount();
                }
            }
            productDetailDtoList.add(productDetailDto);
        }
        productDto.setPriceMin(priceMin);
        productDto.setProductDetailDto(productDetailDtoList);
        return productDto;
    }
}
