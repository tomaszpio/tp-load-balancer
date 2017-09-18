package dispatching;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class BucketCalculator {

    private final int numberOfBuckets;

    public BucketCalculator(int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
    }

    public int calculate(String id) {
        return Hashing.consistentHash(Hashing.sipHash24().hashString(id, Charsets.UTF_8).hashCode(), numberOfBuckets);
    }
}
