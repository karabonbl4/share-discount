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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestJPAConfig.class, TestLiquibaseConfiguration.class})
@Sql("classpath:sql/insert_data.sql")
class DiscountCardControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final static String NEW_CARD = "{\n" +
            "        \"name\": \"silver_card\",\n" +
            "        \"number\": 321321,\n" +
            "        \"discount\": 0.20,\n" +
            "        \"ownerId\": {\n" +
            "            \"id\": 1,\n" +
            "            \"firstName\": \"Ivan\",\n" +
            "            \"surName\": \"Ivanov\",\n" +
            "            \"phoneNumber\": \"+32311212\",\n" +
            "            \"email\": \"ivan@ivanov.by\",\n" +
            "            \"birthday\": null,\n" +
            "            \"score\": 0.00,\n" +
            "            \"isActive\": false\n" +
            "        },\n" +
            "        \"discountPolicyId\": {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"OZpolicy\",\n" +
            "            \"minDiscount\": 0.05,\n" +
            "            \"maxDiscount\": 0.50,\n" +
            "            \"discountStep\": 0.05,\n" +
            "            \"trademarkId\": {\n" +
            "                \"id\": 1,\n" +
            "                \"title\": \"OZ\"\n" +
            "            }\n" +
            "        }\n" +
            "    }";
    private final static String UPDATING_CARD = "{\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"bronze_card\",\n" +
            "        \"number\": 258258,\n" +
            "        \"discount\": 0.10,\n" +
            "        \"ownerId\": {\n" +
            "            \"id\": 1,\n" +
            "            \"firstName\": \"Ivan\",\n" +
            "            \"surName\": \"Ivanov\",\n" +
            "            \"phoneNumber\": \"+32311212\",\n" +
            "            \"email\": \"ivan@ivanov.by\",\n" +
            "            \"birthday\": null,\n" +
            "            \"score\": 0.00,\n" +
            "            \"isActive\": false\n" +
            "        },\n" +
            "        \"discountPolicyId\": {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"OZpolicy\",\n" +
            "            \"minDiscount\": 0.05,\n" +
            "            \"maxDiscount\": 0.50,\n" +
            "            \"discountStep\": 0.05,\n" +
            "            \"trademarkId\": {\n" +
            "                \"id\": 1,\n" +
            "                \"title\": \"OZ\"\n" +
            "            }\n" +
            "        }\n" +
            "    }";;

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
        assertNotNull(webApplicationContext.getBean("discountCardController"));
    }

    @SneakyThrows
    @Test
    public void getAllCards_ResponseOk() {
        mockMvc.perform(get("/cards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @SneakyThrows
    @Test
    public void getCardById_ResponseOk() {
        mockMvc.perform(get("/cards/{id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @SneakyThrows
    @Test
    public void getCardByUser_ResponseOk() {
        mockMvc.perform(get("/cards/user/{id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @SneakyThrows
    @Test
    public void addCard_ResponseCreated() {
        mockMvc.perform(post("/cards/create").contentType("application/json").content(NEW_CARD))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    public void updateCard_ResponseAccepted() {
        mockMvc.perform(put("/cards/update").contentType("application/json").content(UPDATING_CARD))
                .andExpect(status().isAccepted());
    }

    @SneakyThrows
    @Test
    public void deleteCard_ResponseAccepted() {
        mockMvc.perform(delete("/cards/delete/{id}", "1"))
                .andExpect(status().isAccepted());
    }
}