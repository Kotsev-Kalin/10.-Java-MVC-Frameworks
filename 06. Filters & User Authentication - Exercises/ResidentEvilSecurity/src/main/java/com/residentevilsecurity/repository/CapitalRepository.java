package com.residentevilsecurity.repository;

import com.residentevilsecurity.domain.entity.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {
    Capital findByName(String name);

    List<Capital> findAllByOrderByName();
}
