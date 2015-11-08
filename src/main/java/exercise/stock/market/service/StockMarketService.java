package exercise.stock.market.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.stock.market.model.BuyOrSell;
import exercise.stock.market.model.CommonStock;
import exercise.stock.market.model.PreferredStock;
import exercise.stock.market.model.BaseStock;
import exercise.stock.market.model.TradeRecord;
import exercises.stock.exceptions.BusinessException;
import exercises.stock.exceptions.InvalidValueException;

/**
 * A stock market service that manage stocks and trades information.
 * 
 * @author DMONEY
 *
 */
public class StockMarketService {

	/** A stocks managed in this service. */
	private Map<String, BaseStock> stockMap;

	/**
	 * The precision scale in the calculation.
	 */
	private static final int PRECISION_SCALE = 7;

	/**
	 * Constructor.
	 */
	public StockMarketService() {
		this.stockMap = new HashMap<String, BaseStock>();
	}

	/**
	 * Registers a stock to this market service.
	 * 
	 * @param stock the stock to be registered
	 */
	public void registerStock(BaseStock stock) {
		String errorMessage = "The stock " + stock.getSymbol() + " has already been registerd.";
		if (this.stockMap.containsKey(stock.getSymbol())) {
			throw new BusinessException(errorMessage);
		}
		this.stockMap.put(stock.getSymbol(), stock);
	}

	/**
	 * Unregisters a stock from this market service.
	 * 
	 * @param stockSymbol the symbol of the stock to be unregistered
	 */
	public void unregisterStock(String stockSymbol) {
		if (!this.stockMap.containsKey(stockSymbol)) {
			String errorMessage = "The stock " + stockSymbol + " has not been registerd.";
			throw new BusinessException(errorMessage);
		}
		this.stockMap.remove(stockSymbol);
	}

	/**
	 * Returns the stocks managed in this service.
	 * 
	 * @return the stockMap a {@link HashMap} containing the stocks managed in
	 *         this service
	 */
	public Map<String, BaseStock> getStockMap() {
		return this.stockMap;
	}

