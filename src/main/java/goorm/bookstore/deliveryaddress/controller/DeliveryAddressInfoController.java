package goorm.bookstore.deliveryaddress.controller;

import goorm.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import goorm.bookstore.deliveryaddress.service.DeliveryAddressInfoService;
import goorm.bookstore.user.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/users/deliveryaddressinfo")
public class DeliveryAddressInfoController {

    private final DeliveryAddressInfoService deliveryAddressInfoService;

    @Autowired
    public DeliveryAddressInfoController(DeliveryAddressInfoService deliveryAddressInfoService) {
        this.deliveryAddressInfoService = deliveryAddressInfoService;
    }

    @GetMapping
    public String listPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        // 사용자의 배송지 리스트 반환.
        List<DeliveryAddressInfoDto> addressList = deliveryAddressInfoService.findByUser(userDetails.getUsername());

        model.addAttribute("addressList", addressList);

        return "deliveryaddressinfo/list";
    }

    @GetMapping("/create")
    public String addAddressPage(Model model) {
        System.out.println("DeliveryAddressInfoController.addAddressPage");
        model.addAttribute("deliveryAddressInfoDto", new DeliveryAddressInfoDto());
        return "deliveryaddressinfo/create";
    }

    @PostMapping()
    public String addProcess(
            @Validated @ModelAttribute DeliveryAddressInfoDto deliveryAddressInfoDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        if (bindingResult.hasErrors()) {
            return "deliveryaddressinfo/create";
        }

        // 정상 폼이 도착했을 경우
        deliveryAddressInfoService.save(userDetails.getUsername(), deliveryAddressInfoDto);
        return "redirect:/users/deliveryaddressinfo";
    }


    @ResponseBody
    @GetMapping("/checkDuplicateName")
    public ResponseEntity<Map<String, String>> checkDuplicatedName(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @RequestParam("addressName") String addressName) {

        System.out.println("DeliveryAddressInfoController.checkDuplicatedName");
        HashMap<String, String> response = new HashMap<>();

        // 유저 테이블에서 해당되는 유저를 검색 후 이 주소지 이름이 있는지 검색해야함.
        boolean isDuplicated = deliveryAddressInfoService.checkDuplicateAddressName(userDetails.getUsername(), addressName);

        if (isDuplicated) {
            response.put("status", "error");
            response.put("message", "이미 사용 중인 이름입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        response.put("status", "success");
        response.put("message", "사용 가능합니다..");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/edit/{addressName}")
    public String editPage(
            @PathVariable("addressName") String addressName,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        List<DeliveryAddressInfoDto> addressListByUser = deliveryAddressInfoService.findByUser(userDetails.getUsername());

        for (DeliveryAddressInfoDto addressDto : addressListByUser) {
            if (addressDto.getAddressName().equals(addressName)) {
                model.addAttribute("addressDto", addressDto);
                return "deliveryaddressinfo/update";
            }
        }
        return "deliveryaddressinfo/update";
    }

    @PostMapping("/edit/{addressName}")
    public String editProcess(
            @Validated @ModelAttribute DeliveryAddressInfoDto addressDto,
            BindingResult bindingResult,
            @PathVariable("addressName") String addressName,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            return "deliveryaddressinfo/update";
        }

        // 정상 폼이 도착했을 경우
        deliveryAddressInfoService.update(userDetails.getUsername(), addressDto, addressName);
        return "redirect:/users/deliveryaddressinfo";
    }

    @PostMapping("/delete/{addressName}")
    public String deleteProcess(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("addressName") String addressName) {

        deliveryAddressInfoService.delete(userDetails.getUsername(), addressName);
        return "redirect:/users/deliveryaddressinfo";
    }
}

