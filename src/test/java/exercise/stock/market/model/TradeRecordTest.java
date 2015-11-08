package exercise.stock.market.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exercise.stock.market.test.TestUtils;;

/**
 * Tests for {@link TradeRecord}.
 * @author DMONEY
 *
 */
public class TradeRecordTest {

	/** The trade record under tests.*/
	private TradeRecord tradeRecord;
	
	/**
	 * Constructor.
	 */
	public TradeRecordTest(){
	}
	
	/**
	 * Runs before every test.
	 */
	@Before
	public void setUp(){
		this.tradeRecord=new TradeRecord();
	}
	
	/**
	 * Tests for {@link TradeRecord#setStockSymbol(String)} and {@link TradeRecord#getStockSymbol()}.
	 * <ul>
	 * <li>call the {@link TradeRecord#setStockSymbol(String)}</li>
	 * <li>verify that {@link TradeRecord#getStockSymbol()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetStockSymbol(){
		this.tradeRecord.setStockSymbol(TestUtils.TEST_SYMBOL);
		Assert.assertEquals(TestUtils.TEST_SYMBOL, this.tradeRecord.getStockSymbol());
	}
	
	/**
	 * Tests for {@link TradeRecord#setTimestamp(java.util.Date)} and {@link TradeRecord#getTimestamp()}.
	 * <ul>
	 * <li>call the {@link TradeRecord#setTimestamp(java.util.Date)}</li>
	 * <li>verify that {@link TradeRecord#getTimestamp()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetTimeStamp(){
		Date timestamp = new Date();
		this.tradeRecord.setTimestamp(timestamp);
		Assert.assertEquals(timestamp,this.tradeRecord.getTimestamp());
	}
	
	/**
	 * Tests for {@link TradeRecord#setIndicator(BuyOrSell)} and {@link TradeRecord#getIndicator()}.
	 * <ul>
	 * <li>call the {@link TradeRecord#setIndicator(BuyOrSell)}</li>
	 * <li>verify that {@link TradeRecord#getIndicator()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetIndicator(){
		this.tradeRecord.setIndicator(BuyOrSell.BUY);
		Assert.assertEquals(BuyOrSell.BUY,this.tradeRecord.getIndicator());
	}
	
	/**
	 * Tests for {@link TradeRecord#setQuantity(java.math.BigDecimal)} and {@link TradeRecord#getQuantity()}.
	 * <ul>
	 * <li>call the {@link TradeRecord#setQuantity(java.math.BigDecimal)}</li>
	 * <li>verify that {@link TradeRecord#getQuantity()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetQuantity(){
		BigInteger quantity = new BigInteger(64, new Random());
		this.tradeRecord.setQuantity(quantity);
		Assert.assertEquals(quantity, this.tradeRecord.getQuantity());
	}
	
	/**
	 * Tests for {@link TradeRecord#setPrice(java.math.BigDecimal)} and {@link TradeRecord#getPrice()}.
	 * <ul>
	 * <li>call the {@link TradeRecord#setPrice(java.math.BigDecimal)}</li>
	 * <li>verify that {@link TradeRecord#getPrice()} returns the expected value</li>
	 * </ul>
	 */
	@Test
	public void testAndGetPrice(){
		BigDecimal price = TestUtils.getRamdamBigDecimal();
		this.tradeRecord.setPrice(price);
		Assert.assertEquals(price,this.tradeRecord.getPrice());
	}
	
}
