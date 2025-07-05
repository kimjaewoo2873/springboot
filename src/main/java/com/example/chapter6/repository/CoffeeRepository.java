package com.example.chapter6.repository;

import com.example.chapter6.entity.Coffee;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    @Override
    ArrayList<Coffee> findAll(); // 캐스팅 대신 오버라이딩
}
