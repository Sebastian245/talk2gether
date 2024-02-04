package backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.entities.Seguidores;

@Repository
public interface SeguidoresRepository extends JpaRepository<Seguidores,Long>{
    
}

