package com.ezypay.api.subscription.response;

import com.ezypay.api.base.response.BaseResponseObject;

public class SubscriptionResponseObject extends BaseResponseObject {
	private Double amount;
	private String subscriptionType;
	private String subscriptionDates;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	public String getSubscriptionDates() {
		return subscriptionDates;
	}
	public void setSubscriptionDates(String subscriptionDates) {
		this.subscriptionDates = subscriptionDates;
	}
	
	
}
