package exercise.stock.market.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * A model class of trade record containing the trade information, like
 * timestamp, quantity of shares, buy or sell indicator and traded price
 */
public class TradeRecord {

	/** The stock symbol*/
	private String stockSymbol;
	
	/** The time stamp of this trade.*/
	private Date timestamp;
	
	/** The quantify of this trade. */
	private BigInteger quantity;
	
	/** A buy/sell trade indicator. */
	private BuyOrSell indicator; 
	
	/** The trade price. */
	private BigDecimal price;
	
	/**
	 * Constructor.
	 * 
	 * @param stockSymbol
	 * @param timestamp
	 * @param quantity
	 * @param indicator
	 * @param price
	 */
	public TradeRecord(String stockSymbol, Date timestamp, BigInteger quantity, BuyOrSell indicator, BigDecimal price) {
		this.stockSymbol = stockSymbol;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.indicator = indicator;
		this.price = price;
	}

	/**
	 * Constructor.
	 */
	public TradeRecord() {
	}

	/**
	 * @param stockSymbol the stockSymbol to set
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(BuyOrSell indicator) {
		this.indicator = indicator;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the quantity
	 */
	public BigInteger getQuantity() {
		return quantity;
	}

	/**
	 * @return the indicator
	 */
	public BuyOrSell getIndicator() {
		return indicator;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}


}
