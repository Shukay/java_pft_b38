package ru.stqa.pft.soap;

import org.testng.annotations.Test;
import com.lavasoft.GeoIPService;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("194.15.117.78");
        assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>47</State></GeoIP>");
    }
}
