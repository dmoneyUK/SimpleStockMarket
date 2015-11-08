package exercise.stock.market.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * A class of the common stock.
 * @author DMONEY
 *
 */
public class CommonStock extends AbstractStock{

	/**
	 * Constructor.
	 * @param symbol
	 * @param lastDividend
	 * @param parValue
	 * @param price
	 */
	public CommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal price) {
		super(symbol, lastDividend, parValue, price);

	}

}
