package goorm.bookstore.api;

import goorm.bookstore.book.BookSearchResultDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpringBootTest
public class DaumBookApiTest {

    private static final String API_KEY = "";
    private static final String SEARCH_BOOK_URL = "https://dapi.kakao.com/v3/search/book";
    private static final String query = "jpa";
    @Test
    void 다음API테스트() throws UnsupportedEncodingException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(SEARCH_BOOK_URL)
                .queryParam("query", query)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        System.out.println("uri = " + uri);

        ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody().toLowerCase());
        JSONArray jsonArray = jsonObject.getJSONArray("documents");

        ArrayList<BookSearchResultDto> documentList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            JSONObject documentObj = jsonArray.getJSONObject(i);
            documentList.add(BookSearchResultDto.builder()
                    .title(documentObj.getString("title"))
                    .isbn(documentObj.getString("isbn"))
                    .build());
        }
        System.out.println("documentList = " + documentList);
    }

    @Test
    void RestTemplateTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dapi.kakao.com/v3/search/book?query=%EC%9E%90%EB%B0%94";

        // GET 요청 보내기
        // getForObject: 응답을 객체로 직접 변환
        restTemplate.getForObject(url, BookSearchResultDto.class); // 예시입니당.

        // getForEntity : ResponseEntity 로 전체 응답 받기.
        ResponseEntity<BookSearchResultDto> response = restTemplate.getForEntity(url, BookSearchResultDto.class); // 예시입니당.

        // exchange()
        // HTTP 메서드 지정 가능, 바디 / 헤더 포함 가능, 응답을 ResponseEntity<T> 로 반환.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url2 = "https://dapi.kakao.com/v3/search/book?query=%EC%9E%90%EB%B0%94";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // url 는 String 타입도 가능함.
        ResponseEntity<String> response2 = restTemplate.exchange(url2, HttpMethod.GET, requestEntity, String.class);

        response2.getBody();
        response2.getHeaders();

    }
}
