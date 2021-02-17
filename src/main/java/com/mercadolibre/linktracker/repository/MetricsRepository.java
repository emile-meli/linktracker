package com.mercadolibre.linktracker.repository;

import com.mercadolibre.linktracker.model.Metrics;

public interface MetricsRepository {

    public void addRedirect(int id);
    public Metrics getRedirects(int id);
}
