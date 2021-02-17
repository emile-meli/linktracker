package com.mercadolibre.linktracker.service.impl;

import com.mercadolibre.linktracker.model.Link;
import com.mercadolibre.linktracker.model.Metrics;
import com.mercadolibre.linktracker.dto.LinkDTO;
import com.mercadolibre.linktracker.dto.LinkResponseDTO;
import com.mercadolibre.linktracker.dto.MetricsDTO;
import com.mercadolibre.linktracker.exception.InvalidPassword;
import com.mercadolibre.linktracker.exception.LinkNotFound;
import com.mercadolibre.linktracker.repository.LinkRepository;
import com.mercadolibre.linktracker.repository.MetricsRepository;
import com.mercadolibre.linktracker.service.LinkService;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final MetricsRepository metricsRepository;

    public LinkServiceImpl(LinkRepository linkRepository, MetricsRepository metricsRepository) {
        this.linkRepository = linkRepository;
        this.metricsRepository = metricsRepository;
    }

    @Override
    public LinkResponseDTO createLink(LinkDTO linkDTO) {
        Link link = new Link();
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        Integer newLinkId = 0;
        link.setUrl(linkDTO.getUrl());
        link.setPassword(linkDTO.getPassword());
        newLinkId = linkRepository.createLink(link);
        metricsRepository.addRedirect(newLinkId);
        linkResponseDTO.setId(newLinkId);
        linkResponseDTO.setUrl(link.getUrl());
        linkResponseDTO.setValid(link.isValid());
        return linkResponseDTO;
    }

    @Override
    public LinkResponseDTO getLink(int id, String password) {
        Link link = linkRepository.getLink(id);
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        if(link == null) throw new LinkNotFound(String.valueOf(id));
        if(password.equals(link.getPassword())){
            linkResponseDTO.setId(link.getId());
            linkResponseDTO.setUrl(link.getUrl());
            linkResponseDTO.setValid(link.isValid());
            metricsRepository.addRedirect(id);
        }else throw new InvalidPassword();
        return linkResponseDTO;
    }

    @Override
    public MetricsDTO getLinkMetrics(int id) {
        Metrics metrics = metricsRepository.getRedirects(id);
        MetricsDTO metricsDTO = new MetricsDTO();
        metricsDTO.setRedirects(metrics.getRedirects());
        return metricsDTO;
    }

    @Override
    public LinkResponseDTO invalidateLink(int id) {
        Link link = linkRepository.invalidateLink(id);
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        linkResponseDTO.setId(link.getId());
        linkResponseDTO.setUrl(link.getUrl());
        linkResponseDTO.setValid(link.isValid());
        return linkResponseDTO;
    }
}
