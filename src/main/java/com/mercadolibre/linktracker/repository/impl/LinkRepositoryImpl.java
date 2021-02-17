package com.mercadolibre.linktracker.repository.impl;

import com.mercadolibre.linktracker.model.Link;
import com.mercadolibre.linktracker.repository.LinkRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LinkRepositoryImpl implements LinkRepository {

    private Map<Integer, Link> linkHashMap = new HashMap<>();
    private int count = 0;

    @Override
    public Integer createLink(Link link) {
        count++;
        link.setId(count);
        linkHashMap.put(count, link);
        return count;
    }

    @Override
    public Link getLink(int id) {
        return linkHashMap.get(id);
    }

    @Override
    public Link invalidateLink(int id) {
        Link link = linkHashMap.get(id);
        link.setValid(false);
        linkHashMap.put(id, link);
        return link;
    }
}
