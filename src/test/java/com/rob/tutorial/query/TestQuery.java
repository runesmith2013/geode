package com.rob.tutorial.query;

import com.rob.tutorial.TestGeodeClient;
import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import org.apache.geode.cache.query.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test cases showing query functionality
 */
public class TestQuery extends TestGeodeClient {

    /**
     * Insert some data and perform query operations on it
     *
     * OQL allows object traversal in the query, e.q. c.firstname
     */
    @Test
    public void testQueryCustomObjectsOQL() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {
        Map<CustomerKey, Customer> data = new HashMap<>();
        CustomerKey k1 = new CustomerKey(1, "UK");
        CustomerKey k2 = new CustomerKey(2, "UK");
        data.put(k1, new Customer(k1,"Gheorge", "Manuc", 36));
        data.put(k2, new Customer(k2,"Allan", "McDowell", 43));
        customerRegion.putAll(data);

        QueryService queryService = this.cache.getQueryService();
        String query =
                "select * from /baeldung-customers c where c.firstName = 'Allan'";
        SelectResults<Customer> results = (SelectResults<Customer>) queryService.newQuery(query).execute();
        assertEquals(1, results.size());
    }

}
