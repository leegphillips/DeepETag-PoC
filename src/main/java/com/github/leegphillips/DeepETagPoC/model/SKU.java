package com.github.leegphillips.DeepETagPoC.model;

import java.util.Date;

public class SKU {

    private Date created;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SKU sku = (SKU) o;

        if (id != sku.id) return false;
        return created != null ? created.equals(sku.created) : sku.created == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
