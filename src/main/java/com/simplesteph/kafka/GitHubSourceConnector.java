package com.simplesteph.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitHubSourceConnector extends SourceConnector {
    private static Logger log = LoggerFactory.getLogger(GitHubSourceConnector.class);
    private GitHubSourceConnectorConfig config;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        config = new GitHubSourceConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return GitHubSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int n) {
        List<GithubSourceTaskConfig> taskConfigs = config.getTasksConfig();

        // Define the individual task configurations that will be executed.
        ArrayList<Map<String, String>> configs = new ArrayList();

        for(GithubSourceTaskConfig taskConfig: taskConfigs) {
            Map<String, String> connectorConfig = config.originalsStrings();
            connectorConfig.put(GithubSourceTaskConfig.TASKCONFIG_OWNER_PARAMETER, taskConfig.getOwner());
            connectorConfig.put(GithubSourceTaskConfig.TASKCONFIG_NAME_PARAMETER, taskConfig.getRepository());
            connectorConfig.put(GithubSourceTaskConfig.TASKCONFIG_TOPIC_PARAMETER, taskConfig.getTopic());
            configs.add(connectorConfig);
        }

        return configs;
    }

    @Override
    public void stop() {
        // Do things that are necessary to stop your connector.
        // nothing is necessary to stop for this connector
    }

    @Override
    public ConfigDef config() {
        return GitHubSourceConnectorConfig.conf();
    }
}