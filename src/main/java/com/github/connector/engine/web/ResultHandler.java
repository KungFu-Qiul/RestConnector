/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.connector.engine.web;

/**
 * Executes a generic action (e.g. printing debug information) on the result of
 * an executed request.
 * <p>
 * <p>See static factory methods in
 * {@code org.springframework.engine.web.server.result.MockMvcResultHandlers}.
 * <p>
 * <p>Example:
 * <p>
 * <pre>
 * static imports: MockMvcRequestBuilders.*, MockMvcResultHandlers.*
 *
 * mockMvc.perform(get("/form")).andDo(print());
 * </pre>
 *
 * @author Rossen Stoyanchev
 */
public interface ResultHandler {

    /**
     * Apply the action on the given result.
     *
     * @param result the result of the executed request
     * @throws Exception if a failure occurs
     */
    void handle(HttpResult result) throws Exception;

}
