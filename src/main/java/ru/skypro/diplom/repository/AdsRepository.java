package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.Ads;

@Repository
public interface AdsRepository extends JpaRepository<Ads,Integer> {

}
