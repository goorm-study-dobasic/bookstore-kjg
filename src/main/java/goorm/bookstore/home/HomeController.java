package goorm.bookstore.home;

import goorm.bookstore.inventory.dto.InventoryForUserDto;
import goorm.bookstore.inventory.service.UserInventoryService;
import goorm.bookstore.user.dto.JoinUserDto;
import goorm.bookstore.user.dto.LoginUserDto;
import goorm.bookstore.user.service.CustomUserDetails;
import goorm.bookstore.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {

    private final UserService userService;
    private final UserInventoryService userInventoryService;

    @Autowired
    public HomeController(UserService userService, UserInventoryService userInventoryService) {
        this.userService = userService;
        this.userInventoryService = userInventoryService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails,  Model model) {
        // 로그인 상태라면 유저정보, 로그아웃 페이지 반환
        // 비로그인 상태라면 로그인 회원가입 페이지 반환
        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        }

        // List<InventoryForUserDto> all = userInventoryService.findAll();


        return "index";
    }

    @GetMapping("/join")
    public String joinPage(@AuthenticationPrincipal CustomUserDetails userDetails, JoinUserDto joinUserDto, Model model) {

        // 이미 로그인 된 사용자의 경우
        if (userDetails != null) {
            return "redirect:/";
        }

        model.addAttribute("joinUserDto", joinUserDto);
        return "join";
    }

    @PostMapping("/join")
    public String joinProcess(@Validated @ModelAttribute JoinUserDto joinUserDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "join";
        }

        userService.save(joinUserDto);
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/join/duplicate/email")
    public ResponseEntity<Map<String, String>> checkDuplicatedEmail(@RequestParam("email") String email) {

        System.out.println("HomeController.checkDuplicatedEmail");
        boolean isDuplicated = userService.checkDuplicateEmail(email);

        HashMap<String, String> response = new HashMap<>();

        if (isDuplicated) {
            response.put("status", "error");
            response.put("message", "이미 사용 중인 이메일입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            response.put("status", "success");
            response.put("message", "사용 가능한 이메일입니다.");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginUserDto", new LoginUserDto());
        return "login";
    }

    /*@PostMapping("/loginProc")
    public String loginProcess(@Validated @ModelAttribute LoginUserDto loginUserDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            // 인증 토큰 생성 -> 인증 객체한테 인증하라고 시킴. -> 내부적으로 로드바이유저네임 -> 유저디테일 가져와서 -> 입력값과 비교 -> 로그인 성공 시 authentication 객체 반환.
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // context 저장.
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticate);

             // 이제 세션에 저장해야 함.
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            System.out.println("SPRING_SECURITY_CONTEXT_KEY = " + HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

            return "redirect:/";
        } catch (Exception e) {
            return "login";
        }
    }*/
    /*

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }*/
}
