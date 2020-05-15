package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class AdminUIControllerTest extends AbstractControllerTest {

    private final static String AJAX_URL = "/ajax/admin/users/";

    @Test
    void checkEnable() throws Exception {
        boolean checkStatus = USER.isEnabled();
        String switchedStatus = String.valueOf(!checkStatus);
        ResultActions action = perform(MockMvcRequestBuilders.post(AJAX_URL + USER_ID)
                .param("status", switchedStatus)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(!checkStatus, userService.get(USER_ID).isEnabled());
    }
}