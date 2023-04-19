package ru.skypro.diplom.dto;

import ru.skypro.diplom.model.Ads;
import java.util.List;

public class ResponseWrapperAds {
    private int count;
    private List<Ads> results;

    public ResponseWrapperAds(List<Ads> results) {
        this.count = results.size();
        this.results = results;
    }
}
