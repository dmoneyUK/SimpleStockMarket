package exercise.stock.market.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import exercise.stock.market.model.TradeRecord;

/**
 * A utility class for tests.
 * 
 * @author DMONEY
 *
 */
public class TestUtils {

	/** The symbol of the stock. */
	public static final String TEST_SYMBOL = "sample_stock";

	/**
	 * Gets a random BigDecimal value between 0 to 100,000.
	 * 
	 * @return
	 */
	public static BigDecimal getRamdamBigDecimal() {

		return new BigDecimal(Math.random() * 100000);
	}

	/**
	 * Creates the given numbers of {@link TradeRecord}s with random prices.
	 * 
	 * @param num the number of the trades
	 * @return a list of {@link TradeRecord}
	 */
	public static List<TradeRecord> createSampleTradeRecord(int num) {
		
		List<TradeRecord> records = new ArrayList<TradeRecord>();
		while (num > 0) {
				num--;
			//TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
		}
		return records;
	}

}
