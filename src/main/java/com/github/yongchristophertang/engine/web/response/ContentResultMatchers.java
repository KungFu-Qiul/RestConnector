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

package com.github.yongchristophertang.engine.web.response;

import com.github.yongchristophertang.engine.web.ResultMatcher;
import org.apache.http.Header;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;


/**
 * Factory for response content assertions. An instance of this class is
 * typically accessed via {@link HttpResultMatchers#content()}.
 *
 * @author Yong Tang
 * @since 0.4
 */
public class ContentResultMatchers {

    /**
     * Assert the Http Response content type.
     */
    public ResultMatcher contentType(String contentType) {
        return result -> MatcherAssert
                .assertThat("Content Type", result.getHttpResponse().getFirstHeader("Content-Type").getValue(),
                        is(contentType));
    }

    /**
     * Assert the response body content with a Hamcrest {@link org.hamcrest.Matcher}.
     * <pre>
     *   .andExpect(content(containsString("text")));
     * </pre>
     */
    public ResultMatcher string(final Matcher<? super String> matcher) {
        return result -> MatcherAssert.assertThat("Response content", result.getResponseStringContent(), matcher);
    }

    /**
     * Assert the response body content as a String.
     */
    public ResultMatcher string(String content) {
        return string(is(content));
    }


    /**
     * Assert all the response headers for the {@code headerName}.
     */
    public ResultMatcher headers(String headerName, final Matcher<? super List<String>> matcher) {
        return result -> MatcherAssert
                .assertThat("Header content of " + headerName,
                        Arrays.asList(result.getHttpResponse().getHeaders(headerName)).stream().map(Header::getValue)
                                .collect(Collectors.toList()), matcher);
    }

    /**
     * Assert the first response header for the {@code headerName}.
     */
    public ResultMatcher header(String headerName, final Matcher<? super String> matcher) {
        return result -> MatcherAssert.assertThat("Header content of " + headerName,
                result.getHttpResponse().getFirstHeader(headerName).getValue(), matcher);
    }
}
