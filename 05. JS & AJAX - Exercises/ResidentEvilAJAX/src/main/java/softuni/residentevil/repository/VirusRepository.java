package softuni.residentevil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.residentevil.domain.entity.Virus;

@Repository
public interface VirusRepository extends JpaRepository<Virus, String> {
    Virus findByName(String name);
}
