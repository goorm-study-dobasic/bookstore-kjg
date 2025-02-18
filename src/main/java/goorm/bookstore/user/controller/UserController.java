package goorm.bookstore.user.controller;

import goorm.bookstore.user.dto.MyPageDto;
import goorm.bookstore.user.dto.UserDto;
import goorm.bookstore.user.repository.UserRepository;
import goorm.bookstore.user.service.CustomUserDetails;

import goorm.bookstore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        model.addAttribute("myPageDto", createMyPageDto(userDetails));

        return "user/detail";
    }

    @GetMapping("/edit")
    public String editPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {


        model.addAttribute("myPageDto", createMyPageDto(userDetails));

        return "user/edit";
    }

    @PostMapping
    public String editProcess(@Validated @ModelAttribute MyPageDto myPageDto, BindingResult bindingResult) {
        // 수정..
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.save(myPageDto);
        return "redirect:/users";
    }


    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> resignProcess(@RequestBody Map<String, String> requestMap) {

        String userEmail = requestMap.get("email");
        userService.deactivate(userEmail);

        return ResponseEntity.ok("회원 비활성화");

    }

    private MyPageDto createMyPageDto(CustomUserDetails userDetails) {

        return MyPageDto.builder()
                .nickname(userDetails.getNickname())
                .phone(userDetails.getPhone())
                .email(userDetails.getUsername())
                .mileage(userDetails.getMileage())
                .grade(userDetails.getGrade())
                .build();
    }

}
