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
import exercise.stock.market.model.AbstractStock;
import exercise.stock.market.model.TradeRecord;
import exercises.stock.exceptions.BusinessException;

/**
 * A calculator that can calculate various values.
 * 
 * @author DMONEY
 *
 */
public class StockMarketService {

	/** A stocks managed in this service. */
	private Map<String, AbstractStock> stockMap;

	/**
	 * The percision scale in the calculation.
	 */
	private static final int PERCISION_SCALE = 7;

	/**
	 * Constructor.
	 */
	public StockMarketService() {
		this.stockMap = new HashMap<String, AbstractStock>();
	}

	/**
	 * Registers a stock to this service.
	 * 
	 * @param stock
	 */
	public void registerStock(AbstractStock stock) {
		String errorMessage = "The stock " + stock.getSymbol() + " has been registerd.";
		if (this.stockMap.containsKey(stock.getSymbol())) {
			throw new BusinessException(errorMessage);
		}
		this.stockMap.put(stock.getSymbol(), stock);
	}

	/**
	 * Unregisters a stock from this service.
	 * 
	 * @param stockSymbol the symbol of the stock to be unregistered
	 * @throws BusinessException thrown when the given stock not existing.
	 */
	public void unregisterStock(String stockSymbol) {
		if (!this.stockMap.containsKey(stockSymbol)) {
			String errorMessage = "The stock " + stockSymbol + " has not been registerd.";
			throw new BusinessException(errorMessage);
		}
		this.stockMap.remove(stockSymbol);
	}

	/**
	 * Retuns a map of stocks managed in this service.
	 * 
	 * @return the stockMap a {@link HashMap} containing the stocks managed in
	 *         this service
	 */
	public Map<String, AbstractStock> getStockMap() {
		return stockMap;
	}

	/**
	 * Calculates the dividend yield of the given stock based on the given
	 * price. The result keeps precision scale is 3 and uses
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param symbol the symbol of the stock to be calculated
	 * @param price the trade price
	 */
	public BigDecimal getDividendYield(String symbol, BigDecimal price) {
		AbstractStock stock = findStockBySymbol(symbol);
		if (stock == null) {
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		if (stock instanceof CommonStock) {
			CommonStock commonStock = (CommonStock) stock;
			result = commonStock.getLastDividend().divide(price, PERCISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
		} else if (stock instanceof PreferredStock) {
			PreferredStock preferredStock = (PreferredStock) stock;
			result = preferredStock.getFixedDividend().multiply(stock.getParValue()).divide(price, 6,
					BigDecimal.ROUND_HALF_EVEN);
		}

		return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Calculates the P/E Ratio of the given stock based on the given price. The
	 * result keeps precision scale is 3 and uses
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param stock the stock to be calculated
	 * @param price the trade price
	 */
	public BigDecimal getPERatio(String symbol, BigDecimal price) {
		AbstractStock stock = findStockBySymbol(symbol);
		if (stock == null) {
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		if (BigDecimal.ZERO.compareTo(stock.getLastDividend()) >= 0) {
			throw new BusinessException(
					"Cannot calculate P/E Ratio for the stock " + symbol + "since the dividend is ZERO.");
		}
		result = price.divide(stock.getLastDividend(), PERCISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
		return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Records a trade, with time stamp, quantity of shares, buy or sell
	 * indicator and traded price.
	 */
	public void recordTrade(String symbol, Date timestamp, BigInteger quantity, BuyOrSell indicator, BigDecimal price) {
		AbstractStock stock = findStockBySymbol(symbol);
		if (stock == null) {
			return;
		}
		TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
		stock.addTradeRecord(record);
	}

	/**
	 * Gets the volume weighted stock price based on the trades in the 15
	 * minutes. The result keeps precision scale is 0 and uses
	 * {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @param stock the stock to calculate.
	 * @return the VolumeWeightedStockPrice in the last in 15 minutes.
	 */
	public BigDecimal getVolumeWeightedStockPrice(String symbol) {
		AbstractStock stock = findStockBySymbol(symbol);
		if (stock == null) {
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		List<TradeRecord> tradeRecords = getTradeRecordsByTime(stock, 15);
		if (tradeRecords.isEmpty()) {
			throw new BusinessException(
					"No trade record found in the last 15 minutes for the stock " + stock.getSymbol());
		} else {
			BigDecimal priceSum = BigDecimal.ZERO;
			BigInteger quantitySum = BigInteger.ZERO;

			for (TradeRecord record : tradeRecords) {

				priceSum = priceSum.add(record.getPrice().multiply(new BigDecimal(record.getQuantity())));
				quantitySum = quantitySum.add(record.getQuantity());
			}
			result = priceSum.divide(new BigDecimal(quantitySum), PERCISION_SCALE, 3);
		}
		return result.setScale(0, BigDecimal.ROUND_HALF_EVEN);

	}

	/**
	 * Calculates the GBCE all share index value based on the prices of all the
	 * stocks in the market service. The result keeps precision scale is 0 and
	 * uses {@link BigDecimal#ROUND_HALF_EVEN}.
	 * 
	 * @return the GBCE all share index value, with 2 scale.
	 */
	public BigDecimal getGBCEAllShareIndex() {
		if (this.stockMap.isEmpty()) {
			throw new BusinessException(
					"No any stock registered to this market in current. Please register stocks first.");
		}
		BigDecimal accumulate = BigDecimal.ONE;
		for (AbstractStock stock : this.stockMap.values()) {
			accumulate = accumulate.multiply(stock.getPrice());
		}
		int n = this.stockMap.size();
		BigDecimal x = accumulate.divide(accumulate, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal temp = BigDecimal.ZERO;
		BigDecimal e = new BigDecimal("0.1");

		do {
			temp = x;
			x = x.add(accumulate.subtract(x.pow(n)).divide(new BigDecimal(n).multiply(x.pow(n - 1)), 2,
					BigDecimal.ROUND_HALF_EVEN));
		} while (x.subtract(temp).abs().compareTo(e) > 0);

		return x.setScale(0, BigDecimal.ROUND_HALF_EVEN);

	}

	/**
	 * Gets the trade records of the given stock in the last given minutes.
	 * 
	 * @param stock the {@link AbstractStock} to search
	 * @param minutes the range of the time to search
	 * @return
	 */
	private List<TradeRecord> getTradeRecordsByTime(AbstractStock stock, int minutes) {
		List<TradeRecord> result = new ArrayList<TradeRecord>();
		stock.getTradeRecords().sort(new Comparator<TradeRecord>() {

			public int compare(TradeRecord recordA, TradeRecord recordB) {
				if (recordA.getTimestamp().before(recordB.getTimestamp())) {
					return 1;
				} else if (recordA.getTimestamp().after(recordB.getTimestamp())) {
					return -1;
				}
				return 0;
			}
		});

		Date currentTime = new Date();
		for (TradeRecord record : stock.getTradeRecords()) {
			if (currentTime.getTime() - record.getTimestamp().getTime() <= 15 * 60 * 1000) {
				result.add(record);
			} else {
				break;
			}
		}

		return result;

	}

	/**
	 * Finds the {@link AbstractStock} registered to this market with the given
	 * symbol.
	 * 
	 * @param symbol the symbol of the stock to look up
	 * @return the {@link AbstractStock} found
	 */
	private AbstractStock findStockBySymbol(String symbol) {
		AbstractStock stock = null;
		if (this.stockMap.containsKey(symbol)) {
			stock = this.stockMap.get(symbol);
		} else {
			throw new BusinessException(
					"Cannot find the stock " + symbol + " in the market. Please register the stock first");
		}
		return stock;
	}

}
