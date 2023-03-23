package com.senla.controller;

import com.senla.config.LiquibaseConfig;
import com.senla.config.TestJPAConfig;
import com.senla.config.WebConfig;
import com.senla.config.WebSecurityConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestJPAConfig.class, LiquibaseConfig.class, WebSecurityConfig.class})
class PurchaseControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final static String ROOT_URL = "/purchases";

    private final static String CONTENT_JSON = "application/json";

    private final static String NEW_DATA = "{\n" +
            "        \"name\": \"testPurchase\",\n" +
            "        \"transactionDate\": \"2023-02-15 15:10:00\",\n" +
            "        \"sum\": 101.70,\n" +
            "        \"buyer\": {\n" +
            "            \"id\": 1,\n" +
            "            \"firstName\": \"Ivan\",\n" +
            "            \"surName\": \"Ivanov\",\n" +
            "            \"phoneNumber\": \"+32311212\",\n" +
            "            \"email\": \"ivan@ivanov.by\",\n" +
            "            \"birthday\": null,\n" +
            "            \"score\": 0.00,\n" +
            "            \"isActive\": false,\n" +
            "            \"username\": \"user\",\n" +
            "            \"password\": \"password\"\n" +
            "        },\n" +
            "        \"card\": {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"gold_card\",\n" +
            "            \"number\": 123123,\n" +
            "            \"discount\": 0.30,\n" +
            "            \"owner\": {\n" +
            "                \"id\": 1,\n" +
            "                \"firstName\": \"Ivan\",\n" +
            "                \"surName\": \"Ivanov\",\n" +
            "                \"phoneNumber\": \"+32311212\",\n" +
            "                \"email\": \"ivan@ivanov.by\",\n" +
            "                \"birthday\": null,\n" +
            "                \"score\": 0.00,\n" +
            "                \"isActive\": false,\n" +
            "                \"username\": \"user\",\n" +
            "                \"password\": \"password\"\n" +
            "            },\n" +
            "            \"policy\": {\n" +
            "                \"id\": 1,\n" +
            "                \"title\": \"OZpolicy\",\n" +
            "                \"minDiscount\": 0.05,\n" +
            "                \"maxDiscount\": 0.50,\n" +
            "                \"discountStep\": 0.05,\n" +
            "                \"trademark\": {\n" +
            "                    \"id\": 1,\n" +
            "                    \"title\": \"OZ\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @AfterEach
    @Sql(scripts = "classpath:sql/delete-all-from-table.sql")
    public void destroyDB() {
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesCouponController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("purchaseController"));
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser(roles = {"ADMIN"})
    public void getAllPurchases_ResponseOk() {
        mockMvc.perform(get(ROOT_URL).param("pageNumber", "1")
                                     .param("pageSize", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(scripts = "classpath:sql/insert_data.sql")
    public void getPurchaseById_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/{id}"), "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getPurchaseByUser_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/user/{id}"), "1")
                        .param("pageNumber", "1")
                        .param("pageSize", "5"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getPurchaseByCard_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/card/{id}"), "1")
                        .param("pageNumber", "1")
                        .param("pageSize", "5"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @Sql(scripts = "classpath:sql/insert_data.sql")
    public void addPurchase_ResponseCreated() {
        mockMvc.perform(post(ROOT_URL).contentType(CONTENT_JSON).content(NEW_DATA))
                .andExpect(status().isForbidden());
    }
}