package exercise.stock.market.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exercise.stock.market.test.TestUtils;
import exercises.stock.exceptions.InvalidPriceException;

import java.math.BigDecimal;

import org.junit.Assert;

/**
 * A test class for {@link Stock}.
 * 
 * @author DMONEY
 *
 */
public class StockTest {

	/**
	 * The {@link Stock} object under test.
	 */
	private Stock stock;

	/**
	 * The price of this stock. *
	 * 
	 * /** Runs before every test.
	 */
	@Before
	public void setUp() {
		this.stock = new Stock();
	}

	/**
	 * Tests for {@link Stock#Stock()}.
	 * <ul>
	 * <li>create a new {@link Stock}</li>
	 * <li>verify that the trade records is not null</li>
	 * </ul>
	 */
	@Test
	public void testStock() {
		Assert.assertNotNull(this.stock.getTradeRecords());
	}

	/**
	 * Tests for {@link Stock#setSymbol(String)} and {@link Stock#getSymbol()}.
	 * <ul>
	 * <li>call {@link Stock}</li>
	 * <li>verify that the {@link Stock} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetSymbol() {
		this.stock.setSymbol(TestUtils.TEST_SYMBOL);
		Assert.assertEquals(TestUtils.TEST_SYMBOL, this.stock.getSymbol());
	}

	/**
	 * Tests for {@link Stock#setType(StockType)} and
	 * {@link Stock#getTradeRecords()}.
	 * <ul>
	 * <li>call {@link Stock#setType(StockType)}</li>
	 * <li>verify that the {@link Stock#getTradeRecords()} returns the expected
	 * value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetType() {
		this.stock.setType(StockType.Preferred);
		Assert.assertEquals(StockType.Preferred, this.stock.getType());
	}

	/**
	 * Tests for {@link Stock#setPrice(BigDecimal)} and {@link Stock#getPrice()}
	 * .
	 * <ul>
	 * <li>call {@link Stock#setPrice(BigDecimal)}</li>
	 * <li>verify that the {@link Stock#getPrice()} returns the expected value
	 * </li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetPrice() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setPrice(value);
		Assert.assertEquals(value, this.stock.getPrice());
	}
	

	/**
	 * Tests for {@link Stock#setPrice(BigDecimal)} with invalid value.
	 * <ul>
	 * <li>call {@link Stock#setPrice(BigDecimal)} with a nagetive value</li>
	 * <li>verify that an {@link InvalidPriceException} was thrown.</li>
	 * </ul>
	 */
	@Test(expected=InvalidPriceException.class)
	public void testSetInvalidPrice(){
		this.stock.setPrice(new BigDecimal(-1));;
	}

	/**
	 * Tests for {@link Stock#setParValue(BigDecimal)} and
	 * {@link Stock#getParValue()}.
	 * <ul>
	 * <li>call {@link Stock#setParValue(BigDecimal)}</li>
	 * <li>verify that the {@link Stock#getParValue()} returns the expected
	 * value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetParValue() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setParValue(value);
		Assert.assertEquals(value, stock.getParValue());
	}

	/**
	 * Tests for {@link Stock#setLastDividend(BigDecimal)} and
	 * {@link Stock#getLastDividend()}.
	 * <ul>
	 * <li>call {@link Stock#setLastDividend(BigDecimal)}</li>
	 * <li>verify that the {@link Stock#getLastDividend()} returns the expected
	 * value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetLastDividend() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setLastDividend(value);
		;
		Assert.assertEquals(value, this.stock.getLastDividend());
	}

	/**
	 * Tests for {@link Stock#getFixedDividend()} and
	 * {@link Stock#getFixedDividend()}.
	 * <ul>
	 * <li>call {@link Stock#setFixedDividend(BigDecimal)}</li>
	 * <li>verify that the {@link Stock#getFixedDividend()} returns the expected
	 * value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetFixedDividend() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setFixedDividend(value);
		Assert.assertEquals(value, this.stock.getFixedDividend());
	}

	/**
	 * Tests for {@link Stock#addTradeRecord(TradeRecord)} and {@link Stock#getTradeRecords()}.
	 * <ul>
	 * <li>call {@link Stock#addTradeRecord(TradeRecord)}</li>
	 * <li>verify that the {@link Stock#getTradeRecords()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetTradeRecords(){
		TradeRecord record = Mockito.mock(TradeRecord.class);
		this.stock.addTradeRecord(record);;
		Assert.assertEquals(record, this.stock.getTradeRecords().get(0));
	}

}
