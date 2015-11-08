package exercise.stock.market.model;

import java.math.BigDecimal;
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
	private BigDecimal quantity;
	
	/** A buy/sell trade indicator. */
	private BuyOrSell indicator; 
	
	/** The trade price. */
	private BigDecimal price;
	


	/**
	 * @param stockSymbol
	 * @param timestamp
	 * @param quantity
	 * @param indicatorx
	 * @param price
	 */
	public TradeRecord(String stockSymbol,Date timestamp, BigDecimal quantity, BuyOrSell indicator, BigDecimal price) {
		this.stockSymbol = stockSymbol;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.indicator = indicator;
		this.price = price;
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
	public BigDecimal getQuantity() {
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
