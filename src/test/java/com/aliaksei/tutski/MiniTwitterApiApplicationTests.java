package com.aliaksei.tutski;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.aliaksei.tutski.message.Message;
import com.aliaksei.tutski.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MiniTwitterApiApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	private Message message;
	private User user;
	
	@Before 
    public void setUp(){
    	user = new User("alexi");
    	user.setId(1);
    	message = new Message();
    	message.setId(1);
    	message.setText("some text");
    	message.setUser(user);
    }
	
	@Test
	public void testNewUserCreationScenario() throws Exception{
		
		this.mvc.perform(post("/alexi/messages")
    			.contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(asJsonString(message)))
        .andExpect(status().isCreated())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(message.getId())))
        .andExpect(jsonPath("$.text", is(message.getText())))
        .andExpect(jsonPath("$.user.id", is(user.getId())))
        .andExpect(jsonPath("$.user.userName", is(user.getUserName())));
		
		this.mvc.perform(get("/alexi/messages")
    			.contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(message.getId())))
        .andExpect(jsonPath("$[0].text", is(message.getText())))
        .andExpect(jsonPath("$[0].user.id", is(user.getId())))
    	.andExpect(jsonPath("$[0].user.userName", is(user.getUserName())));
	}
	
	/*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
