/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.connector.guice;

import com.github.connector.annotations.Mongo;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Guice Module to provide {@link org.springframework.data.mongodb.core.MongoTemplate}.
 *
 * @author Yong Tang
 * @since 0.4
 */
public class MongoModule extends AbstractModule {

    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {

    }

    /**
     * Provide an injection point of {@link org.springframework.data.mongodb.core.MongoTemplate} via an injected
     * {@link com.github.connector.annotations.Mongo}.
     */
    @Provides
    MongoTemplate provideMongoTemplate(Mongo mongo) {
        String config = mongo.config();
        String host = mongo.host();
        int port = mongo.port();
        String database = mongo.database();

        if (StringUtils.isBlank(config)) {
            if (StringUtils.isBlank(host) || StringUtils.isBlank(database)) {
                throw new IllegalArgumentException("Necessary config is missing for Mongo connection.");
            }
        } else {
            Properties prop = new Properties();
            try {
                prop.load(this.getClass().getClassLoader().getResourceAsStream(config));
                host = prop.getProperty("mongo.host");
                port = Integer.parseInt(prop.getProperty("mongo.port"));
                database = prop.getProperty("mongo.database");
            } catch (IOException e) {
                return null;
            }
        }

        try {
            return new MongoTemplate(new MongoClient(host, port), database);
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
