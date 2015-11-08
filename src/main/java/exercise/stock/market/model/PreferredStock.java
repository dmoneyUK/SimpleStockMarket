package exercise.stock.market.model;

import java.math.BigDecimal;

/**
 * A class of the preferred stock.
 * @author DMONEY
 *
 */
public class PreferredStock extends BaseStock{

	/** The fixed dividend value. */
	private BigDecimal fixedDividend;

	public PreferredStock(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal price, BigDecimal fixedDividend) {
		super(symbol, lastDividend, parValue, price);
		this.fixedDividend = fixedDividend;
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
	
}
