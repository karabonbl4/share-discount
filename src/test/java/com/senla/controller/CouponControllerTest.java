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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestJPAConfig.class, LiquibaseConfig.class, WebSecurityConfig.class})
public class CouponControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final static String NEW_COUPON = "{\n" +
            "        \"name\": \"testCoupon\",\n" +
            "        \"startDate\": \"2012-12-17\",\n" +
            "        \"endDate\": \"2021-08-22\",\n" +
            "        \"discount\": 0.25,\n" +
            "        \"isUsed\": false,\n" +
            "        \"trademark\": {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"OZ\"\n" +
            "        }\n" +
            "    }";
    private final static String UPDATING_COUPON = "{\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"testCoupon123\",\n" +
            "        \"startDate\": \"2012-12-17\",\n" +
            "        \"endDate\": \"2022-01-15\",\n" +
            "        \"discount\": 0.25,\n" +
            "        \"used\": false,\n" +
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
        assertNotNull(webApplicationContext.getBean("couponController"));
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser(roles = {"ADMIN"})
    public void getAllCoupons_ResponseOk() {
        MvcResult mvcResult = mockMvc.perform(get("/coupons/sort")
                        .param("pageNumber", "1")
                        .param("pageSize", "5")
                        .param("sortingBy", "discount"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getCouponById_ResponseOk() {
        mockMvc.perform(get("/coupons/{id}", "1"))
                .andDo(print()).andExpect(status().isGone());
    }

    @SneakyThrows
    @Test
    @WithMockUser
    @Sql(scripts = "classpath:sql/insert_data.sql")
    public void getCouponByPurchase_ResponseOk() {
        mockMvc.perform(get("/coupons/purchase/{id}", "1"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    @WithMockUser
    public void addCoupon_ResponseCreated() {
        mockMvc.perform(post("/coupons").contentType("application/json").content(NEW_COUPON))
                .andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/insert_data.sql")
    @WithMockUser
    public void updateCoupon_ResponseAccessDenied() {
        mockMvc.perform(put("/coupons").contentType("application/json").content(UPDATING_COUPON))
                .andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser
    public void deleteCoupon_ResponseError() {
        mockMvc.perform(delete("/coupons/{id}", "1"))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteCoupon_ResponseAccepted() {
        mockMvc.perform(delete("/coupons/{id}", "1"))
                .andExpect(status().isAccepted());
    }
}