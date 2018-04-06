package com.folaukaveinga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.folaukaveinga.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
