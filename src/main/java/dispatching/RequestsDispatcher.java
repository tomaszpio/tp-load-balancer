package dispatching;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

//@todo add failure handling
public class RequestsDispatcher {
    private final  Map<Integer, String> groupWeights;
    private BucketCalculator bucketCalculator;

    public RequestsDispatcher(final  Map<Integer, String> groupWeights, final BucketCalculator bucketCalculator) {
        this.groupWeights = groupWeights;
        this.bucketCalculator = bucketCalculator;
    }

    public String dispatch(String id) {
        final int bucket = bucketCalculator.calculate(id);
        Optional<Map.Entry<Integer, String>> group = groupWeights.entrySet().stream()
                .filter(findGroupBucketBelongTo(bucket))
                .findFirst();

        if(!group.isPresent()) {
            throw new RuntimeException("Can't find group for id "+id);
        }
        return  group.get().getValue();
    }

    private Predicate<Map.Entry<Integer, String>> findGroupBucketBelongTo(int bucket) {
        return entry -> entry.getKey() > bucket;
    }

}
