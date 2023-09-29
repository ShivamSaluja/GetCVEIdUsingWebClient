package com.example.demowebclient.utils.json;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "json-path")
public class JSONPath {
    private String vulnerabilities;
    private String cveId;
    private String descriptions;
    private String values;
    private String cvssMetricsV31;
    private String cvssMetricsV2;
    private String cvssVectorString;
    private String timestamp;

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getCvssMetricsV31() {
        return cvssMetricsV31;
    }

    public void setCvssMetricsV31(String cvssMetricsV31) {
        this.cvssMetricsV31 = cvssMetricsV31;
    }

    public String getCvssMetricsV2() {
        return cvssMetricsV2;
    }

    public void setCvssMetricsV2(String cvssMetricsV2) {
        this.cvssMetricsV2 = cvssMetricsV2;
    }

    public String getCvssVectorString() {
        return cvssVectorString;
    }

    public void setCvssVectorString(String cvssVectorString) {
        this.cvssVectorString = cvssVectorString;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
