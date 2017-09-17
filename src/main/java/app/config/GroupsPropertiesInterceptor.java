package app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties
public class GroupsPropertiesInterceptor {

    private Map<String, Integer> groupsWeights= new HashMap<>();

    public Map<String, Integer> getGroupsWeights() {
        return groupsWeights;
    }
}
