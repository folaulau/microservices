package com.folaukaveinga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folaukaveinga.model.Order;
import com.folaukaveinga.repository.OrderRepository;
@Service
public class OrderService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderRepository orderRepository;
	
	public Order save(Order order) {
		return this.orderRepository.save(order);
	}
	
	public Order getById(long id) {
		Order order = this.orderRepository.findById(id).get();
		log.info(order.toJson());
		return order;
	}
}
