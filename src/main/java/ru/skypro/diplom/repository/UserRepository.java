package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserOptionalByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsername(String username);

    @Query(value = "select count(*) from client c where c.username = :username", nativeQuery = true)
    Long existUsername(@Param("username") String username);

    Long deleteByUsername(String username);

//    @Query(value = "select username, password, role from client", nativeQuery = true)
//    List<LoadCred> getListOfUsernameAndPasswordAndRole();
}
