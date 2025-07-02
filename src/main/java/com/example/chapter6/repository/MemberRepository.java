package com.example.chapter6.repository;

import com.example.chapter6.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
