Super Simple Socket Market
======

A simple socket market which manages a couple of stocks and provides the functions below:</br>
For a given stock,</br>
i.   Given any price as input, calculate the dividend yield</br>
ii.  Given any price as input, calculate the P/E Ratio</br>
iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price</br>
iv.  Calculate Volume Weighted Stock Price based on trades in past 15 minutes</br>
v.   Calculate the GBCE All Share Index using the geometric mean of prices for all stocks</br>


Precision
========

1.  BigDecimal are used to define most of the fields of numeric value, like price, dividend etc; This is mainly because: it is not clarified these values must be integer and BigDecimal is a bit easier to used in divide calculation.)
2.  Since no requirements for the precision, define the precision as below:
  *  the divide calculation uses precision scale == 7;
  *  the "Dividend Yield" and "P/E Ratio" results have a precision scale == 3 (to present xx.x%);
  *  the "GBCE All Share Index" and "Volume Weighted Stock Price" results have a precision scale == 0 (based on the example date which are all integer)


	    

Edge cases and Exceptions
========
1.  "Price" and "Quantity" are checked to be a POSITIVE in calculation. If ZERO or Negative value will cause InvalidValueException.
2.  It is not clear how to calculate the "Dividend" in the "P/E Ratio" formula. Assume it is same as the "Last Dividend" in the examples. Considering the first example has "LastDividend==0", define it is a NON-Negative number. And if it is ZERO, P/E Ratio will throw an BusinessException.
3.	 Record Trade will throw InvalidValueException if a recorded has non-positive price or quantity;
4.  Volume Weighted Stock Price will throw an InvalidValueException if any stock has NON-POSITIVE price or quantity.(It may not be a critical error in the formula, but it is weird to has a trade record with invalid price or quantity. So better to throw exception to alert) 
5.  BusinessException will be thrown if try to manipulate a stock not exists in the market or register an existing stock again.




