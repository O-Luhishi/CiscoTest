package com.zonos.url_lookup_service.repository;

import com.zonos.url_lookup_service.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String>{
    Url findByHostname(String hostname);
}