package softuni.residentevil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.residentevil.domain.entity.Capital;

import java.util.List;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {
    Capital findByName(String name);

    List<Capital> findAllByOrderByName();
}
