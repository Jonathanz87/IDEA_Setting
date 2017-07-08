import net.jonathanz.config.SpringConfig;
import net.jonathanz.dao.GeocodingDao;
import net.jonathanz.entity.Geocoding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by cezhang on 7/8/17.
 */
public class Tester {
    public static void main(String[] args) {
        testMethod();
    }

    public static void testMethod() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        GeocodingDao geocodingDao = applicationContext.getBean(GeocodingDao.class);

        showTime(geocodingDao::getGeocodingsByAddresses);
        showTime(geocodingDao::getGeocodingsByAddressesAsync);
    }

    public static void showTime(Function<List<String>, List<Geocoding>> c){
        List<String> addresses = Arrays.asList("1600 Amphitheatre Parkway, Mountain View, CA",
                "Wawa, 3450 Historic Sully Way, Chantilly, VA 20151",
                "114 Oakgrove Rd, Sterling, VA 20166",
                "21398 Price Cascades Plaza, Sterling, VA 20164",
                "45425 Dulles Crossing Plaza, Sterling, VA 20166",
                "Atlantic Blvd & Nokes Blvd, Sterling, VA 20166",
                "45575 Dulles Eastern Plaza Suite 171, Sterling, VA 20166",
                "5690 Elmwood Ct # 170, Sterling, VA 20166",
                "45540 Dulles Eastern Plaza, Sterling, VA 20166",
                "45430 Dulles Crossing Plaza, Sterling, VA 20166",
                "21060 Dulles Town Cir, Sterling, VA 20166",
                "21100 Dulles Town Cir, Dulles, VA 20166",
                "21050 Dulles Town Center, Sterling, VA 20166",
                "21300 Redskin Park Dr, Ashburn, VA 20147",
                "44652 Guilford Dr #114, Ashburn, VA 20147",
                "44260 Ice Rink Plaza, Ashburn, VA 20147",
                "44030 Pipeline Plaza, Ashburn, VA 20147",
                "21670 Red Rum Dr #102, Ashburn, VA 20147");
        long startTime = System.currentTimeMillis();
        c.apply(addresses);//.forEach(System.out::println);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
}
