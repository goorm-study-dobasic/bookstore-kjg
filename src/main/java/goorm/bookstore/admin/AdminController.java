package goorm.bookstore.admin;

import goorm.bookstore.blacklist.dto.AddBlacklistDto;
import goorm.bookstore.blacklist.dto.BlacklistDto;
import goorm.bookstore.blacklist.service.BlacklistService;
import goorm.bookstore.user.dto.UserDto;
import goorm.bookstore.user.service.CustomUserDetails;
import goorm.bookstore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BlacklistService blacklistService;

    @Autowired
    public AdminController(UserService userService, BlacklistService blacklistService) {
        this.userService = userService;
        this.blacklistService = blacklistService;
    }

    @GetMapping
    public String adminHome(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println("authority = " + authority.getAuthority());
        }
        return "admin/home";
    }

    @GetMapping("/users")
    public String userListPage(Model model) {
        List<UserDto> users = userService.findByUseYn();
        model.addAttribute("users", users);
        return "admin/userlist";
    }

    @GetMapping("/blacklist")
    public String blacklistPage(Model model) {
        List<BlacklistDto> blacklistDtos = blacklistService.findAll();
        model.addAttribute("blacklists", blacklistDtos);
        return "admin/blacklist";
    }

    @GetMapping("/blacklist/add/{userEmail}")
    public String addBlacklistPage(@PathVariable("userEmail") String userEmail,
                                   Model model) {

        AddBlacklistDto blacklistDto = new AddBlacklistDto();
        blacklistDto.setEmail(userEmail);
        model.addAttribute("blacklistDto", blacklistDto);
        return "admin/blacklist/add";
    }


    @PostMapping("/blacklist/add")
    public String addBlackListProcess(
            @Validated @ModelAttribute BlacklistDto blacklistDto,
            BindingResult bindingResult
           ) {

        // 검증
        if (bindingResult.hasErrors()) {
            return "admin/blacklist/add";
        }

        // 정상
        // 유저 넘기고 블랙리스트 등록
        blacklistService.save(blacklistDto);
        return "redirect:/admin/users";
    }

    @PostMapping("/blacklist/delete/{email}")
    public String deleteBlacklistProcess(@PathVariable("email") String email) {

        // 이메일 넘기면 끝
        blacklistService.delete(email);
        return "redirect:/admin/blacklist";
    }
}
