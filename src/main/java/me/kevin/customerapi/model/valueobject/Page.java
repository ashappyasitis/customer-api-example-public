package me.kevin.customerapi.model.valueobject;

import lombok.Getter;
import me.kevin.customerapi.iface.Pageable;

@Getter
public class Page {
    private final long totalElements;
    private final long totalPages;
    private final long pageNumber;
    private final long pageSize;

    public Page(Pageable pageable, long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int) totalElements / pageable.getPageSize();
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
    }
}
