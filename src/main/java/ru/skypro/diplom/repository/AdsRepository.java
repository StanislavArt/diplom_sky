package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads,Integer> {
    List<Ads> findAllByAuthor(User author);

    @Override
    Optional<Ads> findById(Integer integer);
}
