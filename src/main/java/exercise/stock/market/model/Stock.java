package exercise.stock.market.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercises.stock.exceptions.InvalidPriceException;

/**
 * A model class of stock.
 * @author DMONEY
 *
 */
public class Stock {
	
	/** The symbol of the stock. */
	private String symbol;
	
	/** Type of the stock. */
	private StockType type;
	
	/** The last dividend value */
	private BigDecimal lastDividend;

	/** The fixed dividend value. */
	private BigDecimal fixedDividend;
	
	/** The par value. */
	private BigDecimal parValue;
	
	/** The price of this stock. */
	private BigDecimal price;
	
	/** A map of stocks and their trade records.*/
	private List<TradeRecord> tradeRecords;

	/**
	 * Constructor.
	 */
	public Stock() {
		this.tradeRecords = new ArrayList<TradeRecord>();
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the type
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(StockType type) {
		this.type = type;
	}

	/**
	 * @return the lastDividend
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the fixedDividend
	 */
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * @return the parValue
	 */
	public BigDecimal getParValue() {
		return parValue;
	}

	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	/**
	 * @return the tradeRecords
	 */
	public List<TradeRecord> getTradeRecords() {
		return this.tradeRecords;
	}

	/**
	 * @param tradeRecords the tradeRecords to set
	 */
	public void setTradeRecords(List<TradeRecord> tradeRecords) {
		this.tradeRecords = tradeRecords;
	}
	
	/**
	 * Adds a {@link TradeRecord} to the trade records of this stock.
	 * @param tradeRecords the tradeRecords to set
	 */
	public void addTradeRecord(TradeRecord tradeRecord) {
		this.tradeRecords.add(tradeRecord);
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		if(BigDecimal.ZERO.compareTo(price)>=0){
			throw new InvalidPriceException(price + "is a invalid price for the stock "+this.symbol);
		}
		this.price = price;
	}
	

}
