package com.tp.loadbalancer.dispatching;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class BucketCalculator {

    private final int numberOfBuckets;

    public BucketCalculator(final int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
    }

    public int calculate(final String id) {
        return Hashing.consistentHash(Hashing.murmur3_32().hashString(id, Charsets.UTF_8).hashCode(), numberOfBuckets);
    }
}
