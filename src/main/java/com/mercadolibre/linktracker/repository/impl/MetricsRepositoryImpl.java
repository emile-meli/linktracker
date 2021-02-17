package com.mercadolibre.linktracker.repository.impl;

import com.mercadolibre.linktracker.model.Metrics;
import com.mercadolibre.linktracker.repository.MetricsRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MetricsRepositoryImpl implements MetricsRepository {

    private final Map<Integer, Metrics> metricsMap = new HashMap<>();

    @Override
    public void addRedirect(int id) {
        Metrics metrics;
        if(metricsMap.containsKey(id)){
            metrics = metricsMap.get(id);
            metrics.setRedirects(metrics.getRedirects()+1);
        }else{
            metrics = new Metrics();
            metrics.setId(id);
            metrics.setRedirects(0);
        }
        metricsMap.put(id,metrics);
    }

    @Override
    public Metrics getRedirects(int id) {
        return metricsMap.get(id);
    }
}
