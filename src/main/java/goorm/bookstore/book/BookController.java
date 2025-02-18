package goorm.bookstore.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.bookstore.book.dto.BookSearchDto;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final String API_KEY = "e0b415dd4c54c47b8b91b3f0e643c76e";
    private static final String SEARCH_BOOK_URL = "https://dapi.kakao.com/v3/search/book";


    @GetMapping // /books
    public String bookSearchPage(Model model) {

        model.addAttribute("bookSearchDto", new BookSearchDto());

        return "books/search";
    }


    @GetMapping("/search") // "/api/book/query="검색어"&page=3
    public String bookSearchAPI(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model ) throws JsonProcessingException {

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

        List<BookSearchResultDto> searchResult = new ArrayList<>();

        for (JsonNode jsonNode : documentsNode) {
            String thumbnail = jsonNode.path("thumbnail").asText();
            String title = jsonNode.path("title").asText();
            String isbn = jsonNode.path("isbn").asText();
            searchResult.add(new BookSearchResultDto(thumbnail, title, isbn));
        }

        // 검색창 전용
        model.addAttribute("bookSearchDto", new BookSearchDto((query)));

        // 검색결과 / 페이징 전용
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "books/search";
    }
}
