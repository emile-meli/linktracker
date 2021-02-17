package com.mercadolibre.linktracker.service;

import com.mercadolibre.linktracker.dto.LinkDTO;
import com.mercadolibre.linktracker.dto.LinkResponseDTO;
import com.mercadolibre.linktracker.dto.MetricsDTO;

public interface LinkService {

    public LinkResponseDTO createLink(LinkDTO linkDTO);
    public LinkResponseDTO getLink(int id, String password);
    public MetricsDTO getLinkMetrics(int id);
    public LinkResponseDTO invalidateLink(int id);
}
