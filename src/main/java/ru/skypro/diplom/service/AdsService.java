package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.dto.FullAds;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.Image;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@SessionScope
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    public AdsService(AdsRepository adsRepository, UserService userService) {
        this.adsRepository = adsRepository;
        this.userService = userService;
    }

    public ResponseWrapperAds getAllAds() {
       List<Ads> adsList = adsRepository.findAll();
        return getResponseWrapperAdsDTO(adsList);
    }

    public ResponseWrapperAds getAds() {
        User user = userService.getCurrentUser();
        if (user == null) return null;
        List<Ads> adsList = adsRepository.findAllByAuthor(userService.getCurrentUser());
        return getResponseWrapperAdsDTO(adsList);
    }

    public Ads addAds(CreateAds createAds, byte[] image) {
        User user = userService.getCurrentUser();
        if (user == null) return null;
        Ads ads = createAdsDTO(createAds);

        Set<Image> images = new HashSet<>();
        images.add(new Image(new String(image)));
        ads.setImages(images);

        Ads adsDB = adsRepository.save(ads);
        if (adsDB == null) logger.error("Write error into database (function 'addAds()'");
        return adsDB;
    }

    public FullAds getFullAd(int id) {
        // проверка на авторизацию
        // ...

        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) return null;
        return getFullAdsDTO(ads);
    }

    public Ads updateAds(int id, CreateAds createAds) {
        // проверка на авторизацию
        // ...

        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) return null;
        updateAdsDTO(ads, createAds);
        ads = adsRepository.save(ads);
        if (ads == null) logger.error("Write error into database (function 'updateAds()'");
        return ads;
    }

    public void removeAds(int id) {
        // проверка на авторизацию
        // ...

        adsRepository.deleteById(id);
    }

    private ResponseWrapperAds getResponseWrapperAdsDTO(List<Ads> adsList) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds(adsList);
        return responseWrapperAds;
    }

    private FullAds getFullAdsDTO(Ads ads) {
        FullAds fullAds = new FullAds();
        fullAds.setPk(ads.getPk());
        fullAds.setTitle(ads.getTitle());
        fullAds.setDescription(ads.getDescription());
        fullAds.setPrice(ads.getPrice());
        fullAds.setAuthorFirstName(ads.getAuthor().getFirstName());
        fullAds.setAuthorLastName(ads.getAuthor().getLastName());
        fullAds.setEmail(ads.getAuthor().getEmail());
        fullAds.setPhone(ads.getAuthor().getPhone());
        fullAds.setImages((String[])ads.getImages().toArray());
        return fullAds;
    }

    private Ads createAdsDTO(CreateAds createAds) {
        Ads ads = new Ads(createAds.getTitle(), createAds.getDescription(), createAds.getPrice());
        ads.setAuthor(userService.getCurrentUser());
        return ads;
    }

    private void updateAdsDTO(Ads ads, CreateAds createAds) {
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
    }



}
