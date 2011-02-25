/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.oauth2;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

public class ProtectedResourceClientFactory {

	public static RestTemplate standard(String accessToken) {
		RestTemplate client = new RestTemplate();
		if (interceptorsSupported) {
			client.setInterceptors(new ClientHttpRequestInterceptor[] { new OAuth2RequestInterceptor(accessToken) });
		} else {
			client.setRequestFactory(OAuth2SigningRequestFactory.standard(client.getRequestFactory(), accessToken));
		}
		return client;		
	}
	
	public static RestTemplate draft10(String accessToken) {
		RestTemplate client = new RestTemplate();
		if (interceptorsSupported) {
			client.setInterceptors(new ClientHttpRequestInterceptor[] { OAuth2RequestInterceptor.draft10(accessToken) });
		} else {
			client.setRequestFactory(OAuth2SigningRequestFactory.draft10(client.getRequestFactory(), accessToken));
		}
		return client;		
	}

	public static RestTemplate draft8(String accessToken) {
		RestTemplate client = new RestTemplate();
		if (interceptorsSupported) {
			client.setInterceptors(new ClientHttpRequestInterceptor[] { OAuth2RequestInterceptor.draft8(accessToken) });
		} else {
			client.setRequestFactory(OAuth2SigningRequestFactory.draft8(client.getRequestFactory(), accessToken));
		}
		return client;		
	}

	private static boolean interceptorsSupported = ClassUtils.isPresent("org.springframework.http.client.ClientHttpRequestInterceptor",
			ProtectedResourceClientFactory.class.getClassLoader());

}