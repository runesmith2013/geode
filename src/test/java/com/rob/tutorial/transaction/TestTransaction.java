package com.rob.tutorial.transaction;

import com.rob.tutorial.TestGeodeClient;
import com.rob.tutorial.data.Customer;
import com.rob.tutorial.data.CustomerKey;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.TransactionId;
import org.junit.Test;

/**
 * Test cases showing transaction functionality
 *
 *
 * Semantics
 * - Repeatable Read
 *  -- thread sees own changes
 *  -- other threads do not until commit is called
 *
 *  - optimistic
 *  -- no entry level locks, readers not blocked
 *  -- conflictdetection if data changed, exception thrown
 *
 *  -not persistent (2017)
 *
 *  - data must be colocated
 *  -- e.g. within one partitonedregin
 *  -- e.g. across two or more partitions on same jvm
 *  -- implement a partitionresolver for data colocation
 *
 */
public class TestTransaction  extends TestGeodeClient {

    @Test
    public void testTransaction() {

        CacheTransactionManager mgr = cache.getCacheTransactionManager();
        mgr.begin();
        customerRegion.put(new CustomerKey(1, "UK"), new Customer("Rob", "Barr", 42);
        customerRegion.put(new CustomerKey(2, "UK"), new Customer("Rob2", "Barr2", 242);
        region.put("Hello","World");
        mgr.commit();

    }

    @Test
    public void testSuspendAndResumeTransaction() {
        CacheTransactionManager mgr = cache.getCacheTransactionManager();
        mgr.begin();
        customerRegion.put(new CustomerKey(1, "UK"), new Customer("Rob", "Barr", 42);

        TransactionId trxId = mgr.suspend();
        //-- other non-transactional work

        mgr.resume(trxId);
        mgr.tryResume(trxId);


    }


    @Test
    /**
     * These operations are atomic when working on single entries
     */
    public void testSingleAtomicOperations() {

        //-- atomic
        region.putIfAbsent("Test", "Value");

        region.replace("Test", "value2");
        region.remove("Test");
    }



}
