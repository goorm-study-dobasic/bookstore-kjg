package goorm.bookstore.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.bookstore.inventory.domain.InventoryStatus;
import goorm.bookstore.inventory.dto.AddInventoryDto;
import goorm.bookstore.inventory.dto.InventoryForAdminDto;
import goorm.bookstore.inventory.dto.SearchBookDto;
import goorm.bookstore.inventory.dto.UpdateInventoryDto;
import goorm.bookstore.inventory.service.AdminInventoryService;
import goorm.bookstore.user.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/inventory")
public class AdminInventoryController {


    private static final String API_KEY = "e0b415dd4c54c47b8b91b3f0e643c76e";
    private static final String SEARCH_BOOK_URL = "https://dapi.kakao.com/v3/search/book";
    private final AdminInventoryService adminInventoryService;


    @Autowired
    public AdminInventoryController(AdminInventoryService adminInventoryService) {
        this.adminInventoryService = adminInventoryService;
    }


    @GetMapping
    public String listPage(Model model) {

        List<InventoryForAdminDto> inventoryForAdminDtoList = adminInventoryService.findAll();
        model.addAttribute("inventoryForAdminDtoList", inventoryForAdminDtoList);

        return "inventory";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {

        model.addAttribute("searchBookDto", new SearchBookDto());
        return "inventory/search";
    }


    @GetMapping("/search/api") // "/api/book/query="검색어"&page=3
    public String bookSearchAPI(
            @Validated @ModelAttribute SearchBookDto searchBookDto,
            BindingResult bindingResult,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model ) throws JsonProcessingException {


        if (bindingResult.hasErrors()) {
            return "inventory/search";
        }

        String query = searchBookDto.getQuery();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);


        URI uri = UriComponentsBuilder.fromUriString(SEARCH_BOOK_URL)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("size", 10)
                .encode(StandardCharsets.UTF_8)
                .build() // UriComponent
                .toUri(); // Uri

        // 단순 문자열
        ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);
        int totalPages = 1;


        // spring 에서는 ObjectMapper 를 사용하는 것이 일반적이다.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode documentsNode = root.path("documents");

        // 305 / 10.0 -> 30.5 -> 올림 -> 31
        totalPages = (int) Math.ceil(root.path("meta").get("total_count").asInt() / 10.0);

        if (page > totalPages) {
            page = totalPages;
        }

        List<AddInventoryDto> searchResult = new ArrayList<>();

        for (JsonNode jsonNode : documentsNode) {
            searchResult.add(AddInventoryDto.builder()
                    .thumbnail(jsonNode.path("thumbnail").asText())
                    .title(jsonNode.path("title").asText())
                    .isbn(jsonNode.path("isbn").asText())
                    .publisher(jsonNode.path("publisher").asText())
                    .build());
        }

        // 검색창 전용
        model.addAttribute("searchBook", new SearchBookDto(query));


        // 검색결과 / 페이징 전용
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "inventory/search";
    }


    @GetMapping("/add")
    public String addBookPage(@RequestParam("isbn") String isbn, Model model) throws JsonProcessingException {

        String firstIsbn = getIsbn(isbn);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(SEARCH_BOOK_URL)
                .queryParam("query", firstIsbn)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);


        model.addAttribute("addInventoryDto", apiResponseToAddInventoryDto(response));
        return "inventory/addForm";
    }

    @PostMapping("/add")
    public String addBookProcess(@Validated @ModelAttribute AddInventoryDto addInventoryDto,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal CustomUserDetails userDetails ) {

        if (bindingResult.hasErrors()) {
            return "inventory/addForm";
        }

        // 없다면 추가.
        adminInventoryService.save(addInventoryDto,userDetails.getUsername());

        return "redirect:/admin/inventory";
    }


    @GetMapping("/edit/{inventoryId}")
    public String editPage(@PathVariable("inventoryId") Long inventoryId, Model model) {
        InventoryForAdminDto inventoryForAdminDto = adminInventoryService.findById(inventoryId);

        UpdateInventoryDto updateInventoryDto = UpdateInventoryDto.builder()
                .inventoryId(inventoryForAdminDto.getInventoryId())
                .status(inventoryForAdminDto.getStatus())
                .quantity(inventoryForAdminDto.getQuantity()).build();

        model.addAttribute("inventoryForAdminDto", inventoryForAdminDto);
        model.addAttribute("updateInventoryDto", updateInventoryDto);
        return "inventory/editForm";
    }


    @PostMapping("/edit")
    public String editProcess(@Validated @ModelAttribute UpdateInventoryDto updateInventoryDto,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("inventoryForAdminDto", adminInventoryService.findById(updateInventoryDto.getInventoryId()));
            return "inventory/editForm";
        }

        // 수정
        adminInventoryService.update(updateInventoryDto, userDetails.getUsername());
        redirectAttributes.addAttribute("inventoryId", updateInventoryDto.getInventoryId());
        return "redirect:/admin/inventory/edit/{inventoryId}";
    }

    @PostMapping("/delete/{inventoryId}")
    public String deleteProcess(@PathVariable("inventoryId") Long id) {
        adminInventoryService.delete(id);
        return "redirect:/admin/inventory";
    }

    private static String getIsbn(String isbn) {

        for (String s : isbn.split(" ")) {
            if (!s.isEmpty()) {
                return s;
            }
        }
        return null;
    }

    private AddInventoryDto apiResponseToAddInventoryDto(ResponseEntity<String> response) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode jsonNode = root.path("documents").get(0);
        System.out.println("jsonNode = " + jsonNode);

        return AddInventoryDto.builder()
                .thumbnail(jsonToString(jsonNode, "thumbnail"))
                .title(jsonToString(jsonNode, "title"))
                .contents(jsonToString(jsonNode, "contents"))
                .url(jsonToString(jsonNode, "url"))
                .thumbnail(jsonToString(jsonNode, "thumbnail"))
                .isbn(jsonToString(jsonNode, "isbn"))
                .publisher(jsonToString(jsonNode, "publisher"))
                .datetime(offsetDateTimeToLocalDateTime(jsonNode))
                .authors(jsonToStringArray(jsonNode, "authors"))
                .translators(jsonToStringArray(jsonNode, "translators"))
                .price(jsonToInt(jsonNode, "price"))
                .salePrice(jsonToInt(jsonNode, "sale_price")
                        == -1 ? jsonToInt(jsonNode, "price") : jsonToInt(jsonNode, "sale_price"))
                .status(InventoryStatus.fromString(jsonNode.path("status").asText())).build();
    }

    private static LocalDateTime offsetDateTimeToLocalDateTime(JsonNode jsonNode) {
        return OffsetDateTime.parse(jsonNode.get("datetime").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime();
    }

    private String jsonToString(JsonNode jsonNode, String fieldName) {
        return jsonNode.path(fieldName).asText();
    }

    private int jsonToInt(JsonNode jsonNode, String fieldName) {
        return jsonNode.path(fieldName).asInt();
    }

    private String[] jsonToStringArray(JsonNode jsonNode, String fieldName) {
        ArrayList<String> arrayList = new ArrayList<>();
        JsonNode json = jsonNode.get(fieldName);

        for (int i = 0; i < json.size(); i++) {
            String tmp = json.get(i).textValue();
            if (tmp == null) {
                continue;
            }
            arrayList.add(tmp);
        }
        return arrayList.toArray(String[]::new);
    }
}
