package com.myretail.product.dto;

import java.io.Serializable;

public class PriceDTO implements Serializable{
	
	private static final long serialVersionUID = 2646195566936380306L;
	
	private String productID;
	private double value;
	private String currencyCode;

	public double getValue() {
		return value;
	}

	public void setValue(double d) {
		this.value = d;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}
	
	

}
