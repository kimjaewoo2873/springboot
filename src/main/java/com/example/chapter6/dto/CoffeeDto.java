package com.example.chapter6.dto;

import com.example.chapter6.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CoffeeDto {
    private Long id;
    private String name;
    private String price;

    public Coffee toEntity(){
        return new Coffee(id, name, price);
    }
}
