package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.dto.FullAds;
import ru.skypro.diplom.dto.ResponseAds;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    @Value("${diplom.storage}")
    private String storagePath;

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
        if (user == null) { return null; }
        List<Ads> adsList = adsRepository.findAllByAuthor(userService.getCurrentUser());
        return getResponseWrapperAdsDTO(adsList);
    }

    public ResponseAds addAds(CreateAds createAds, MultipartFile file) {
        User user = userService.getCurrentUser();
        if (user == null) { return null; }
        if (!writeFile(file)) { return null; }
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setAuthor(user);
        ads.setImage(file.getOriginalFilename());

        Ads adsDB = adsRepository.save(ads);
        return createAdsDTO(adsDB);
    }

    public FullAds getFullAd(int id) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) { return null; }
        return getFullAdsDTO(ads);
    }

    public ResponseAds updateAds(int id, CreateAds createAds) {
        Ads ads = adsRepository.findById(id).orElse(null);
        if (ads == null) { return null; }
        updateAdsDTO(ads, createAds);
        ads = adsRepository.save(ads);
        return createAdsDTO(ads);
    }

    public void removeAds(int id) {
        adsRepository.deleteById(id);
    }

    public List<String> updateAdsImage(int AdPk, MultipartFile file) {
        List<String> images = new ArrayList<>();
        Ads ads = adsRepository.findById(AdPk).orElse(null);
        if (ads == null) { return images; }
        if (!writeFile(file)) { return images; }
        ads.setImage(file.getOriginalFilename());
        adsRepository.save(ads);
        try {
            images.add(new String(file.getBytes()));
        } catch (IOException e) {
            logger.error("Error InputStream in function 'updateAdsImage()'");
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return images;
    }

    private boolean writeFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            File pathFile = new File(storagePath + fileName);
            file.transferTo(pathFile);
            return true;
        } catch (IOException e) {
            logger.error("Error writing file in function 'updateAdsImage()'");
            logger.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
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
        fullAds.setImage(transferFileToString(ads.getImage()));
        return fullAds;
    }

    private ResponseAds createAdsDTO(Ads ads) {
        ResponseAds responseAds = new ResponseAds();
        if (ads == null) { return responseAds; }
        responseAds.setPk(ads.getPk());
        responseAds.setTitle(ads.getTitle());
        responseAds.setAuthor(ads.getAuthor().getId());
        responseAds.setPrice(ads.getPrice());
        responseAds.setImage(transferFileToString(ads.getImage()));
        return responseAds;
    }

    private void updateAdsDTO(Ads ads, CreateAds createAds) {
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
    }

    private String transferFileToString(String fileName) {
        if (fileName.isEmpty()) { return ""; }
        try {
            byte[] array = Files.readAllBytes(Paths.get(storagePath + fileName));
            return new String(array);
        } catch (IOException e) {
            logger.error("Error reading file in function 'transferFileToString()'");
            logger.error(Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
