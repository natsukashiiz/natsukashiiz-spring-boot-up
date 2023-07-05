package com.natsukashiiz.iicommon.model;

public class Pagination {
    private Integer page = 1;
    private Integer limit = 10;
    private Integer offset = 10;

    public Integer getPage() {
        return page;
    }

    public Pagination setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public Pagination setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        offset = (page - 1) * limit;
        return offset;
    }
}