	/**
	 * Calculates the dividend yield of the given stock based on the given
	 * price. The result precision scale is 3 and applies
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param symbol the symbol of the stock to be calculated
	 * @param price the price used in calculation
	 */
	public BigDecimal getDividendYield(String symbol, BigDecimal price) {

		BaseStock stock = findStockBySymbol(symbol);

		checkPositive(price);

		BigDecimal result = BigDecimal.ZERO;
		if (stock instanceof CommonStock) {
			CommonStock commonStock = (CommonStock) stock;
			result = commonStock.getLastDividend().divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
		} else if (stock instanceof PreferredStock) {
			PreferredStock preferredStock = (PreferredStock) stock;
			result = preferredStock.getFixedDividend().multiply(stock.getParValue()).divide(price, PRECISION_SCALE,
					BigDecimal.ROUND_HALF_EVEN);
		}

		return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Calculates the P/E Ratio of the given stock based on the given price. The
	 * result precision scale is 3 and applies
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param stock the stock to be calculated
	 * @param price the trade price
	 */
	public BigDecimal getPERatio(String symbol, BigDecimal price) {
		BaseStock stock = findStockBySymbol(symbol);

		checkPositive(price);

		BigDecimal result = BigDecimal.ZERO;
		if (BigDecimal.ZERO.compareTo(stock.getLastDividend()) >= 0) {
			throw new BusinessException(
					"Cannot calculate P/E Ratio for the stock " + symbol + "since the dividend is ZERO.");
		}
		result = price.divide(stock.getLastDividend(), PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
		return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Records a trade, with time stamp, quantity of shares, buy or sell
	 * indicator and traded price.
	 */
	public void recordTrade(String symbol, Date timestamp, BigInteger quantity, BuyOrSell indicator, BigDecimal price) {
		BaseStock stock = findStockBySymbol(symbol);

		checkPositive(price);
		checkPositive(new BigDecimal(quantity));

		TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
		stock.addTradeRecord(record);
	}

	/**
	 * Gets the volume weighted stock price based on the trades in the 15
	 * minutes. The result keeps precision scale is 0 and applies
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param stock the stock to calculate.
	 * @return the VolumeWeightedStockPrice in the last in 15 minutes.
	 */
	public BigDecimal getVolumeWeightedStockPrice(String symbol) {

		BaseStock stock = findStockBySymbol(symbol);

		BigDecimal result = BigDecimal.ZERO;
		List<TradeRecord> tradeRecords = getTradeRecordsByTime(stock, 15);
		if (tradeRecords.isEmpty()) {
			return result;
		}

		BigDecimal priceSum = BigDecimal.ZERO;
		BigInteger quantitySum = BigInteger.ZERO;

		for (TradeRecord record : tradeRecords) {
			BigDecimal price = record.getPrice();
			checkPositive(price);
			BigDecimal quatity = new BigDecimal(record.getQuantity());
			checkPositive(quatity);

			priceSum = priceSum.add(price.multiply(quatity));
			quantitySum = quantitySum.add(record.getQuantity());
		}
		result = priceSum.divide(new BigDecimal(quantitySum), PRECISION_SCALE, 3);
		return result.setScale(0, BigDecimal.ROUND_HALF_EVEN);

	}

	/**
	 * Calculates the GBCE all share index value based on the prices of all the
	 * stocks in the market service. The result precision scale is 0 and applies
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @return the GBCE all share index value, with 2 scale.
	 */
	public BigDecimal getGBCEAllShareIndex() {
		if (this.stockMap.isEmpty()) {
			throw new BusinessException(
					"No any stock registered to this market in current. Please register stocks first.");
		}

		BigDecimal accumulate = BigDecimal.ONE;
		int n = this.stockMap.size();

		for (BaseStock stock : this.stockMap.values()) {
			BigDecimal price = stock.getPrice();
			checkPositive(price);
			accumulate = accumulate.musltiply(price);
		}

		BigDecimal x = accumulate.divide(accumulate, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal temp = BigDecimal.ZERO;
		BigDecimal e = new BigDecimal("0.1");

		do {
			temp = x;
			x = x.add(accumulate.subtract(x.pow(n)).divide(new BigDecimal(n).multiply(x.pow(n - 1)), PRECISION_SCALE,
					BigDecimal.ROUND_HALF_EVEN));
		} while (x.subtract(temp).abs().compareTo(e) > 0);

		return x.setScale(0, BigDecimal.ROUND_HALF_EVEN);

	}

	/**
	 * Gets the trade records of the given stock in the last given minutes.
	 * 
	 * @param stock the {@link BaseStock} to search
	 * @param minutes the range of the time to search
	 * @return a {@link TradeRecord}s matching the search criterion
	 */
	private List<TradeRecord> getTradeRecordsByTime(BaseStock stock, int minutes) {
		List<TradeRecord> result = new ArrayList<TradeRecord>();
		// TODO maybe better to build a trade record list in time order in
		// future.
		Date currentTime = new Date();
		for (TradeRecord record : stock.getTradeRecords()) {
			if (currentTime.getTime() - record.getTimestamp().getTime() <= 15 * 60 * 1000) {
				result.add(record);
			}
		}

		return result;

	}

	/**
	 * Finds the {@link BaseStock} registered to this market with the given
	 * symbol.
	 * 
	 * @param symbol the symbol of the stock to look up
	 * @return the {@link BaseStock} found
	 */
	private BaseStock findStockBySymbol(String symbol) {
		BaseStock stock = null;
		if (this.stockMap.containsKey(symbol)) {
			stock = this.stockMap.get(symbol);
		} else {
			throw new BusinessException(
					"Cannot find the stock " + symbol + " in the market. Please register the stock first");
		}
		return stock;
	}

	/**
	 * Validates the given value is a positive.
	 * 
	 * @param value the value to be checked
	 */
	private void checkPositive(BigDecimal value) {
		if (value == null || BigDecimal.ZERO.compareTo(value) >= 0) {
			throw new InvalidValueException("Found non-positive value: " + value);
		}
	}

}
