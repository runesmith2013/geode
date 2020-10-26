package com.rob.tutorial.Cap;

/**
 * Geode is CP if network partition detection turned on
 * Can turn off network partition detection to get AP
 *  - can lead to split brain
 *  - same key modified on different
 *
 * Choose two from:
 *
 * Consistency
 * Availability
 * Network Partition tolerance
 *
 *
 * Membership coordinator - first member to start up
 * - when a new member comes in, gets added to membership view (list of members)
 * - coordinator broadcasts changes to all members
 * - single node
 *
 * members get a weight
 * - server - 10
 * - member - 3
 * -
 *
 * Failure detection TCPRING
 * - pings neighbours on left
 * - if neighbour is down, suspect notification sent to coordinator
 * - coordinator tries to connect to suspect
 * - updates memberview, sends out to new view
 *
 * if more than one member crashes
 * - coordinator measures weight of system. if more than 50% lost, network partition event
 * - losing side gets shut off - gets and puts will fail
 *
 * if coordinator dies, new coordinator elected
 *
 * client failover
 * - clients will fail to connect, try new member until connect to winning network partition
 *
 *
 *
 */
public class TestCapTheorem {
}
