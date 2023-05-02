package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.dto.FullAds;
import ru.skypro.diplom.dto.ResponseAds;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;
import ru.skypro.diplom.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    public AdsService(AdsRepository adsRepository, CommentRepository commentRepository, UserService userService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public ResponseWrapperAds getAllAds() {
       List<Ads> adsList = adsRepository.findAll();
        return getResponseWrapperAdsDTO(adsList);
    }

    public ResponseWrapperAds getAds(Authentication auth) {
        User user = userService.getUserFromAuthentication(auth);
        if (user == null) { return null; }
        List<Ads> adsList = adsRepository.findAllByAuthor(user);
        return getResponseWrapperAdsDTO(adsList);
    }

    @PreAuthorize("authentication.principal.username != 'user@gmail.com'")
    public ResponseAds addAds(CreateAds createAds, MultipartFile file, Authentication auth) {
        User user = userService.getUserFromAuthentication(auth);
        if (user == null) { return null; }

        String fileName = userService.prepareFileName(file);
        if (fileName == null) { return null; }

        if (!userService.writeFile(file, fileName)) { return null; }
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setAuthor(user);
        ads.setImage(fileName);
        Ads adsDB = adsRepository.save(ads);
        return createAdsDTO(adsDB);
    }

    @PreAuthorize("authentication.principal.username != 'user@gmail.com'")
    public FullAds getFullAd(int id) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) { return null; }
        return getFullAdsDTO(ads);
    }

    @PreAuthorize("authentication.principal.username != 'user@gmail.com'")
    public ResponseAds updateAds(int id, CreateAds createAds, Authentication auth) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) { return null; }
        if (userService.operationForbidden(auth, ads.getAuthor().getUsername())) { return null; }
        updateAdsDTO(ads, createAds);
        ads = adsRepository.save(ads);
        return createAdsDTO(ads);
    }

    @PreAuthorize("authentication.principal.username != 'user@gmail.com'")
    @Transactional
    public void removeAds(int id, Authentication auth) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) { return; }
        if (userService.operationForbidden(auth, ads.getAuthor().getUsername())) { return; }
        if (!userService.deleteImageFile(ads.getImage())) { return; }
        commentRepository.deleteAllCommentsByAds(id);
        adsRepository.deleteAds(id);
    }

    @PreAuthorize("authentication.principal.username != 'user@gmail.com'")
    public List<String> updateAdsImage(int adPk, MultipartFile file) {
        List<String> images = new ArrayList<>();
        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) { return images; }

        String fileName = userService.prepareFileName(file);
        if (fileName == null) { return images; }

        if (!userService.deleteImageFile(ads.getImage())) {
            return images;
        }
        if (!userService.writeFile(file, fileName)) { return images; }
        ads.setImage(fileName);
        adsRepository.save(ads);
        images.add("/ads/" + ads.getPk() + "/image");
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
        fullAds.setImage("/ads/" + ads.getPk() + "/image");
        return fullAds;
    }

    private ResponseAds createAdsDTO(Ads ads) {
        ResponseAds responseAds = new ResponseAds();
        if (ads == null) { return responseAds; }
        responseAds.setPk(ads.getPk());
        responseAds.setTitle(ads.getTitle());
        responseAds.setAuthor(ads.getAuthor().getId());
        responseAds.setPrice(ads.getPrice());
        responseAds.setImage("/ads/" + ads.getPk() + "/image");
        return responseAds;
    }

    private void updateAdsDTO(Ads ads, CreateAds createAds) {
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
    }

    public byte[] getAdsImage(int adPk) {
        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) { return new byte[0]; }
        String fileName = ads.getImage();
        if (fileName == null || fileName.isEmpty()) { return new byte[0]; }
        byte[] data = userService.transferFileToByteArray(fileName);
        return data;
    }

}
