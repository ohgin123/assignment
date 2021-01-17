package com.ezypay.api.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezypay.api.service.SubscriptionService;
import com.ezypay.api.subscription.objects.SubscriptionTypeEnum;
import com.ezypay.api.subscription.request.SubscriptionRequestObject;
import com.ezypay.api.subscription.response.SubscriptionResponseObject;
import com.ezypay.api.utility.MD5;

@Component
@RequestMapping("/subscription")
@PropertySource("classpath:messages.properties")
public class SubscriptionController {
	Logger logger = Logger.getLogger(SubscriptionController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SubscriptionResponseObject> createSubscription(@RequestHeader Map<String, String> headers, @RequestBody SubscriptionRequestObject subscriptionRequestObject ) {
		Double amount;
		SubscriptionTypeEnum subscriptionTypeEnum;
		String day;
		DateTime startDate;
		DateTime endDate;
		String secretKey = headers.get("secret-key").toUpperCase();
		
		try {
			amount = subscriptionRequestObject.getAmount();
			subscriptionTypeEnum = SubscriptionTypeEnum.fromString(subscriptionRequestObject.getSubscriptionType());
			day = subscriptionRequestObject.getDay();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
			startDate = fmt.parseDateTime(subscriptionRequestObject.getStartDate());
			endDate = fmt.parseDateTime(subscriptionRequestObject.getEndDate());
		} catch (Exception e) {
			logger.debug("Invalid Parameters");
			SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();
			subscriptionResponseObject.setMessage(env.getProperty("invalidParameters"));
			return new ResponseEntity<>(subscriptionResponseObject, HttpStatus.BAD_REQUEST);
		}
		
		String secretKeyCombine = String.valueOf(amount) + "-" + subscriptionRequestObject.getSubscriptionType() + "-" + day + "-" + subscriptionRequestObject.getStartDate() + "-" + subscriptionRequestObject.getEndDate();
		String myHash = "";
		try {
			myHash = MD5.getMd5(secretKeyCombine);
		} catch (NoSuchAlgorithmException e) {
			logger.debug("Unexpected Error");
			SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();
			subscriptionResponseObject.setMessage(env.getProperty("unexpectedError"));
			return new ResponseEntity<>(subscriptionResponseObject, HttpStatus.BAD_REQUEST);
		}
		if (!myHash.equals(secretKey)) {
			SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();
			subscriptionResponseObject.setMessage(env.getProperty("dataNotPassMD5"));
			return new ResponseEntity<>(subscriptionResponseObject, HttpStatus.BAD_REQUEST);
		}
		Boolean validateSubscriptionService = subscriptionService.validateSubscriptionType(subscriptionTypeEnum, day, startDate);
		if (!validateSubscriptionService) {
			logger.debug("Invalid Days");
			SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();
			subscriptionResponseObject.setMessage(env.getProperty("invalidDays"));
			return new ResponseEntity<>(subscriptionResponseObject, HttpStatus.BAD_REQUEST);
		}
		
		String invoiceDates = subscriptionService.getInvoiceDateList(subscriptionTypeEnum, startDate, endDate);

		//int status = apiKeyRepository.insertApiKey(apiRequestObject.getApiValue(),calendar.getTime());
		SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();
		subscriptionResponseObject.setAmount(amount);
		subscriptionResponseObject.setMessage(env.getProperty("successful"));
		subscriptionResponseObject.setSubscriptionDates(invoiceDates);
		subscriptionResponseObject.setSubscriptionType(subscriptionTypeEnum.toString());
		return new ResponseEntity<>(subscriptionResponseObject, HttpStatus.OK);
	}
}
