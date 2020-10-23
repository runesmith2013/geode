package com.rob.tutorial.function;


import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.Map;


/**
 * Implements a function which is executed in parallel on the server side
 *
 * can be deployed to the cluster using the gfsh deploy command to all or subset of members
 *
 * - getId used to access function through FunctionService API
 * - isHA returns true if function can be executed after a member failure
 * - hasResult returns true if result is returned
 */
public class UpperCaseNames implements Function<Boolean> {

    @Override
    /**
     * This should be thread safe to accomodate multiple identical calls
     * Use the RegionFunctionContext.isPossibleDuplicate to determine whether this is being re-run due to HA failover
     *
     * FunctionContext holds information about the execution and the data:
     * - context holds the functionID, the resultSender object for passing results to the originator and arguments from originator
     *
     * For data dependent functions, the RFC holds the Region object, the set of key filters and a boolean indicating multiple identical calls
     *
     * For partitioned regions the PartitionRegionHelper provides access to
     */
    public void execute(FunctionContext<Boolean> functionContext) {
        RegionFunctionContext regionContext = (RegionFunctionContext)functionContext;

        Region<CustomerKey, Customer> region = regionContext.getDataSet();

        for (Map.Entry<CustomerKey, Customer> entry: region.entrySet()) {
            Customer customer = entry.getValue();
            customer.setFirstName(customer.getFirstName().toUpperCase());
        }
        functionContext.getResultSender().lastResult(true);
    }

    /**
     * Must return a unique value, so class name is a good bet
     * @return
     */
    public String getId() {
        return getClass().getName();
    }

}
