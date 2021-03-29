package com.github.vasyapogoriliy.storage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-products-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-products-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createFirstCustomers() throws Exception {
        mockMvc.perform(post("/customers/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Ivan\",\"lastName\": \"Ivanov\",\"email\": \"ivanov@gmail.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSecondCustomers() throws Exception {
        mockMvc.perform(post("/customers/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"some\",\"lastName\": \"some\",\"email\": \"some@gmail.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createOrderForFirstCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/new", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createOrderForSecondCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/new", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createFirstOrderItemForFirstCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/{orderId}/products/new", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": \"{\"name\": \"apple\", \"weight\": \"50\", \"price\": \"10\"}\",\"amount\": \"20\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSecondOrderItemForFirstCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/{orderId}/products/new", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": \"{\"name\": \"pear\", \"weight\": \"60\", \"price\": \"10\"}\",\"amount\": \"40\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createFirstOrderItemForSecondCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/{orderId}/products/new", 2, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": \"{\"name\": \"cucumber\", \"weight\": \"40\", \"price\": \"15\"}\",\"amount\": \"30\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSecondOrderItemForSecondCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/orders/{orderId}/products/new", 2, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": \"{\"name\": \"tomato\", \"weight\": \"50\", \"price\": \"20\"}\",\"amount\": \"30\"}"))
                .andExpect(status().isCreated());
    }



}
