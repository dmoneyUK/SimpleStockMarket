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
######InvalidValueException
Based on common knowledge, "Price" and "Quantity" should be POSITIVE. So, define InvalidValueException for the cases where ZERO or Negative values are found. Specifically, the cases listed below will get InvalidValueException:
  *  In "Dividend Yield" calculation (Both of Common or Preferred stocks): "Price" is NON-POSITIVE;
  *  In "P/E Ratio" calculation: "Price" is NON-POSITIVE;
  *  In "Volume Weighted Stock Price" calculation: either "Price" or "Quantity" is NON-POSITIVE;(It may not be a critical error in the formula, but it is weird to have a trade record with an invalid price or quantity. So better to throw exception to alert.) 
  *  In "GBCE All Share Index" calculation: any "Price" is NON-POSITIVE;
  *  In "Record Trade": either "Price" or "Quantity" is NON-POSITIVE.

2.  It is not clear how to calculate the "Dividend" in the "P/E Ratio" formula. Assume it is same as the "Last Dividend" in the examples and the "Dividend" should be a POSITIVE value. However, the first example shows "LastDividend==0". So, define it is as a NON-Negative number. And if it is ZERO, "P/E Ratio" calculation will throw an BusinessException.

######BusinessException
BusinessException will be used for the edge cases which are not related to calculation or the requirements are not cleared. Specifically, it will be used in the following scenarios:
  *  Register an existing stock to the market service;
  *  Unregister a stock which has not registered to the market service;
  *  Cannot find the given stock symbol in the registered stock in the service;
  *  When calculate "P/E Ratio" and the "Dividend" is NON-POSITIVE. It is not clear how to calculate the "Dividend" in the "P/E Ratio" formula. Assume it is same as the "Last Dividend" in the examples and the "Dividend" should be a POSITIVE value. However, the first example shows "LastDividend==0". Hence, throw BusinessException rather than InvalidValueException.
  *  When calculate the "GBCE All Share Index" and if no any stock has registered to the market service;

The idea of BusinessException is to let calling method to do further process.
  




