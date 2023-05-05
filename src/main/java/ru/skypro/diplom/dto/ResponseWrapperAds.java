package ru.skypro.diplom.dto;

import ru.skypro.diplom.model.Ads;
import java.util.List;

/**
 * Объект DTO для работы с объявлениями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.Ads}
 *
 * {@code count}: количество найденных объявлений
 * {@code results}: список объявлений.
 * @see ResponseAds
 */
public class ResponseWrapperAds {
    private int count;
    private List<ResponseAds> results;

    public ResponseWrapperAds() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResponseAds> getResults() {
        return results;
    }

    public void setResults(List<ResponseAds> results) {
        this.results = results;
    }
}
