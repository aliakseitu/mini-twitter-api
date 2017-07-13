package com.aliaksei.tutski;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.aliaksei.tutski.message.Message;
import com.aliaksei.tutski.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MiniTwitterApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    private static final int COUNT = 10;
    private static final int FUSERSCOUNT = 3;
    private static final int FMESSAGESCOUNT = 2;
    private static final String MAIN_USER = "alexi";
    
    private Message[] messages;
    private User user;
    private Message[][] followingUserMessages;
    private User[] followingUsers;

    @Before
    public void setUp() {
        user = new User(MAIN_USER);
        user.setId(1);
        messages = new Message[10];
        for (int i = 0; i < COUNT; i++) {
            Message message = new Message();
            message.setId(i + 1);
            message.setText("some text " + i + 1);
            message.setUser(user);
            messages[i] = message;
        }
        followingUserMessages = new Message[FUSERSCOUNT][FMESSAGESCOUNT];
        followingUsers = new User[FUSERSCOUNT];
        for (int i = 0; i < FUSERSCOUNT; i++) {
            User u = new User("UserName + " + i);
            u.setId(2 + i);
            for (int j = 0; j < FMESSAGESCOUNT; j++) {
                Message message = new Message();
                message.setId(COUNT + FUSERSCOUNT * i + j + 1);
                message.setText("some text for user " + u.getUserName() + j + 1);
                message.setUser(u);
                followingUserMessages[i][j] = message;
            }
            followingUsers[i] = u;
        }
        
        System.out.println(followingUsers);
    }

    @After
    public void tearDown(){
        
    }
    @Test
    public void testNewUserCreationScenario() throws Exception {

        this.mvc.perform(
                post("/" + MAIN_USER + "/messages")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(messages[0])))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(messages[0].getId())))
                .andExpect(jsonPath("$.text", is(messages[0].getText())))
                .andExpect(jsonPath("$.user.id", is(user.getId())))
                .andExpect(jsonPath("$.user.userName", is(MAIN_USER)));

        this.mvc.perform(
                    get("/" + MAIN_USER + "/messages")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(messages[0].getId())))
                .andExpect(jsonPath("$[0].text", is(messages[0].getText())))
                .andExpect(jsonPath("$[0].user.id", is(user.getId())))
                .andExpect(jsonPath("$[0].user.userName", is(user.getUserName())));
    }

    @Test
    public void testWall() throws Exception {

        for (int i = 0; i < COUNT; i++)
            testCreateUserWithMessage(user, messages[i]);
 
       ResultActions ra = this.mvc.perform(
                get("/" + MAIN_USER + "/messages")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(COUNT)));
       
       for (int i = 0; i < COUNT; i++)
           ra = ra
                   .andExpect(jsonPath("$[" + i +"].id", is(messages[COUNT - i - 1].getId())))
                   .andExpect(jsonPath("$[" + i +"].text", is(messages[COUNT - i - 1].getText())))
                   .andExpect(jsonPath("$[" + i +"].user.id", is(user.getId())))
                   .andExpect(jsonPath("$[" + i +"].user.userName", is(user.getUserName())));

    }

    @Test
    public void testFollowUser() throws Exception {
        
        testCreateUserWithMessage(user, messages[0]);
        testCreateUserWithMessage(followingUsers[0], messages[1]);
        
        testFollowAction(user, followingUsers[0]);

    }
    
    @Test
    public void testTimeline() throws Exception {
        testCreateUserWithMessage(user, messages[0]);
        
        for (int i = 0; i < FUSERSCOUNT; i++) {
            for (int k = 0; k < FMESSAGESCOUNT; k++)
                testCreateUserWithMessage(followingUsers[i], followingUserMessages[i][k]);
            testFollowAction(user, followingUsers[i]);
        }
        
        int all = FUSERSCOUNT * FMESSAGESCOUNT;
        ResultActions ra = this.mvc.perform(
                get("/" + MAIN_USER + "/timeline")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(all)));
       
        int idx = 0;
        for (int i = FUSERSCOUNT - 1; i >= 0; i--)
            for (int k = FMESSAGESCOUNT - 1; k >= 0; k--) {
                ra = ra
                        .andExpect(jsonPath("$[" + idx + "].text", is(followingUserMessages[i][k].getText())))
                        .andExpect(jsonPath("$[" + idx + "].user.id", is(followingUsers[i].getId())))
                        .andExpect(jsonPath("$[" + idx + "].user.userName", is(followingUsers[i].getUserName())));
                idx++;
            }
        
    }
    
    private ResultActions testCreateUserWithMessage(User u, Message m) throws Exception{
        return this.mvc.perform(
                    post("/" + u.getUserName() + "/messages")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(m)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(m.getId())))
                .andExpect(jsonPath("$.text", is(m.getText())))
                .andExpect(jsonPath("$.user.id", is(u.getId())))
                .andExpect(jsonPath("$.user.userName", is(u.getUserName())));
    }
    
    private ResultActions testFollowAction(User u, User toFollowUser) throws Exception{
        return this.mvc.perform(
                put("/" + u.getUserName() + "/follow")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(toFollowUser)))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
        
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
