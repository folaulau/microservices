package com.folaukaveinga.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.folaukaveinga.model.Order;
import com.folaukaveinga.service.OrderService;




@RestController
public class OrderController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable long id){
		log.info("get order by id: ", id);
		return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
	}
	
}
