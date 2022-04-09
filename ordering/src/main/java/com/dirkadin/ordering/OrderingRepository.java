package com.dirkadin.ordering;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderingRepository extends JpaRepository<Order, Integer> {

}
