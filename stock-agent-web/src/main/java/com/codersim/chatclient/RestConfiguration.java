/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codersim.chatclient;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Configuration
public class RestConfiguration {

    /**
     * 如果使用基于 netty 的 ReactorClientHttpConnector，可以使用如下配置：
     * <p>
     * https://projectreactor.io/docs/netty/release/reference/index.html#response-timeout
     * https://docs.spring.io/spring-framework/reference/web/webflux-webclient/client-builder.html#webflux-client-builder-reactor-timeout
     */
    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> restClientBuilder
                .requestFactory(
                        ClientHttpRequestFactoryBuilder.reactor().withCustomizer(
                                factory -> {
                                    factory.setConnectTimeout(Duration.of(10, ChronoUnit.MINUTES));
                                    factory.setReadTimeout(Duration.of(10, ChronoUnit.MINUTES));
                                }
                        ).build()
                );
    }

    /**
     * 如果设置 WebClient 响应超时时间，其底层仍然使用 netty 的 ReactorClientHttpConnector 时
     * 1. 在 build 模型 api 时设置，例如：
     *  WebClient.Builder webClientBuilder = WebClient.builder()
     *         .clientConnector(new ReactorClientHttpConnector(
     *                 HttpClient.create().responseTimeout(Duration.of(60, ChronoUnit.SECONDS))));
     *
     *   OllamaApi ollamaApi = OllamaApi.builder()
     *         .webClientBuilder(webClientBuilder)
     *         .build();
     * 2. 按照如下写法，将其作为配置 bean 注入
     */
    @Bean
    public WebClient.Builder webClientBuilder() {

        return WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create().responseTimeout(Duration.of(600, ChronoUnit.SECONDS))
                        )
                );
    }

}
