package com.mercadolibre.linktracker.repository;

import com.mercadolibre.linktracker.dao.Metrics;

public interface MetricsRepository {

    public void addRedirect(int id);
    public Metrics getRedirects(int id);
}
