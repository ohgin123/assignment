/**
 * 
 */
package com.ezypay.api.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Filter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author ohgin
 *
 */
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

//
//	@Test
//	public void test() {
//		this.mockMvc
//	    .perform(
//	      post("/api/tasks")
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .content("{\"taskTitle\": \"Learn MockMvc\"}")
//	        .with(csrf())
//	        .with(SecurityMockMvcRequestPostProcessors.user("duke"))
//	    )
//	    .andExpect(status().isCreated())
//	    .andExpect(header().exists("Location"))
//	    .andExpect(header().string("Location", Matchers.containsString("42")));
//		
//	}

}
