package exercise.stock.market.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exercise.stock.market.test.TestUtils;

public class TestPreferredStock {

	/** The {@link PreferredStock} under test. */
	PreferredStock stock;

	
	/**
	 * Runs before every test.
	 */
	@Before
	public void setUp() {
		this.stock = TestUtils.getDefaultPreferredStock();
	}

	/**
	 * Tests for {@link AbstractStock#getFixedDividend()} and
	 * {@link AbstractStock#getFixedDividend()}.
	 * <ul>
	 * <li>call {@link AbstractStock#setFixedDividend(BigDecimal)}</li>
	 * <li>verify that the {@link AbstractStock#getFixedDividend()} returns the
	 * expected value</li>
	 * </ul>
	 */
	@Test
	public void testSetAndGetFixedDividend() {
		BigDecimal value = TestUtils.getRamdamBigDecimal();
		this.stock.setFixedDividend(value);
		Assert.assertEquals(value, this.stock.getFixedDividend());
	}
}
