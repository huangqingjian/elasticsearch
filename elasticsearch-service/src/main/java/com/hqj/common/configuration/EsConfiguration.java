package com.hqj.common.configuration;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es 配置
 */
@Configuration
public class EsConfiguration {
    @Value("${es.host:}")
    private String host;
    @Value("${es.port:}")
    private Integer port;
    @Value("${es.scheme:}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, scheme));
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(2000);
            requestConfigBuilder.setSocketTimeout(5000);
            requestConfigBuilder.setConnectionRequestTimeout(1000);
            requestConfigBuilder.setStaleConnectionCheckEnabled(true);
            return requestConfigBuilder;
        });
        builder.setDefaultHeaders(new Header[]{new BasicHeader("Connection", "close")});
        return new RestHighLevelClient(builder);
    }
}
