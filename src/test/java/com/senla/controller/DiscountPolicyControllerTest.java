package com.senla.controller;

import com.senla.config.LiquibaseConfig;
import com.senla.config.TestJPAConfig;
import com.senla.config.WebConfig;
import com.senla.config.WebSecurityConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestJPAConfig.class, LiquibaseConfig.class, WebSecurityConfig.class})
class DiscountPolicyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final static String ROOT_URL = "/policies";

    private final static String CONTENT_JSON = "application/json";

    private final static String NEW_DATA = "{\n" +
            "        \"title\": \"testPolicy\",\n" +
            "        \"minDiscount\": 0.01,\n" +
            "        \"maxDiscount\": 1.00,\n" +
            "        \"discountStep\": 0.05,\n" +
            "        \"trademark\": {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"OZ\"\n" +
            "        }\n" +
            "    }";
    private final static String UPDATING_DATA = "{\n" +
            "        \"id\": 1,\n" +
            "        \"title\": \"OZpoli\",\n" +
            "        \"minDiscount\": 0.05,\n" +
            "        \"maxDiscount\": 0.50,\n" +
            "        \"discountStep\": 0.10,\n" +
            "        \"trademark\": {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"OZ\"\n" +
            "        }\n" +
            "    }";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesCouponController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("discountPolicyController"));
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser(roles = {"ADMIN"})
    public void getAllPolicies_ResponseOk() {
        mockMvc.perform(get(ROOT_URL)
                        .param("pageNumber", "1")
                        .param("pageSize", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser
    public void getPolicyById_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/{id}"), "1"))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser(roles = {"ADMIN"})
    public void getPolicyByTrademark_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/trademark/{id}"), "1")
                        .param("pageNumber", "1")
                        .param("pageSize", "5"))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser
    public void addPolicy_ResponseCreated() {
        mockMvc.perform(post(ROOT_URL).contentType(CONTENT_JSON).content(NEW_DATA))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser(roles = {"ADMIN"})
    public void updatePolicy_ResponseAccepted() {
        mockMvc.perform(put(ROOT_URL).contentType(CONTENT_JSON).content(UPDATING_DATA))
                .andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletePolicy_ResponseForbidden() {
        mockMvc.perform(delete(ROOT_URL.concat("/{id}"), "1"))
                .andExpect(status().isForbidden());
    }
}