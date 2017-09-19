package com.tp.loadbalancer.dispatching;

import com.tp.loadbalancer.config.GroupsPropertiesInterceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GroupWeightsLoader {
    private Iterator<Map.Entry<String, String>> groupsProps;
    private final  Map<Integer, String> groupWeights = new HashMap<>();

    public GroupWeightsLoader(final GroupsPropertiesInterceptor groupsProperties) {
        this.groupsProps = groupsProperties.getGroupsWeights().entrySet().iterator();
    }

    public Map<Integer, String> load() {
        int lastWeight = 0;
        while(groupsProps.hasNext()){
            final Map.Entry<String, String> groupsProp = groupsProps.next();
            int weightedBucket = lastWeight + Integer.valueOf(groupsProp.getValue());
            groupWeights.put(weightedBucket, groupsProp.getKey());
            lastWeight = weightedBucket;
        }
        return  groupWeights;
    }
}
