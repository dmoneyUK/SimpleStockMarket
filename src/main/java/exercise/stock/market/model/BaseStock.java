package exercise.stock.market.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * A base class of stocks.
 * 
 * @author DMONEY
 *
 */
public class BaseStock {

	/** The symbol of the stock. */
	protected String symbol;

	/** The last dividend value */
	protected BigDecimal lastDividend;

	/** The par value. */
	protected BigDecimal parValue;

	/** The price of this stock. */
	protected BigDecimal price;

	/** A map of stocks and their trade records. */
	protected List<TradeRecord> tradeRecords;

	/**
	 * @param symbol
	 * @param lastDividend
	 * @param parValue
	 * @param price
	 * @param tradeRecords
	 */
	public BaseStock(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal price) {
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.price = price;
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
	 * 
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
		this.price = price;
	}

}
