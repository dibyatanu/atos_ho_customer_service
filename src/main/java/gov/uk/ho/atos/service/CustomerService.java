package gov.uk.ho.atos.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gov.uk.ho.atos.exception.CustomerServiceException;
import gov.uk.ho.atos.model.Customer;

@Service
public class CustomerService {
	
	private static final Map<Long,Customer> customerRepository=Collections.synchronizedMap(new HashMap<>());
	
	public Customer saveCustomer(final Customer customer)
	{
		 customerRepository.put(customer.getId(), customer);
		 return customerRepository.get(customer.getId());
	}
	
	public void deleteCustomer(final long Id)
	{
		final Optional<Customer> customer=Optional.ofNullable(customerRepository.remove(Id)) ;
		if(!customer.isPresent())
		{
			throw new CustomerServiceException("Customer Id not Found "+Id);
		}
	}
	
	public List<Customer> retriveAllCustomers()
	{
		return customerRepository.values().stream().collect(Collectors.toList());
	}

}
