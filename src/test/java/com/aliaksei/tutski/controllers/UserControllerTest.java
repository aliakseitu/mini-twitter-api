package com.aliaksei.tutski.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.aliaksei.tutski.user.User;
import com.aliaksei.tutski.user.UserController;
import com.aliaksei.tutski.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    
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
    
    @Test
    public void testFollowingUser() throws Exception {
    	
    	User fUser = new User("peter");
    	this.mvc.perform(put("/alexi/follow")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(fUser)))
        
    	.andExpect(status().isOk())
    	.andExpect(content().string(""));
    	
    }
}
