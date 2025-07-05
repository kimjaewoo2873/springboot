package com.example.chapter6.controller;

import com.example.chapter6.dto.CoffeeDto;
import com.example.chapter6.entity.Coffee;
import com.example.chapter6.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;
    // GET 조회
    @GetMapping("/api/coffees")
    public List<Coffee> index(){
        return coffeeRepository.findAll();
    }
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }
    // POST 생성
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto coffeeDto) { // JSON 데이터로된 DTO 받기
        Coffee coffee = coffeeDto.toEntity();
        return coffeeRepository.save(coffee);
    }
    // PATCH
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity(); // 새 데이터
        Coffee target = coffeeRepository.findById(id).orElse(null); // 기존 데이터
        if(target == null || id != coffee.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    // DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
