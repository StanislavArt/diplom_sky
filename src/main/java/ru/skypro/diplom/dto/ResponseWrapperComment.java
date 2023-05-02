package ru.skypro.diplom.dto;

import ru.skypro.diplom.model.Comment;

import java.util.List;

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
