package com.ezypay.api.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.ezypay.api.subscription.objects.SubscriptionTypeEnum;


@Service
public class SubscriptionService {
	public Boolean validateSubscriptionType(SubscriptionTypeEnum subscriptionType,
					String day, DateTime startDate) {
		
		if (subscriptionType.equals(SubscriptionTypeEnum.WEEKLY)) {
			String startDateWeekDay = startDate.dayOfWeek().getAsText();
			if (!startDateWeekDay.equals(day)) {
				return false;
			}
		}
		else if (subscriptionType.equals(SubscriptionTypeEnum.MONTHLY)) {
			int dayInt;
			try {
				dayInt = Integer.valueOf(day);
			} catch (Exception e) {
				return false;
			}
			
			int startDateDay = startDate.get(DateTimeFieldType.dayOfMonth());
			if (startDateDay != dayInt) {
				return false;
			}
		}
		
		return true;
	}
	
	public String getInvoiceDateList(SubscriptionTypeEnum subscriptionType, DateTime startTime, DateTime endTime) {
		List<String> dateList = new ArrayList<String>(); 
			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			while (startTime.isBefore(endTime)) {
				
				String startTimeDate = fmt.print(startTime);
				dateList.add(startTimeDate);
				if (subscriptionType.equals(SubscriptionTypeEnum.DAILY)) {
					startTime = startTime.plusDays(1);
				} else if (subscriptionType.equals(SubscriptionTypeEnum.WEEKLY)) {
					startTime = startTime.plusWeeks(1);
				} else if (subscriptionType.equals(SubscriptionTypeEnum.MONTHLY)) {
					startTime = startTime.plusMonths(1);
				}
			}
			
			if (startTime.isAfter(endTime)) {
				dateList.add(fmt.print(endTime));
			}
	
			String result = dateList.stream().reduce("", (subConcatStr, str) -> subConcatStr + "," + str);
			result = result.substring(1);
			result = "[\"" + result +  "\"]";
			System.out.println(result);
			return result.trim();
	}
}
