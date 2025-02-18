package goorm.bookstore.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.bookstore.book.dto.BookIsbnDto;
import goorm.bookstore.book.dto.BookSearchDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private static final String API_KEY = "e0b415dd4c54c47b8b91b3f0e643c76e";
    private static final String SEARCH_BOOK_URL = "https://dapi.kakao.com/v3/search/book";

  //  @GetMapping("/{param}")
    public String searchPage(@PathVariable("param") String param, Model model) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);


        URI uri = UriComponentsBuilder.fromUriString(SEARCH_BOOK_URL)
                .queryParam("query", param)
                .queryParam("page", 1)
                .queryParam("size", 10)
                .encode(StandardCharsets.UTF_8)
                .build(). // UriComponent
                        toUri(); // Uri

        // uri = https://dapi.kakao.com/v3/search/book?query=%EC%9E%90%EB%B0%94


        // 단순 문자열
        ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);


        // spring 에서는 ObjectMapper 를 사용하는 것이 일반적이다.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode documentsNode = root.path("documents");



        List<BookSearchResultDto> bookList = new ArrayList<>();

        for (JsonNode jsonNode : documentsNode) {
            String thumbnail = jsonNode.path("thumbnail").asText();
            String title = jsonNode.path("title").asText();
            String isbn = jsonNode.path("isbn").asText();
            bookList.add(new BookSearchResultDto(thumbnail, title, isbn));
        }

        model.addAttribute("bookList", bookList);
        return "tmp/booklist";
    }


    @GetMapping("/add")
    public JsonNode addBookPage(@RequestParam("isbn") String isbn) throws JsonProcessingException {
        System.out.println("BookRestController.addBookPage");
        System.out.println("isbn = " + isbn);

        // 공백이 %20으로 바뀐다..
        // 어차피 다 고유값이라. isbn이 두개 이상이여도 어차피 각각이 고유값이라 그냥 처음껄로 조회하면 됩니다.
        String firstIsbn = isbn.split(" ")[0];
        System.out.println("firstIsbn = " + firstIsbn);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(SEARCH_BOOK_URL)
                .queryParam("query", firstIsbn)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        System.out.println("uri = " + uri);

        ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        System.out.println("response = " + response.getBody());
        JsonNode documentsNode = root.path("documents");
        System.out.println("documentsNode = " + documentsNode);
        return documentsNode;

   /*     BookSearchResultDto bookSearchResultDto = new BookSearchResultDto(
                documentsNode.path("thumbnail").asText(),
                documentsNode.path("title").asText(),
                documentsNode.path("isbn").asText());

        System.out.println("bookSearchResultDto = " + bookSearchResultDto);
        model.addAttribute("bookSearchResultDto", bookSearchResultDto);
        return "tmp/add";*/

    }



}
