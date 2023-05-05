package ru.skypro.diplom.dto;

import ru.skypro.diplom.model.Comment;

import java.util.List;

/**
 * Объект DTO для работы с комментариями. Взаимодействует с классом-моделью {@link Comment}
 *
 * {@code count}: количество найденных комментариев
 * {@code results}: список комментариев.
 * @see CommentDTO
 */
public class ResponseWrapperComment {
    private int count;
    private List<CommentDTO> results;

    public ResponseWrapperComment() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentDTO> getResults() {
        return results;
    }

    public void setResults(List<CommentDTO> results) {
        this.results = results;
    }
}
