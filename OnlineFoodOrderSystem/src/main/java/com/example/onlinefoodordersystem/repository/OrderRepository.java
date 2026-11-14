package com.example.onlinefoodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.onlinefoodordersystem.model.FoodOrder;


@Repository
public interface OrderRepository extends JpaRepository<FoodOrder, Long> {

}