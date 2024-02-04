package backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.entities.Seguidos;

@Repository
public interface SeguidosRepository extends JpaRepository<Seguidos,Long>{
    
}
