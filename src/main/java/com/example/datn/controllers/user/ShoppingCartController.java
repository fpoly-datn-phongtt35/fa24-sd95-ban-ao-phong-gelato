package com.example.datn.controllers.user;



import com.example.datn.dto.Account.AccountDto;
import com.example.datn.dto.AddressShipping.AddressShippingDto;
import com.example.datn.dto.Cart.CartDto;
import com.example.datn.dto.DiscountCode.DiscountCodeDto;
import com.example.datn.entities.Account;
import com.example.datn.exceptions.NotFoundException;
import com.example.datn.exceptions.ShopApiException;
import com.example.datn.repositories.AccountRepository;
import com.example.datn.services.AccountService;
import com.example.datn.services.AddressShippingService;
import com.example.datn.services.BillService;
import com.example.datn.services.CartService;
import com.example.datn.services.serviceImpl.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ShoppingCartController {
    private final CartService cartService;

    private final BillService billService;

    private final DiscountCodeService discountCodeService;

    private final AddressShippingService addressShippingService;

    @Autowired
    private AccountService accountService;

    public ShoppingCartController(CartService cartService, BillService billService, DiscountCodeService discountCodeService, AddressShippingService addressShippingService) {
        this.cartService = cartService;
        this.billService = billService;
        this.discountCodeService = discountCodeService;
        this.addressShippingService = addressShippingService;
    }

    @GetMapping("/shoping-cart")
    public String viewShoppingCart(Model model) {
        List<CartDto> cartDtoList = cartService.getAllCartByAccountId();
        Page<DiscountCodeDto> discountCodeList = discountCodeService.getAllAvailableDiscountCode(PageRequest.of(0, 15));
        List<AddressShippingDto> addressShippingDtos = addressShippingService.getAddressShippingByAccountId();
        AccountDto accountDto = accountService.getAccountLogin();
        model.addAttribute("discountCodes", discountCodeList.getContent());
        model.addAttribute("addressShippings", addressShippingDtos);
        model.addAttribute("profile", accountDto);
        model.addAttribute("carts", cartDtoList);
        return "user/shoping-cart";
    }

    @ResponseBody
    @PostMapping("/api/addToCart")
    public void addToCart(@RequestBody CartDto cartDto) throws NotFoundException {
        cartService.addToCart(cartDto);
    }

    @ResponseBody
    @PostMapping("/api/deleteCart/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    @ResponseBody
    @PostMapping("/api/updateCart")
    public void updateCart(@RequestBody CartDto cartDto) throws NotFoundException {
        cartService.updateCart(cartDto);
    }

    @ResponseBody
    @RequestMapping(value = "/shoping-cart/update-profile", method = RequestMethod.POST)
    public ResponseEntity<?> updateProfileShopingCart(@RequestBody AccountDto accountDto){
        try {
            accountService.updateProfile(accountDto);
            return ResponseEntity.ok("Cập nhật thông tin thành công !");
        } catch (ShopApiException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Cập nhật thông tin thất bại !");
        }
    }
}

