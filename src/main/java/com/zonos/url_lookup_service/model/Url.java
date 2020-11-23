package com.zonos.url_lookup_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;

    @Indexed(unique = true)
    private String hostname;
    private String port;

    public Url(){

    }

    public Url(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Url [id=" + id + ", hostname=" + hostname + ", port=" + port + "]";
    }

}
