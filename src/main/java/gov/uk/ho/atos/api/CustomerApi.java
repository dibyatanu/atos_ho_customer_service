package gov.uk.ho.atos.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.uk.ho.atos.model.Customer;
import gov.uk.ho.atos.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerApi {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addCustomer(@RequestBody final Customer customer)
	{
		customerService.saveCustomer(customer);
		return new ResponseEntity<String>("{\"message\":\"customer has been sucessfully added\"}", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeCustomer(@PathVariable("id") final Long Id)
	{
		customerService.deleteCustomer(Id);
		return new ResponseEntity<String>("{\"message\":\"customer has been sucessfully removed\"}", HttpStatus.ACCEPTED);
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> listAllCustomers()
	{
		return new ResponseEntity<List<Customer>>(customerService.retriveAllCustomers(), HttpStatus.OK);
	}

}
