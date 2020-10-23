package com.rob.tutorial.function;

import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import com.rob.tutorial.function.MultiGetFunction;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.distributed.DistributedMember;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestMultiGetFunction {

    private static ClientCache cache;
    private static Region<String, String> exampleRegion;


    @BeforeClass
    public static void connect() {

        //-- connect to the cache using the locator
        cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();

        //-- connect to the region
        exampleRegion = cache.<String,String>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("baeldung");

    }

    @Test
    /*
    Gemfire's default ResultCollector collects all results into an arrayList
    The getResult method blocks until all results are received, then returns the full set
     */
    public void testMultiGetFunction() {

        MultiGetFunction function = new MultiGetFunction();

        //-- programmatically register function, instead of automatic through JAR file deploy
        FunctionService.registerFunction(function);

        Set keysForGet = new HashSet();
        keysForGet.add("KEY_4");
        keysForGet.add("KEY_5");

        Execution execution = FunctionService.onRegion(exampleRegion)
                .withFilter(keysForGet)
                .setArguments(Boolean.TRUE)
                .withCollector(new MyArrayListResultsCollector());

        ResultCollector rc = execution.execute(function);

        //-- retrieve results, if any
        List result = (List) rc.getResult();

    }

    class MyArrayListResultsCollector extends ResultCollector {

        @Override
        /**
         * Called when results arrive from the Function instance sendREsults method
         */
        public void addResult(DistributedMember distributedMember, Object o) {

        }

        @Override
        /**
         * Called when final result received
         */
        public void endResults() {

        }

        @Override
        /**
         * Remove any partial results data if called in HA mode
         */
        public void clearResults() {

        }

        @Override
        /**
         * returns results to caller
         */
        public Object getResult() throws FunctionException {
            return null;
        }

        @Override
        public Object getResult(long l, TimeUnit timeUnit) throws FunctionException, InterruptedException {
            return null;
        }
    }

}
