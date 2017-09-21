package com.github.leegphillips.DeepETagPoC.model;

import java.util.Date;

public class SKU {
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private Date created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SKU sku = (SKU) o;

        return created != null ? created.equals(sku.created) : sku.created == null;

    }

    @Override
    public int hashCode() {
        return created != null ? created.hashCode() : 0;
    }
}
