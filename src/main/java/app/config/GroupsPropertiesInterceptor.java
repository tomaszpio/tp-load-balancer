package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix="com")
public class GroupsPropertiesInterceptor {

    @Value("#{${groupsWeights}}")
    private Map<String, String> groupsWeights = new HashMap<>();

    public Map<String, String> getGroupsWeights() {
        return groupsWeights;
    }
}
