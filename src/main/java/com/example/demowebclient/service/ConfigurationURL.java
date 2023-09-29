package com.example.demowebclient.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cve")
public class ConfigurationURL {
    private String cveIdHost;
    private String cveIdtPath;

    public String getCveIdHost() {
        return cveIdHost;
    }

    public void setCveIdHost(String cveIdHost) {
        this.cveIdHost = cveIdHost;
    }

    public String getCveIdtPath() {
        return cveIdtPath;
    }

    public void setCveIdtPath(String cveIdtPath) {
        this.cveIdtPath = cveIdtPath;
    }
}
