package goorm.bookstore.cart.controller;

import goorm.bookstore.cart.dto.AddCartDto;
import goorm.bookstore.cart.dto.CartDto;
import goorm.bookstore.cart.service.CartService;
import goorm.bookstore.user.service.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping
    public String cartPage(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        List<CartDto> cartDtoList = cartService.findAll(user.getUsername());

        model.addAttribute("cartDtoList", cartDtoList);
        return "user/cart";
    }

    @PostMapping("/add")
    public String addCartProcess(@ModelAttribute @Valid AddCartDto addCartDto) {
        cartService.save(addCartDto);
        return "redirect:/users/carts";
    }
}
