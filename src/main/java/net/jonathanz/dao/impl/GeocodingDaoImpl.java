package net.jonathanz.dao.impl;

import net.jonathanz.dao.GeocodingDao;
import net.jonathanz.entity.Geocoding;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by cezhang on 7/8/17.
 */
@Repository
public class GeocodingDaoImpl implements GeocodingDao {
    private final static String GOOGLE_API_GET_GEOCODING =
            "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyC712NdbxZvFtdkX9QRxwUSHtzY9vzj7rI";
    private RestTemplate restTemplate = new RestTemplate();


    public Geocoding getGeocodingByAddress(String address) {
        ResponseEntity<Geocoding> result = restTemplate.getForEntity(
                String.format(GOOGLE_API_GET_GEOCODING, address.replaceAll(" ", "+")),
                Geocoding.class);
        if(result.getStatusCodeValue() != 200){
            throw new RuntimeException("Response error " + result.getStatusCode());
        }
        return result.getBody();
    }

    public List<Geocoding> getGeocodingsByAddresses(List<String> addresses) {
        return addresses.stream()
                .map(s -> s.replace(" ", "+"))
                .map(s -> restTemplate.getForEntity(
                        String.format(GOOGLE_API_GET_GEOCODING, s),
                        Geocoding.class))
                .filter(r -> r.getStatusCodeValue() == 200)
                .map(r -> r.getBody())
                .collect(Collectors.toList());
    }

    public List<Geocoding> getGeocodingsByAddressesAsync(List<String> address) {
        ExecutorService pool = Executors.newFixedThreadPool(Math.min(address.size(), 100));

        return address.stream()
                .map(s -> s.replace(" ", "+"))
                .map(s -> CompletableFuture.supplyAsync(() -> restTemplate.getForEntity(
                        String.format(GOOGLE_API_GET_GEOCODING, s),
                        Geocoding.class), pool))
                .collect(Collectors.toList()).stream()
                .map(CompletableFuture::join)
                .filter(r -> r.getStatusCodeValue() == 200)
                .map(r -> r.getBody())
                .collect(Collectors.toList());
    }
}
