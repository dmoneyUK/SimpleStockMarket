package exercise.stock.market.test;

import java.math.BigDecimal;
import exercise.stock.market.model.CommonStock;
import exercise.stock.market.model.PreferredStock;


/**
 * A utility class for tests.
 * 
 * @author DMONEY
 *
 */
public class TestUtils {

	/** The symbol of the stock. */
	public static final String PREFIX_STOCK_SYMBOL = "sample_stock_";
	
	/** The symbol of the common stock. */
	public static final String TEST_COMMON_STOCK = PREFIX_STOCK_SYMBOL+"common";
	
	/** The symbol of the preferred stock. */
	public static final String TEST_PREFERRED_STOCK = PREFIX_STOCK_SYMBOL+"preferred";

	/**
	 * Gets a random {@link BigDecimal} between 0 to 100,000.
	 * 
	 * @return a {@link BigDecimal}
	 */
	public static BigDecimal getRamdamBigDecimal() {

		return new BigDecimal(Math.random() * 100000);
	}
	
	/**
	 * Gets a default {@link CommonStock} for tests.
	 * @return a default {@link CommonStock}
	 */
	public static CommonStock getDefaultCommonStock(){
		return new CommonStock(TEST_COMMON_STOCK, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	}
	
	/**
	 * Gets a default {@link PreferredStock} for tests.
	 * @return a default {@link PreferredStock}
	 */
	public static PreferredStock getDefaultPreferredStock(){
		return new PreferredStock(TEST_PREFERRED_STOCK, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	}

}
