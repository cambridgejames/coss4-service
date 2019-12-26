package cn.cambridge.hexohero.basic.exception;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommonExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authorizationExceptionHandler() throws Exception {
        String loginResultString = mockMvc.perform(MockMvcRequestBuilders.post("/login/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\"username\":\"testUser\",\"password\":\"testUser\"}"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        String helloResultString = mockMvc.perform(MockMvcRequestBuilders.post("/common/helloWorld"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String trulyResultString = mapper.writeValueAsString(CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PERMISSION_DENIED));
        System.out.println(loginResultString);
        System.out.println(helloResultString);
        System.out.println(trulyResultString);
    }

}
