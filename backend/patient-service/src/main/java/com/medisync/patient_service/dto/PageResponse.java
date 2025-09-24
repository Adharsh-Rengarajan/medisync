package com.medisync.patient_service.dto;

import java.util.List;

public class PageResponse<T> {

    private List<T> items;
    private long totalItems;
    private int page;
    private int size;
    private int totalPages;

    public PageResponse() { }

    public PageResponse(List<T> items, long totalItems, int page, int size, int totalPages) {
        this.items = items;
        this.totalItems = totalItems;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
