package com.example.springboot.prices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot.prices.model.Price;

public interface PriceRepository extends JpaRepository<Price, Integer>{

}
