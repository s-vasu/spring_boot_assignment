package com.zemoso.AuthorMonolithic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("test")
class AuthorMonolithicApplicationTests {

	@MockBean
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

}
