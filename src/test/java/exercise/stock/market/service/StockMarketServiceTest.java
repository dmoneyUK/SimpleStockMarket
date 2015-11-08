package exercise.stock.market.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exercise.stock.market.model.BaseStock;
import exercise.stock.market.model.BuyOrSell;
import exercise.stock.market.model.CommonStock;
import exercise.stock.market.model.PreferredStock;
import exercise.stock.market.model.TradeRecord;
import exercise.stock.market.service.StockMarketService;
import exercise.stock.market.test.TestUtils;
import exercises.stock.exceptions.BusinessException;
import exercises.stock.exceptions.InvalidValueException;;

/**
 * A test class for {@link StockMarketService}.
 * 
 * @author DMONEY
 *
 */
public class StockMarketServiceTest {

	/** The {@link StockMarketService} under tests. */
	private StockMarketService service;

	/**
	 * Runs before every test.
	 */
	@Before
	public void setUp() {
		this.service = new StockMarketService();
	}

	/**
	 * Tests for {@link StockMarketService#registerStock(BaseStock)} and
	 * {@link StockMarketService#unregisterStock(String)}.
	 * <ul>
	 * <li>call the {@link StockMarketService#registerStock(BaseStock)}
	 * {@link StockMarketService#unregisterStock(String)}</li>
	 * <li>verify that the give stock is registered and then unregistered
	 * successfully without exception.</li>
	 * </ul>
	 */
	@Test
	public void testRegisterAndUnregisterStock() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		Assert.assertEquals(stock, this.service.getStockMap().get(TestUtils.TEST_COMMON_STOCK));
		this.service.unregisterStock(TestUtils.TEST_COMMON_STOCK);
	}

	/**
	 * Tests for {@link StockMarketService#registerStock(CommonStock)} with
	 * duplicated stock.
	 * <ul>
	 * <li>call the {@link StockMarketService#registerStock(CommonStock)} twice
	 * </li>
	 * <li>verify that a {@link BusinessException} was caught</li>
	 * </ul>
	 */
	@Test(expected = BusinessException.class)
	public void testRegisterStockDuplicated() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		this.service.registerStock(stock);
	}

	/**
	 * Tests for {@link StockMarketService#unregisterStock(String)} with a
	 * non-existing stock symbol.
	 * <ul>
	 * <li>call the {@link StockMarketService#unregisterStock(String)}</li>
	 * <li>verify that a {@link BusinessException} was caught</li>
	 * </ul>
	 */
	@Test(expected = BusinessException.class)
	public void testUnregisterStockNonExist() {
		this.service.unregisterStock(TestUtils.TEST_COMMON_STOCK);
	}

	/**
	 * Tests for {@link StockMarketService#getDividendYield(String, BigDecimal)}
	 * for a {@link CommonStock}.
	 * <ul>
	 * <li>create a {@link CommonStock} with last dividend == 23</li>
	 * <li>call the
	 * {@link StockMarketService#getDividendYield(String, BigDecimal)} with
	 * price ==130</li>
	 * <li>verify that dividend yield is calculated correctly</li>
	 * </ul>
	 */
	@Test
	public void testGetDividendYield_CommonStock() {

		CommonStock commonStock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(commonStock);
		commonStock.setLastDividend(new BigDecimal(23));
		BigDecimal result = this.service.getDividendYield(TestUtils.TEST_COMMON_STOCK, new BigDecimal(130));
		Assert.assertEquals(new BigDecimal("0.177"), result);

	}

	/**
	 * Tests for {@link StockMarketService#getDividendYield(String, BigDecimal)}
	 * for a {@link PreferredStock}.
	 * <ul>
	 * <li>create a {@link PreferredStock} with FixedDividend == 0.02 and
	 * ParValue==100</li>
	 * <li>call the
	 * {@link StockMarketService#getDividendYield(String, BigDecimal)} with
	 * Price==130</li>
	 * <li>verify that dividend yields is calculated correctly</li>
	 * </ul>
	 */
	@Test
	public void testGetDividendYield_PreferredStock() {

		PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
		this.service.registerStock(preferredStock);
		preferredStock.setFixedDividend(new BigDecimal("0.02"));
		preferredStock.setParValue(new BigDecimal(100));
		BigDecimal result = this.service.getDividendYield(TestUtils.TEST_PREFERRED_STOCK, new BigDecimal(130));
		Assert.assertEquals(new BigDecimal("0.015"), result);

	}

	/**
	 * Tests for {@link StockMarketService#getDividendYield(String, BigDecimal)}
	 * for a {@link CommonStock} with an invalid Price.
	 * <ul>
	 * <li>create a {@link CommonStock}</li>
	 * <li>call the
	 * {@link StockMarketService#getDividendYield(String, BigDecimal)} with
	 * price == -1</li>
	 * <li>verify that an {@link InvalidValueException} is thrown</li>
	 * </ul>
	 */
	@Test(expected = InvalidValueException.class)
	public void testGetDividendYield_InvalidPrice() {

		CommonStock commonStock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(commonStock);
		commonStock.setLastDividend(new BigDecimal(23));
		this.service.getDividendYield(TestUtils.TEST_COMMON_STOCK, new BigDecimal(0));

	}

	/**
	 * Tests for {@link StockMarketService#getPERatio(String, BigDecimal)} for a
	 * {@link CommonStock}.
	 * <ul>
	 * <li>create a {@link CommonStock} with LastDividend == 23</li>
	 * <li>call the {@link StockMarketService#getPERatio(String, BigDecimal)}
	 * with Price==60</li>
	 * <li>verify that P/E Ratio is calculated correctly</li>
	 * </ul>
	 */
	@Test
	public void testGetPERatio() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		stock.setLastDividend(new BigDecimal(23));
		BigDecimal result = this.service.getPERatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(60));
		Assert.assertEquals(new BigDecimal("2.609"), result);
	}

	/**
	 * Tests for {@link StockMarketService#getPERatio(String, BigDecimal)} for a
	 * {@link CommonStock}.
	 * <ul>
	 * <li>create a {@link CommonStock} with LastDividend == 23</li>
	 * <li>call the {@link StockMarketService#getPERatio(String, BigDecimal)}
	 * with Price==0</li>
	 * <li>verify that an {@link InvalidValueException} is thrown</li>
	 * </ul>
	 */
	@Test(expected = InvalidValueException.class)
	public void testGetPERatio_InvalidPrice() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		stock.setLastDividend(new BigDecimal(23));
		this.service.getPERatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(0));
	}

	/**
	 * Tests for {@link StockMarketService#getPERatio(String, BigDecimal)} for a
	 * {@link CommonStock}.
	 * <ul>
	 * <li>create a {@link CommonStock} with last dividend is 0</li>
	 * <li>call the {@link StockMarketService#getPERatio(String, BigDecimal)}
	 * with a given price</li>
	 * <li>verify that an {@link BusinessException} is thrown</li>
	 * </ul>
	 */
	@Test(expected = BusinessException.class)
	public void testGetPERatio_DividendZero() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		stock.setLastDividend(BigDecimal.ZERO);
		this.service.getPERatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(100));
	}

	/**
	 * Tests for {@link StockMarketService#recordTrade(String, Date, BigInteger,
	 * BuyOrSell, BigDecimal).
	 * <ul>
	 * <li>call the {@link StockMarketService#recordTrade(String, Date,
	 * BigInteger, BuyOrSell, BigDecimal)</li>
	 * <li>verify that the trade information is recorded correctly</li>
	 * </ul>
	 */
	@Test
	public void testRecordTrade() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		Date timestamp = new Date();
		BigInteger quantity = new BigInteger("1000");
		BigDecimal price = new BigDecimal(1250);
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, timestamp, quantity, BuyOrSell.BUY, price);
		TradeRecord record = stock.getTradeRecords().get(0);
		Assert.assertEquals(TestUtils.TEST_COMMON_STOCK, record.getStockSymbol());
		Assert.assertEquals(timestamp, record.getTimestamp());
		Assert.assertEquals(quantity, record.getQuantity());
		Assert.assertEquals(price, record.getPrice());
		Assert.assertEquals(BuyOrSell.BUY, record.getIndicator());
	}
	
	/**
	 * Tests for {@link StockMarketService#recordTrade(String, Date, BigInteger,
	 * BuyOrSell, BigDecimal).
	 * <ul>
	 * <li>call the {@link StockMarketService#recordTrade(String, Date,
	 * BigInteger, BuyOrSell, BigDecimal) with price==-1</li>
	 * <li>verify that an {@link InvalidValueException} is caught</li>
	 * </ul>
	 */
	@Test(expected=InvalidValueException.class)
	public void testRecordTrade_InvalidPrice() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		BigInteger quantity = new BigInteger("1000");
		BigDecimal price = new BigDecimal(-1);
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(), quantity, BuyOrSell.BUY, price);
	}
	
	/**
	 * Tests for {@link StockMarketService#recordTrade(String, Date, BigInteger,
	 * BuyOrSell, BigDecimal).
	 * <ul>
	 * <li>call the {@link StockMarketService#recordTrade(String, Date,
	 * BigInteger, BuyOrSell, BigDecimal) with quantity==0</li>
	 * <li>verify that an {@link InvalidValueException} is caught</li>
	 * </ul>
	 */
	@Test(expected=InvalidValueException.class)
	public void testRecordTrade_InvalidQuantity() {
		CommonStock stock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(stock);
		BigInteger quantity = BigInteger.ZERO;
		BigDecimal price = new BigDecimal(100);
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(), quantity, BuyOrSell.BUY, price);
	}

	/**
	 * Tests for {@link StockMarketService#getVolumeWeightedStockPrice(String).
	 * <ul>
	 * <li>record a TEST_COMMON_STOCK trade 3 minutes ago which bought 123456
	 * shares at the price 1235</li>
	 * <li>record a TEST_COMMON_STOCK trade 12 minutes ago which sold 7890
	 * shares at the price 1021</li>
	 * <li>record a TEST_PREFERRED_STOCK trade 7 minutes ago which bought
	 * 10000 shares at the price 3012</li>
	 * <li>record a TEST_COMMON_STOCK trade 16 minutes ago which bought 2400
	 * shares at the price 1187</li>
	 * <li>call the {@link StockMarketService#recordTrade(String, Date,
	 * BigInteger, BuyOrSell, BigDecimal)</li>
	 * <li>verify that the only the trade of TEST_COMMON_STOCK in the last 15
	 * minutes were used and the result is correct</li>
	 * </ul>
	 */
	@Test
	public void testVolumWeightedStockPrice() {
		CommonStock commonStock = TestUtils.getDefaultCommonStock();
		PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
		this.service.registerStock(commonStock);
		this.service.registerStock(preferredStock);
		long startTime = new Date().getTime();
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(startTime - 3 * 1000 * 60),
				new BigInteger("123456"), BuyOrSell.BUY, new BigDecimal(1235));
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(startTime - 12 * 1000 * 60),
				new BigInteger("7890"), BuyOrSell.SELL, new BigDecimal(1021));
		this.service.recordTrade(TestUtils.TEST_PREFERRED_STOCK, new Date(startTime - 7 * 1000 * 60),
				new BigInteger("10000"), BuyOrSell.BUY, new BigDecimal(3012));
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(startTime - 16 * 1000 * 60),
				new BigInteger("2400"), BuyOrSell.BUY, new BigDecimal(1187));
		Assert.assertEquals(new BigDecimal(1222),
				this.service.getVolumeWeightedStockPrice(TestUtils.TEST_COMMON_STOCK));
	}
	
	/**
	 * Tests for {@link StockMarketService#getVolumeWeightedStockPrice(String).
	 * <ul>
	 * <li>record only one trade 20 minutes ago </li>
	 * <li>verify that the only the trade of TEST_COMMON_STOCK in the last 15
	 * minutes were used and the result is correct</li>
	 * </ul>
	 */
	@Test
	public void testVolumWeightedStockPrice_NoTrade() {
		CommonStock commonStock = TestUtils.getDefaultCommonStock();
		this.service.registerStock(commonStock);
		long startTime = new Date().getTime();
		this.service.recordTrade(TestUtils.TEST_COMMON_STOCK, new Date(startTime - 20 * 1000 * 60),
				new BigInteger("2400"), BuyOrSell.BUY, new BigDecimal(1187));
		Assert.assertEquals(new BigDecimal(0),
				this.service.getVolumeWeightedStockPrice(TestUtils.TEST_COMMON_STOCK));
	}

	
	/**
	 * Tests for {@link StockMarketService#getGBCEAllShareIndex().
	 * <ul>
	 * <li>register 5 stocks with valid prices in the market</li>
	 * <li>call the method under test</li>
	 * <li>verify that the geometric mean is calculated correctly</li>
	 * </ul>
	 */
	@Test
	public void testGBCEAllShareIndex() {
		CommonStock commonStock1 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 1, BigDecimal.ZERO, BigDecimal.ZERO,
				new BigDecimal(1234));
		CommonStock commonStock2 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 2, BigDecimal.ZERO, BigDecimal.ZERO,
				new BigDecimal(7057));
		CommonStock commonStock3 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 3, BigDecimal.ZERO, BigDecimal.ZERO,
				new BigDecimal(4069));
		PreferredStock preferStock1 = new PreferredStock(TestUtils.TEST_PREFERRED_STOCK + 1, BigDecimal.ZERO,
				BigDecimal.ZERO, new BigDecimal(583), BigDecimal.ZERO);
		PreferredStock preferStock2 = new PreferredStock(TestUtils.TEST_PREFERRED_STOCK + 2, BigDecimal.ZERO,
				BigDecimal.ZERO, new BigDecimal(11231), BigDecimal.ZERO);
		this.service.registerStock(commonStock1);
		this.service.registerStock(commonStock2);
		this.service.registerStock(commonStock3);
		this.service.registerStock(preferStock1);
		this.service.registerStock(preferStock2);
		Assert.assertEquals(new BigDecimal(2972), this.service.getGBCEAllShareIndex());
	}
	
	/**
	 * Tests for {@link StockMarketService#getGBCEAllShareIndex().
	 * <ul>
	 * <li>register 5 stocks with valid prices in the market</li>
	 * <li>call the method under test</li>
	 * <li>verify that an {@link InvalidValueException} was caught</li>
	 * </ul>
	 */
	@Test(expected=InvalidValueException.class)
	public void testGBCEAllShareIndex_PriceZero() {
		CommonStock commonStock1 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 1, BigDecimal.ZERO, BigDecimal.ZERO,
				new BigDecimal(1234));
		CommonStock commonStock2 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 2, BigDecimal.ZERO, BigDecimal.ZERO,
				new BigDecimal(7057));
		CommonStock commonStock3 = new CommonStock(TestUtils.TEST_COMMON_STOCK + 3, BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO);
		this.service.registerStock(commonStock1);
		this.service.registerStock(commonStock2);
		this.service.registerStock(commonStock3);
		this.service.getGBCEAllShareIndex();
	}
	
	/**
	 * Tests for {@link StockMarketService#getGBCEAllShareIndex().
	 * <ul>
	 * <li>call the method under test when no any stock registered</li>
	 * <li>verify that an {@link BusinessException} was caught</li>
	 * </ul>
	 */
	@Test(expected=BusinessException.class)
	public void testGBCEAllShareIndex_NoStock() {
		Assert.assertEquals(BigDecimal.ZERO,this.service.getGBCEAllShareIndex());
	}

}
