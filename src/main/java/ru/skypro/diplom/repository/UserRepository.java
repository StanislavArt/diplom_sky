package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
