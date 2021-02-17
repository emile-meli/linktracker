package com.mercadolibre.linktracker.repository;

import com.mercadolibre.linktracker.model.Link;

public interface LinkRepository {

    public Integer createLink(Link link);
    public Link getLink(int id);
    public Link invalidateLink(int id);
}
