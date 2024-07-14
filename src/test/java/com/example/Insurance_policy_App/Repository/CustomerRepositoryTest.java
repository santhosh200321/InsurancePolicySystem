package com.example.Insurance_policy_App.Repository;

import com.example.Insurance_policy_App.Entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

    private Customer Customer1;
    private Customer Customer2;

    @BeforeEach
    void init()
    {
        Customer1 = Customer.builder()
                .name("Customer1")
                .email("Customer123@gamil.com")
                .password("1234567")
                .username("CustomerXxx56")
                .build();

        Customer2 = Customer.builder()
                .name("Customer2")
                .id(1L)
                .email("Customer567@gamil.com")
                .password("7890123")
                .username("CustomerXxx96")
                .build();

    }
    @Test
    public void saveTest()
    {
        Customer test = repository.save(Customer1);
        assertNotNull(test);
        assertThat(test.getId()).isNotEqualTo(null);
    }
    @Test
    public void getListOfCustomers()
    {
        Customer test1 = repository.save(Customer1);
        Customer test2 = repository.save(Customer2);
        List<Customer> list = repository.findAll();
        assertThat(list).isNotNull();
        assertEquals(2,list.size());
    }
    @Test
    public void deleteTest() {
        Customer deletedCustomer = repository.save(Customer1);
        repository.delete(deletedCustomer);
        Optional<Customer> optional = repository.findById(deletedCustomer.getId());
        assertThat(optional).isEmpty();
    }

    @Test
    public void updateTest()
    {
        repository.save(Customer1);
        Customer test = repository.findById(Customer1.getId()).get();
        test.setName("s.santhosh");
        repository.save(test);
        assertEquals("s.santhosh",test.getName());
    }
    @Test
    public void findById()
    {
        repository.save(Customer1);
        Customer test = repository.findById(Customer1.getId()).get();
        assertEquals("Customer1",test.getName());
    }

}