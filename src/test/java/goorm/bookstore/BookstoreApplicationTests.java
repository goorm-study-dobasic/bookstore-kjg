package goorm.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class BookstoreApplicationTests {

	@Test
	void contextLoads() {

		int zero = 8 / 10;
		int one = 11 / 10;


		// 0 이라면 +1 * 10 까지 반복
		int i = (zero + 1) * 10;
		System.out.println("i = " + i);

		int j = (one + 1) * 10;
		System.out.println("j = " + j);
	}
}
