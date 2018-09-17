package com.kfc.vitals.sf;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="file:secure/secure.properties")
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class AuthIntegrationTest {

	@Autowired
	SfEnterpriseConnection conn;
	
	@Test
	public void secureContextLoads() {
		assertThat(conn.getUserInfo().getUserFullName(),notNullValue());
		
	}

}
