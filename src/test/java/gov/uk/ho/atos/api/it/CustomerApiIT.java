package gov.uk.ho.atos.api.it;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import gov.uk.ho.atos.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerApiIT {

	@LocalServerPort
	private int port;
	private TestRestTemplate testRestTemplate;
	private static final String url = "/customers";
	private Customer  customer;
	

	@Before
	public void setUp() {
		customer=createCustomer();
		testRestTemplate = new TestRestTemplate();
		RestAssured.port = port;
	}

	@Test
	public void test_endpoints()  {
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<Customer> entity = new HttpEntity<Customer>(customer, headers);

		final ResponseEntity<String> responsePost = testRestTemplate.exchange(buildURL(url), HttpMethod.POST, entity,
				String.class);
		assertThat("unexpected status code", responsePost.getStatusCode(), is(equalTo(HttpStatus.ACCEPTED)));

		final ResponseEntity<String> responseDelete = testRestTemplate.exchange(buildURL(url + "/1"), HttpMethod.DELETE,
				null, String.class);
		assertThat("unexpected status code", responseDelete.getStatusCode(), is(equalTo(HttpStatus.ACCEPTED)));

		final ResponseEntity<String> responseDeleteBadRequest = testRestTemplate.exchange(buildURL(url + "/4"),
				HttpMethod.DELETE, null, String.class);
		assertThat("unexpected status code", responseDeleteBadRequest.getStatusCode(),
				is(equalTo(HttpStatus.BAD_REQUEST)));

		testRestTemplate.exchange(buildURL(url), HttpMethod.POST, entity, String.class);
		final ParameterizedTypeReference<List<Customer>> responseType = new ParameterizedTypeReference<List<Customer>>() {};
	
		final ResponseEntity<List<Customer>> responseGet = testRestTemplate.exchange(buildURL(url), HttpMethod.GET,
				null, responseType);
		assertThat("unexpected status code", responseGet.getStatusCode(), is(equalTo(HttpStatus.OK)));
		
		
		

	}
//	 using rest assured
	@Test
	public void test_add_get_delete_customer() throws IOException
	{
		given().contentType(ContentType.JSON).body(customer).when().post(url).then().statusCode(HttpStatus.ACCEPTED.value());
	    when().get(url).then().assertThat().statusCode(HttpStatus.OK.value());
	    when().delete(url+ "/1").then().statusCode(HttpStatus.ACCEPTED.value());
	}
	
	


	private String buildURL(final String uri) {
		return "http://localhost:" + port + uri;
	}

	private Customer createCustomer() {
		return Customer.builder().Id(1l).firstName("John").lastName("Smith").build();
	}

}
