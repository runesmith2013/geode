package com.rob.tutorial.OffHeap;

/**
 * Own memory manager using sun.misc.unsafe
 * Memory allocated in 2GB slabs
 *
 * No GC tuning
 * - no stop the world pauses
 *
 * Scale members vertically
 * improved throughput
 *
 * Allocate off heap
 * gfsh> start server --name=server1 --off-heap-memory-size=15g --lock-memory (eagerly provision upfront)
 *
 * use off heap memory
 * gfsh> create region --name=region1 --type=PARTITION_REDUNDANT --off-heap
 *
 *
 * Keys and Indexes still on heap
 * Values stored off heap
 * scales into the terabytes
 */
public class TestOffHeap {
}
