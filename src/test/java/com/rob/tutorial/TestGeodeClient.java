package com.rob.tutorial;

import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.query.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Based on tutorial found here: https://www.baeldung.com/apache-geode
 */
public class TestGeodeClient {

    private static ClientCache cache;
    private static Region<String, String> region;
    private static Region<CustomerKey, Customer> customerRegion;


    @BeforeClass
    public static void connect() {

        //-- connect to the cache using the locator
        cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();

        //-- connect to the region
        region = cache.<String,String>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("baeldung");

        customerRegion = cache.<CustomerKey, Customer>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("baeldung-customers");

    }


    @Test
    public void testPutAndSaveSingleValues() {
        this.region.put("A", "Hello");
        this.region.put("B","World");

        assertEquals ("Hello", region.get("A"));
        assertEquals("World", region.get("B"));
    }

    @Test
    public void testPutAndSaveMultipleValues() {

        Supplier<Stream<String>> keys = () -> Stream.of("A","B", "C", "D");
        Map<String, String> values = keys.get().collect(Collectors.toMap(Function.identity(),
                String::toLowerCase));

        this.region.putAll(values);

        keys.get().forEach(k-> assertEquals(k.toLowerCase(), this.region.get(k)));


    }

    @Test
    public void testPutAndGetCustomObject() {
        CustomerKey key = new CustomerKey(123,"UK");

        Customer customer = new Customer(key,"William","Russell", 35);
        this.customerRegion.put(key, customer);

        Customer storedCustomer = this.customerRegion.get(key);
        assertEquals("William", storedCustomer.getFirstName());
        assertEquals("Russell", storedCustomer.getLastName());
        
    }
    

    @Test
    public void testQueryCustomObjectsOQL() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {
        Map<CustomerKey, Customer> data = new HashMap<>();
        CustomerKey k1 = new CustomerKey(1, "UK");
        CustomerKey k2 = new CustomerKey(2, "UK");
        data.put(k1, new Customer(k1,"Gheorge", "Manuc", 36));
        data.put(k2, new Customer(k2,"Allan", "McDowell", 43));
        this.customerRegion.putAll(data);

        QueryService queryService = this.cache.getQueryService();
        String query =
                "select * from /baeldung-customers c where c.firstName = 'Allan'";
        SelectResults<Customer> results =
                (SelectResults<Customer>) queryService.newQuery(query).execute();
        assertEquals(1, results.size());
    }



}


