package com.senla.controller;

import com.senla.config.TestJPAConfig;
import com.senla.config.TestLiquibaseConfiguration;
import com.senla.config.WebConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestJPAConfig.class, TestLiquibaseConfiguration.class})
@Sql("classpath:sql/insert_data.sql")
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
            "        \"user\": {\n" +
            "            \"id\": 1,\n" +
            "            \"firstName\": \"Ivan\",\n" +
            "            \"surName\": \"Ivanov\",\n" +
            "            \"phoneNumber\": \"+32311212\",\n" +
            "            \"email\": \"ivan@ivanov.by\",\n" +
            "            \"birthday\": null,\n" +
            "            \"score\": 0.00,\n" +
            "            \"isActive\": false\n" +
            "        },\n" +
            "        \"card\": {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"gold_card\",\n" +
            "            \"number\": 123123,\n" +
            "            \"discount\": 0.30,\n" +
            "            \"ownerId\": {\n" +
            "                \"id\": 1,\n" +
            "                \"firstName\": \"Ivan\",\n" +
            "                \"surName\": \"Ivanov\",\n" +
            "                \"phoneNumber\": \"+32311212\",\n" +
            "                \"email\": \"ivan@ivanov.by\",\n" +
            "                \"birthday\": null,\n" +
            "                \"score\": 0.00,\n" +
            "                \"isActive\": false\n" +
            "            },\n" +
            "            \"discountPolicyId\": {\n" +
            "                \"id\": 1,\n" +
            "                \"title\": \"OZpolicy\",\n" +
            "                \"minDiscount\": 0.05,\n" +
            "                \"maxDiscount\": 0.50,\n" +
            "                \"discountStep\": 0.05,\n" +
            "                \"trademarkId\": {\n" +
            "                    \"id\": 1,\n" +
            "                    \"title\": \"OZ\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
    public void getAllPurchases_ResponseOk() {
        mockMvc.perform(get(ROOT_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    public void getPurchaseById_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/{id}"), "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @SneakyThrows
    @Test
    public void getPurchaseByUser_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/user/{id}"), "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    public void getPurchaseByCard_ResponseOk() {
        mockMvc.perform(get(ROOT_URL.concat("/card/{id}"), "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_JSON));
    }

    @SneakyThrows
    @Test
    public void addPurchase_ResponseCreated() {
        mockMvc.perform(post(ROOT_URL).contentType(CONTENT_JSON).content(NEW_DATA))
                .andExpect(status().isCreated());
    }


    @SneakyThrows
    @Test
    public void deletePurchase_ResponseAccepted() {
        mockMvc.perform(delete(ROOT_URL.concat("/{id}"), "1"))
                .andExpect(status().isAccepted());
    }
}