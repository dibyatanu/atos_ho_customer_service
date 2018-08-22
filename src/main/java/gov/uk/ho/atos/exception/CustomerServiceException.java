package gov.uk.ho.atos.exception;

public class CustomerServiceException extends RuntimeException{

	private static final long serialVersionUID = 2324579180069932610L;
	
	public CustomerServiceException(final String message)
	{
		super(message);
	}

}
