package com.ezypay.api.service;

import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.ezypay.api.subscription.objects.SubscriptionTypeEnum;

public class SubscriptionServiceTest {
	
	SubscriptionService subscriptionService;
	
	@Before
	public void init() {
		subscriptionService = new SubscriptionService();
	}
	
	@Test
	public void testValidateSubscriptionTypeDaily() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime startDate = fmt.parseDateTime("20200101");
		
	    assertTrue(subscriptionService.validateSubscriptionType(SubscriptionTypeEnum.WEEKLY, "Wednesday", startDate));
		
		
	}
}
