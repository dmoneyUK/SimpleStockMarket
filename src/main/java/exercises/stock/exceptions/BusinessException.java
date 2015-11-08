package exercises.stock.exceptions;


public class BusinessException extends RuntimeException {
	
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -5398829504118023324L;

	/**
	 * Constructor.
	 */
	public BusinessException(String message){
		super(message);
	}

}
