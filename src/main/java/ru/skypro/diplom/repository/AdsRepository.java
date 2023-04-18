package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.model.Ads;

public interface AdsRepository extends JpaRepository<Ads,Integer> {

}
