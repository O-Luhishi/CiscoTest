package com.zonos.url_lookup_service.controller;


import com.zonos.url_lookup_service.model.Url;
import com.zonos.url_lookup_service.repository.UrlRepository;
import com.zonos.url_lookup_service.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    UrlRepository urlRepository;

    @RequestMapping(path="/urlinfo/1/{hostname_port}/**", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> malwareCheckerOld(@PathVariable("hostname_port") String hostname){
        String sanitised_hostname = UrlUtil.urlParser(hostname);
        Url result = urlRepository.findByHostname(sanitised_hostname);
        boolean malware = result != null;
        return new ResponseEntity<>("{'Domain': " + sanitised_hostname + ", 'Malware': "+ malware +"}", HttpStatus.OK);
    }

    @PostMapping(path="/add_malware_domain",produces = "application/json")
    public ResponseEntity<Url> addMalwareDomain(@RequestBody Url url){
        try {
            String sanitised_hostname = UrlUtil.urlParser(url.getHostname());
            Url _url = urlRepository.save(new Url(sanitised_hostname, url.getPort()));
            return new ResponseEntity<>(_url, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @RequestMapping(path="/get_all_known_domains", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Url>> getAllKnownDomains(){
        try {
            List<Url> urls = new ArrayList<>(urlRepository.findAll());
            if (urls.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(urls, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
