package com.ezypay.api.subscription.objects;

public enum SubscriptionTypeEnum {
	DAILY("Daily"),
	WEEKLY("Weekly"),
	MONTHLY("Monthly");
	
	private final String subscriptionType;
	
	SubscriptionTypeEnum(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	
	public String getSubscriptionTypeEnum() {
		return this.subscriptionType;
	}
	
    public static SubscriptionTypeEnum fromString(String text) {
        for (SubscriptionTypeEnum subscriptionTypeEnum : SubscriptionTypeEnum.values()) {
            if (subscriptionTypeEnum.subscriptionType.equalsIgnoreCase(text)) {
                return subscriptionTypeEnum;
            }
        }
        return null;
    }
}
