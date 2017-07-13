package com.aliaksei.tutski.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.aliaksei.tutski.message.Message;
import com.aliaksei.tutski.message.MessageController;
import com.aliaksei.tutski.message.MessageService;
import com.aliaksei.tutski.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    private User user;
    private Message message;

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

    @Before
    public void setUp() {
        user = new User("alexi");
        message = new Message();
        message.setId(1);
        message.setText("some text");
        message.setUser(user);
    }

    @Test
    public void testAddMessage() throws Exception {

        given(messageService.createMessageForUser(message, user.getUserName())).willReturn(message);

        this.mvc.perform(
                    post("/alexi/messages")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(message)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(message.getId())))
                .andExpect(jsonPath("$.text", is(message.getText())))
                .andExpect(jsonPath("$.user.id", is(user.getId())))
                .andExpect(jsonPath("$.user.userName", is(user.getUserName())));

        verify(messageService, times(1)).createMessageForUser(message, user.getUserName());
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void testGetAllUserMessages() throws Exception {

        given(messageService.getAllUserMessages(user.getUserName())).willReturn(Arrays.asList(message));

        this.mvc.perform(get("/alexi/messages")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(message.getId())))
                .andExpect(jsonPath("$[0].text", is(message.getText())))
                .andExpect(jsonPath("$[0].user.id", is(user.getId())))
                .andExpect(jsonPath("$[0].user.userName", is(user.getUserName())));

        verify(messageService, times(1)).getAllUserMessages(user.getUserName());
        verifyNoMoreInteractions(messageService);
    }
}
