package com.fair.dto;

/**
 * DTO to hold country aggregate information for front end
 */

//@JsonIgnoreProperties({"pairs"})
public class ByCountryAggregate {

	private int tradeCount;
	private String[] pairs;
	private String originatingCountry;


	public String[] getPairs() {
		return pairs;
	}

	public void setPairs(String[] pairs) {
		this.pairs = pairs;
	}

	public int getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(int tradeCount) {
		this.tradeCount = tradeCount;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}
}