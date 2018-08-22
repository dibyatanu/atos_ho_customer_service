package gov.uk.ho.atos.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.uk.ho.atos.exception.CustomerServiceException;
import gov.uk.ho.atos.model.Customer;

public class CustomerServiceTest {

	private CustomerService customerService;

	@Rule
	public ExpectedException excpetion = ExpectedException.none();

	@Before
	public void setUP() {
		customerService = new CustomerService();
	}

	@Test
	public void saveCustomer_should_add_customer_to_map() {
		// arrange
		final Customer customer = createCustomer();

		// act
		final Customer saveCustomer = customerService.saveCustomer(customer);

		// assert
		assertThat("Unexpeted save customer", saveCustomer, is(equalTo(customer)));
	}

	@Test
	public void deleteCustomer_should_delete_customer_from_map() {
		// arrange
		final Customer customer = createCustomer();
		customerService.saveCustomer(customer);

		// act
		customerService.deleteCustomer(1l);

		// assert
		assertThat("List should be empty", customerService.retriveAllCustomers().isEmpty(), is(true));

	}

	@Test
	public void deleteCustomer_should_throw_excpetion_if_id_does_not_exits() {

		// arrange
		excpetion.expect(CustomerServiceException.class);
		excpetion.expectMessage("Customer Id not Found " + 1L);

		// act
		customerService.deleteCustomer(1l);
	}

	@Test
	public void retriveAllCustomers_should_get_all_Customers() {
		// arrange
		final Customer customer = createCustomer();
		customerService.saveCustomer(customer);

		// act
		final List<Customer> customers = customerService.retriveAllCustomers();

		// assert
		assertThat("List should not be empty", customers.isEmpty(), is(false));
		assertThat(customers, hasSize(1));
	}

	private Customer createCustomer() {
		return Customer.builder().Id(1L).firstName("John").lastName("Smith").build();
	}

}
