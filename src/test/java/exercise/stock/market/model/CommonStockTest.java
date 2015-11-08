package exercise.stock.market.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exercise.stock.market.test.TestUtils;
import exercises.stock.exceptions.InvalidPriceException;

import java.math.BigDecimal;

import org.junit.Assert;

/**
 * A test class for {@link CommonStock}.
 * 
 * @author DMONEY
 *
 */
public class CommonStockTest {

	/**
	 * The {@link AbstractStock} object under test.
	 */
	private AbstractStock stock;

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
	 * Tests for {@link AbstractStock#Stock()}.
	 * <ul>
	 * <li>create a new {@link AbstractStock}</li>
	 * <li>verify that the trade records is not null</li>
	 * </ul>
	 */
	@Test
	public void testStock() {
		Assert.assertNotNull(this.stock.getTradeRecords());
	}

	/**
	 * Tests for {@link AbstractStock#setSymbol(String)} and {@link AbstractStock#getSymbol()}.
	 * <ul>
	 * <li>call {@link AbstractStock}</li>
	 * <li>verify that the {@link AbstractStock} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetSymbol() {
		this.stock.setSymbol("Test");
		Assert.assertEquals("Test", this.stock.getSymbol());
	}

	/**
	 * Tests for {@link AbstractStock#setPrice(BigDecimal)} and {@link AbstractStock#getPrice()}
	 * .
	 * <ul>
	 * <li>call {@link AbstractStock#setPrice(BigDecimal)}</li>
	 * <li>verify that the {@link AbstractStock#getPrice()} returns the expected value
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
	 * Tests for {@link AbstractStock#setPrice(BigDecimal)} with invalid value.
	 * <ul>
	 * <li>call {@link AbstractStock#setPrice(BigDecimal)} with a nagetive value</li>
	 * <li>verify that an {@link InvalidPriceException} was thrown.</li>
	 * </ul>
	 */
	@Test(expected=InvalidPriceException.class)
	public void testSetInvalidPrice(){
		this.stock.setPrice(new BigDecimal(-1));;
	}

	/**
	 * Tests for {@link AbstractStock#setParValue(BigDecimal)} and
	 * {@link AbstractStock#getParValue()}.
	 * <ul>
	 * <li>call {@link AbstractStock#setParValue(BigDecimal)}</li>
	 * <li>verify that the {@link AbstractStock#getParValue()} returns the expected
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
	 * Tests for {@link AbstractStock#setLastDividend(BigDecimal)} and
	 * {@link AbstractStock#getLastDividend()}.
	 * <ul>
	 * <li>call {@link AbstractStock#setLastDividend(BigDecimal)}</li>
	 * <li>verify that the {@link AbstractStock#getLastDividend()} returns the expected
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
	 * Tests for {@link AbstractStock#addTradeRecord(TradeRecord)} and {@link AbstractStock#getTradeRecords()}.
	 * <ul>
	 * <li>call {@link AbstractStock#addTradeRecord(TradeRecord)}</li>
	 * <li>verify that the {@link AbstractStock#getTradeRecords()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetTradeRecords(){
		TradeRecord record = Mockito.mock(TradeRecord.class);
		this.stock.addTradeRecord(record);;
		Assert.assertEquals(record, this.stock.getTradeRecords().get(0));
	}

}
