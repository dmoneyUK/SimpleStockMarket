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
import exercise.stock.market.model.Stock;
import exercise.stock.market.model.StockType;
import exercise.stock.market.model.TradeRecord;

/**
 * A calculator that can calculate various values.
 * 
 * @author DMONEY
 *
 */
public class StockMarketService {

	/** A stocks managed in this service. */
	private Map<String, Stock> stockMap;

	/**
	 * Constructor.
	 */
	public StockMarketService() {
		this.stockMap = new HashMap<String, Stock>();
	}

	/**
	 * Registers a stock to this service.
	 * 
	 * @param stock
	 */
	public void registerStock(Stock stock) {
		if (this.stockMap.containsKey(stock.getSymbol())) {
			System.out.println("The stock " + stock.getSymbol() + " has been registerd.");
			return;
		}
		this.stockMap.put(stock.getSymbol(), stock);
	}

	/**
	 * Unregisters a stock from this service.
	 * 
	 * @param stockSymbol
	 */
	public void unregisterStock(String stockSymbol) {
		if (!this.stockMap.containsKey(stockSymbol)) {
			System.out.println("The stock " + stockSymbol + " has not been registerd.");
		}
		this.stockMap.remove(stockSymbol);
	}

	/**
	 * Calculates the dividend yield of the given stock based on the given
	 * price.
	 * 
	 * @param symbol the symbol of the stock to be calculated
	 * @param price the trade price
	 */
	public BigDecimal getDividendYield(String symbol, BigDecimal price) {
		Stock stock = findStockBySymbol(symbol);
		if(stock==null){
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		StockType stockType = stock.getType();
		if (StockType.Common.equals(stockType)) {
			result = stock.getLastDividend().divide(price);
		} else if (StockType.Preferred.equals(stockType)) {
			result = stock.getFixedDividend().multiply(stock.getParValue()).divide(price);
		}
		return result;
	}

	/**
	 * Calculates the P/E Ratio of the given stock based on the given price.
	 * 
	 * @param stock the stock to be calculated
	 * @param price the trade price
	 */
	public BigDecimal getPERatio(String symbol, BigDecimal price) {
		Stock stock = findStockBySymbol(symbol);
		if(stock==null){
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal dividend = getDividendYield(symbol, price);
		result = price.divide(dividend);
		return result;
	}

	/**
	 * Records a trade, with time stamp, quantity of shares, buy or sell
	 * indicator and traded price.
	 */
	public void recordTrade(String symbol, Date timestamp, BigInteger quantity, BuyOrSell indicator, BigDecimal price) {
		Stock stock = findStockBySymbol(symbol);
		if(stock==null){
			return;
		}
		TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
		stock.addTradeRecord(record);
	}

	/**
	 * Gets the volume weighted stock price based on the trades in the 15
	 * minutes.
	 * 
	 * @param stock the stock to calculate.
	 * @return the VolumeWeightedStockPrice in the last in 15 minutes.
	 */
	public BigDecimal getVolumeWeightedStockPrice(String symbol) {
		Stock stock = findStockBySymbol(symbol);
		if(stock==null){
			return null;
		}
		BigDecimal result = BigDecimal.ZERO;
		List<TradeRecord> tradeRecords = getTradeRecordsByTime(stock, 15);
		if (tradeRecords.isEmpty()) {
			System.out.println("No trade record found in the last 15 minutes for the stock " + stock.getSymbol());
		} else {
			BigDecimal priceSum = BigDecimal.ZERO;
			BigInteger quantitySum = BigInteger.ZERO;
			
			for (TradeRecord record : tradeRecords) {

				priceSum = priceSum.add(record.getPrice().multiply(new BigDecimal(record.getQuantity())));
				quantitySum = quantitySum.add(record.getQuantity());
			}
			result = priceSum.divide(new BigDecimal(quantitySum));
		}
		return result;

	}

	/**
	 * Calculates the GBCE all share index value with 2 scale, based on the prices of all the stocks in the market service.
	 * @return the GBCE all share index value, with 2 scale.
	 */
	public BigDecimal getGBCEAllShareIndex() {
		if(this.stockMap.isEmpty()){
			System.out.println("No any stock registered to this market in current. Please register stocks first.");
		}
		BigDecimal accumulate = BigDecimal.ONE;
		for(Stock stock:this.stockMap.values()){
			accumulate = accumulate.multiply(stock.getPrice());
		}
			int n = this.stockMap.size();
	        BigDecimal x = accumulate.divide(accumulate,BigDecimal.ROUND_HALF_EVEN);  
	        BigDecimal temp = BigDecimal.ZERO;  
	        BigDecimal e=new BigDecimal("000.1");
	          
	        do{
	        	temp=x;  
	            x = x.add(accumulate.subtract(x.pow(n)).divide(new BigDecimal(n).multiply(x.pow(n-1)),2,BigDecimal.ROUND_HALF_EVEN));
	           	System.out.println("x="+ x);  
	        }while(x.subtract(temp).abs().compareTo(e)>0);  
	     
	        return x.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	}

	/**
	 * Gets the trade records of the given stock in the last given minutes.
	 * 
	 * @param stock the {@link Stock} to search
	 * @param minutes the range of the time to search
	 * @return
	 */
	private List<TradeRecord> getTradeRecordsByTime(Stock stock, int minutes) {
		List<TradeRecord> result = new ArrayList<TradeRecord>();
		stock.getTradeRecords().sort(new Comparator<TradeRecord>() {

			public int compare(TradeRecord recordA, TradeRecord recordB) {
				if (recordA.getTimestamp().before(recordB.getTimestamp())) {
					return -1;
				} else if (recordA.getTimestamp().after(recordB.getTimestamp())) {
					return 1;
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
	 * Finds the {@link Stock} registered to this market with the given symbol.
	 * @param symbol the symbol of the stock to look up
	 * @return the {@link Stock} found
	 */
	private Stock findStockBySymbol(String symbol) {
		Stock stock =null;
		if (this.stockMap.containsKey(symbol)) {
			stock= this.stockMap.get(symbol);
		}else{
		System.out.print("Cannot find the stock " + symbol + " in the market. Please register the stock first");
		}
		return stock;
	}

}
