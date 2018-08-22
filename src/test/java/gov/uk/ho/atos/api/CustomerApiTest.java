package gov.uk.ho.atos.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gov.uk.ho.atos.model.Customer;
import gov.uk.ho.atos.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerApiTest {
	@InjectMocks
	private CustomerApi customerApi;
	@Mock
	private CustomerService customerService;

	@Test
	public void addCustomer_should_call_save_method() {
		// arrange
		final Customer customer = createCustomer();
		when(customerService.saveCustomer(customer)).thenReturn(customer);

		// act
		final ResponseEntity<String> response = customerApi.addCustomer(customer);

		// assert

		verify(customerService).saveCustomer(ArgumentMatchers.eq(customer));
		assertThat("unexpected status code", response.getStatusCode(), is(equalTo(HttpStatus.ACCEPTED)));
		assertThat("unexpected response body ", response.getBody(),
				is(equalTo("{\"message\":\"customer has been sucessfully added\"}")));
	}

	@Test
	public void removeCustomer_should_call_deelete_method() {
		// act
		final ResponseEntity<String> response = customerApi.removeCustomer(1L);

		// assert
		verify(customerService).deleteCustomer(ArgumentMatchers.eq(1l));
		assertThat("unexpected status code", response.getStatusCode(), is(equalTo(HttpStatus.ACCEPTED)));
		assertThat("unexpected response body ", response.getBody(),
				is(equalTo("{\"message\":\"customer has been sucessfully removed\"}")));
	}

	@Test
	public void listAllCustomers_should_call_retrieve_method() {
		// arrange
		when(customerService.retriveAllCustomers()).thenReturn(Arrays.asList(createCustomer()));

		// act
		final ResponseEntity<List<Customer>> response = customerApi.listAllCustomers();

		// assert
		verify(customerService).retriveAllCustomers();
		assertThat("unexpected status code", response.getStatusCode(), is(equalTo(HttpStatus.OK)));
	}

	private Customer createCustomer() {
		return Customer.builder().Id(1L).firstName("John").lastName("Smith").build();
	}

}
