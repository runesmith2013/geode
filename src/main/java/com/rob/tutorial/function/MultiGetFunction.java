package com.rob.tutorial.function;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Multi get function which returns all the values matching the keys
 */
public class MultiGetFunction implements Function {

    @Override
    public void execute(FunctionContext functionContext) {

        if (! (functionContext instanceof RegionFunctionContext)) {
            throw new FunctionException("THis is a data aware function and needs to be called using FunctionService.onRegion");

        }

        RegionFunctionContext context = (RegionFunctionContext)functionContext;
        Set keys = context.getFilter();
        Set keysTillSecondLast = new HashSet();
        int setSize = keys.size();

        Iterator keysIterator = keys.iterator();
        for (int i = 0; i < setSize-1; i++) {
            keysTillSecondLast.add(keysIterator.next());
        }

        for (Object k: keysTillSecondLast) {
            context.getResultSender().sendResult(PartitionRegionHelper.getLocalDataForContext(context).get(k));
        }

        Object lastResult = keysIterator.next();
        context.getResultSender().lastResult(PartitionRegionHelper.getLocalDataForContext(context).get(lastResult));

    }

    public String getId() {
        return getClass().getName();
    }

}
