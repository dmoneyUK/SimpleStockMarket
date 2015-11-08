package exercises.stock.exceptions;

/**
 * An exception indicating the a value is invalid.
 * @author DMONEY
 *
 */
public class InvalidValueException extends BusinessException {

	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 1381320130022416598L;

	/**
	 * Constructor.
	 */
	public InvalidValueException(String message){
		super(message);
	}
}
