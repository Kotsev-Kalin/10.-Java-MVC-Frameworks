package com.residentevilsecurity.repository;

import com.residentevilsecurity.domain.entity.Virus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirusRepository extends JpaRepository<Virus, String> {
    Virus findByName(String name);
}
