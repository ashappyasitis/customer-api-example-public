package me.kevin.customerapi.iface;

public interface Pageable {
    int getPageNumber();
    int getPageSize();
    long getOffset();
}
