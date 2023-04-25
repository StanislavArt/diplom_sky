package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.dto.FullAds;
import ru.skypro.diplom.dto.ResponseAds;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;

import java.util.ArrayList;
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
       ResponseWrapperAds responseWrapperAds = getResponseWrapperAdsDTO(adsList);
       return responseWrapperAds;
    }

    public ResponseWrapperAds getAds() {
        User user = userService.getCurrentUser();
        if (user == null) return null;
        List<Ads> adsList = adsRepository.findAllByAuthor(userService.getCurrentUser());
        return getResponseWrapperAdsDTO(adsList);
    }

    public ResponseAds addAds(CreateAds createAds, byte[] image) {
        User user = userService.getCurrentUser();
        if (user == null) return null;
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setAuthor(user);
        //ads.setImage(new String(image));
        ads.setImage("Picture");

        Ads adsDB = adsRepository.save(ads);
        if (adsDB == null) logger.error("Write error into database (function 'addAds()'");
        ResponseAds responseAds = createAdsDTO(adsDB);
        return responseAds;
    }

    public FullAds getFullAd(int id) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) return null;
        return getFullAdsDTO(ads);
    }

    public ResponseAds updateAds(int id, CreateAds createAds) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) return null;
        updateAdsDTO(ads, createAds);
        ads = adsRepository.save(ads);
        if (ads == null) logger.error("Write error into database (function 'updateAds()'");
        return createAdsDTO(ads);
    }

    public void removeAds(int id) {
        adsRepository.deleteById(id);
    }

    public List<String> updateAdsImage(int AdPk, MultipartFile file) {
        List<String> images = new ArrayList<>();
        Ads ads = adsRepository.findById(AdPk).orElse(null);
        if (ads == null) return images;

        // сохранить картинку на диске

        // записать путь в БД

        return images;
    }

    private ResponseWrapperAds getResponseWrapperAdsDTO(List<Ads> adsList) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsList.size());

        List<ResponseAds> responseAdsList = new ArrayList<>();
        for (Ads item : adsList) {
            responseAdsList.add(createAdsDTO(item));
        }
        responseWrapperAds.setResults(responseAdsList);
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
        fullAds.setImage(ads.getImage());
        return fullAds;
    }

    private ResponseAds createAdsDTO(Ads ads) {
        ResponseAds responseAds = new ResponseAds();
        if (ads == null) return responseAds;
        responseAds.setPk(ads.getPk());
        responseAds.setTitle(ads.getTitle());
        responseAds.setAuthor(ads.getAuthor().getId());
        responseAds.setPrice(ads.getPrice());
        responseAds.setImage(ads.getImage());
        return responseAds;
    }

    private void updateAdsDTO(Ads ads, CreateAds createAds) {
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
    }



}
