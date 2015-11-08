package exercise.stock.market.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exercise.stock.market.test.TestUtils;
import exercises.stock.exceptions.InvalidValueException;

import java.math.BigDecimal;

import org.junit.Assert;

/**
 * A test class for {@link CommonStock}.
 * 
 * @author DMONEY
 *
 */
public class BaseStockTest {

	/**
	 * The {@link BaseStock} object under test.
	 */
	private BaseStock stock;

	/**
	 * The price of this stock.
	 * 
	 * /** Runs before every test.
	 */
	@Before
	public void setUp() {
		this.stock = TestUtils.getDefaultCommonStock();
	}

	/**
	 * Tests for {@link BaseStock#Stock()}.
	 * <ul>
	 * <li>create a new {@link BaseStock}</li>
	 * <li>verify that the trade records is not null</li>
	 * </ul>
	 */
	@Test
	public void testBaseStock() {
		Assert.assertNotNull(this.stock.getTradeRecords());
	}

	/**
	 * Tests for {@link BaseStock#setSymbol(String)} and {@link BaseStock#getSymbol()}.
	 * <ul>
	 * <li>call {@link BaseStock}</li>
	 * <li>verify that the {@link BaseStock} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetSymbol() {
		this.stock.setSymbol("Test");
		Assert.assertEquals("Test", this.stock.getSymbol());
	}

	/**
	 * Tests for {@link BaseStock#setPrice(BigDecimal)} and {@link BaseStock#getPrice()}
	 * .
	 * <ul>
	 * <li>call {@link BaseStock#setPrice(BigDecimal)}</li>
	 * <li>verify that the {@link BaseStock#getPrice()} returns the expected value
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
	 * Tests for {@link BaseStock#setParValue(BigDecimal)} and
	 * {@link BaseStock#getParValue()}.
	 * <ul>
	 * <li>call {@link BaseStock#setParValue(BigDecimal)}</li>
	 * <li>verify that the {@link BaseStock#getParValue()} returns the expected
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
	 * Tests for {@link BaseStock#setLastDividend(BigDecimal)} and
	 * {@link BaseStock#getLastDividend()}.
	 * <ul>
	 * <li>call {@link BaseStock#setLastDividend(BigDecimal)}</li>
	 * <li>verify that the {@link BaseStock#getLastDividend()} returns the expected
	 * value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetLastDividend() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setLastDividend(value);
		Assert.assertEquals(value, this.stock.getLastDividend());
	}

	/**
	 * Tests for {@link BaseStock#addTradeRecord(TradeRecord)} and {@link BaseStock#getTradeRecords()}.
	 * <ul>
	 * <li>call {@link BaseStock#addTradeRecord(TradeRecord)}</li>
	 * <li>verify that the {@link BaseStock#getTradeRecords()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetTradeRecords(){
		TradeRecord record = Mockito.mock(TradeRecord.class);
		this.stock.addTradeRecord(record);;
		Assert.assertEquals(record, this.stock.getTradeRecords().get(0));
	}

}
