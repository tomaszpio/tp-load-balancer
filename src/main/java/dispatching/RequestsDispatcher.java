package dispatching;

import app.config.GroupsPropertiesInterceptor;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

//@todo add failure handling
public class RequestsDispatcher {
    private static final  Map<Integer, String> groupWeights = new HashMap<>();
    private final GroupsPropertiesInterceptor groupsProperties;

    public RequestsDispatcher(GroupsPropertiesInterceptor groupsProperties) {
        this.groupsProperties = groupsProperties;
        Iterator<Map.Entry<String, String>> groupsProps = this.groupsProperties.getGroupsWeights().entrySet().iterator();
        int lastWeight = 0;
        while(groupsProps.hasNext()){
            final Map.Entry<String, String> groupsProp = groupsProps.next();
            int weightedBucket = lastWeight + Integer.valueOf(groupsProp.getValue());
            groupWeights.put(weightedBucket, groupsProp.getKey());
            lastWeight = weightedBucket;
        }
    }

    public String dispatch(String id) {
        final int bucket = Hashing.consistentHash(Hashing.murmur3_32().hashString(id, Charsets.UTF_8).hashCode(), 10);
        Optional<Map.Entry<Integer, String>> result = groupWeights.entrySet().stream().filter(entry -> entry.getKey() > bucket).findFirst();
        if(!result.isPresent()) {
            throw new RuntimeException("Can't find group for id "+id);
        }
        return  result.get().getValue();
    }
}
