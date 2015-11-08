package exercises.stock.exceptions;

/**
 * An exception indicating the stock price is invalid.
 * @author DMONEY
 *
 */
public class InvalidPriceException extends RuntimeException {

	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 1381320130022416598L;

	/**
	 * Constructor.
	 */
	public InvalidPriceException(String message){
		super(message);
	}
}
