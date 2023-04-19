package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;

import java.util.List;

@Service
@SessionScope
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;

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

    private ResponseWrapperAds getResponseWrapperAdsDTO(List<Ads> adsList) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds(adsList);
        return responseWrapperAds;
    }

}
