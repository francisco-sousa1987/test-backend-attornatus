package br.com.attornatus.testbackend.repository;

import br.com.attornatus.testbackend.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Pessoa, Long> {
}
