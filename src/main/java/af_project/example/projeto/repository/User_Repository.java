package af_project.example.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import af_project.example.projeto.entity.User;


@Repository
public interface User_Repository extends JpaRepository<User, Long> {
}
