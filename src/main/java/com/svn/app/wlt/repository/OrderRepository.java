package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
