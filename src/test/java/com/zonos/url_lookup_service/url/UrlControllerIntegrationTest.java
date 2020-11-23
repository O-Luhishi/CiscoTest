package com.zonos.url_lookup_service.url;

import com.zonos.url_lookup_service.CiscoTestApplicationTests;
import com.zonos.url_lookup_service.model.Url;
import com.zonos.url_lookup_service.repository.UrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class UrlControllerIntegrationTest extends CiscoTestApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mock;

    @Autowired
    UrlRepository urlRepository;

    @Before
    public void setUP() {
        mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private void insertDomains(){
        urlRepository.save(new Url("malwaredomain.co.uk", "80"));
        urlRepository.save(new Url("malware.com", "80"));
        urlRepository.save(new Url("notmalware.io", "80"));
        urlRepository.save(new Url("ransomeware.eu", "80"));
    }

    private void removeAllDomains(){
        urlRepository.deleteAll();
    }

    @Test
    public void testSafeURLReturnsFalse() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/api/urlinfo/1/www.google.co.uk:443/https://www.google.com:443/flower.jpeg")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{'Domain': google.co.uk, 'Malware': false}"));
    }

    @Test
    public void testMalwareURLReturnsTrue() throws Exception {
        removeAllDomains();
        insertDomains();
        mock.perform(MockMvcRequestBuilders.get("/api/urlinfo/1/www.malwaredomain.co.uk:80/https://www.malwaredomain.co.uk:80?=Download/malware.gif")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{'Domain': malwaredomain.co.uk, 'Malware': true}"));
    }

    @Test
    public void testAddingMalwareDomainToDB() throws Exception {
        removeAllDomains();
        mock.perform(MockMvcRequestBuilders.post("/api/add_malware_domain")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hostname\": \"www.trl.com/malware?q=gif\", \"port\": \"443\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllKnownMalwareDomains() throws Exception {
        removeAllDomains();
        insertDomains();
        mock.perform(MockMvcRequestBuilders.get("/api/get_all_known_domains")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllKnownMalwareDomainsWithNoData() throws Exception{
        removeAllDomains();
        mock.perform(MockMvcRequestBuilders.get("/api/get_all_known_domains")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
