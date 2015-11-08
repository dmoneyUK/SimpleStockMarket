package exercise.stock.market.service;

import java.math.BigDecimal;

public class Test {
	// num是被开方数，n是开方次数,precision设置保留几位小数
	public static BigDecimal  rootN_Decimal(BigDecimal num,int n,int precision)  
    {  
		BigDecimal N = new BigDecimal(n);
        BigDecimal x = num.divide(N,BigDecimal.ROUND_HALF_EVEN);  
        BigDecimal temp = BigDecimal.ZERO;  
       
          
        BigDecimal e=new BigDecimal("0.1");  
        for(int i=1;i<precision;++i)  
            e=e.divide(BigDecimal.TEN,i+1,BigDecimal.ROUND_HALF_EVEN);  
            
        do{
        	temp=x;  
            x = x.add(num.subtract(x.pow(n)).divide(N.multiply(x.pow(n-1)),precision,BigDecimal.ROUND_HALF_EVEN));
           	System.out.println("x="+ x);  
        }while(x.subtract(temp).abs().compareTo(e)>0);  
     
        return x.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

	public static void main(String[] args) {

		System.out.println(rootN_Decimal(new BigDecimal(100.41212), 3, 2));

	}
}