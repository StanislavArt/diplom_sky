package ru.skypro.diplom.dto;

import ru.skypro.diplom.model.Comment;

import java.util.List;

public class ResponseWrapperComment {
    private int count;
    private List<Comment> results;

    public ResponseWrapperComment() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Comment> getResults() {
        return results;
    }

    public void setResults(List<Comment> results) {
        this.results = results;
    }
}
