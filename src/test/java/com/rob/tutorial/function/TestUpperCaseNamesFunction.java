package com.rob.tutorial.function;

import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import com.rob.tutorial.function.UpperCaseNames;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUpperCaseNamesFunction {

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
    public void executeUpperCaseNames() {
        //-- invoke execution
        Execution execution = FunctionService.onRegion(this.customerRegion);
        execution.execute(UpperCaseNames.class.getName());

        //-- lookup customer to verify that name has been uppercased
        Customer customer = this.customerRegion.get(new CustomerKey(1, "UK"));


    }

}
