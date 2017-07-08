package net.jonathanz.dao;

import net.jonathanz.entity.Geocoding;

import java.util.List;

/**
 * Created by cezhang on 7/8/17.
 */
public interface GeocodingDao {
    Geocoding getGeocodingByAddress(String address);

    List<Geocoding> getGeocodingsByAddresses(List<String> addresses);

    List<Geocoding> getGeocodingsByAddressesAsync(List<String> address);

}
