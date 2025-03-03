package com.SWOOSH.repository;

import com.SWOOSH.model.Employee;
import com.SWOOSH.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Integer countOrderByEmployee(Employee employee);
}
