package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Order;
import com.svn.app.wlt.entity.Trade;
import com.svn.app.wlt.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(Long id){
        return orderRepository.findById(id).get();
    }

    public Order create(Order order){
        order.setId(null);
        return orderRepository.save(order);
    }

    public Order update(Order order){
        return orderRepository.save(order);
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public List<Order> saveAll(List<Order> orderList){
        return orderRepository.saveAll(orderList);
    }
}
