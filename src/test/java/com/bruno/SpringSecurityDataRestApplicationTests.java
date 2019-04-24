package com.bruno;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SpringSecurityDataRestApplicationTests {

    private static final String EXAMPLES_API = "/api/examples";
    private static final String PARAM_API = "/1";
    private static final String OAUTH_API = "/oauth/token";
    private static final String ADMIN_USERNAME = "admin@test.com";
    private static final String AGENT_USERNAME = "agent@test.com";
    private static final String CUSTOMER_USERNAME = "customer@test.com";
    private static final String PASSWORD = "123";
    private static final String CLIENT_ID = "bruno";
    private static final String CLIENT_SECRET = "bernardo";
    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String PAYLOAD = "{\"name\":\"Test\"}";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void givenNoLoggedUserWhenCallingTheApiThenReturn401Error() throws Exception {
        mockMvc.perform(get(EXAMPLES_API))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenLoggedUserWhenRefreshingTokenThenReturnNewAccessToken() throws Exception {
        var refreshToken = obtainToken(ADMIN_USERNAME)[1];
        var params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        performOAuthRequest(params);
    }

    @Test
    public void givenAgentLoggedInWhenDeletingExampleThenReturn403Error() throws Exception {
        var accessToken = obtainToken(AGENT_USERNAME)[0];

        mockMvc.perform(delete(EXAMPLES_API + PARAM_API)
                .header(AUTHORIZATION, BEARER + accessToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenCustomerLoggedInWhenUpdatingExampleThenReturn403Error() throws Exception {
        var accessToken = obtainToken(CUSTOMER_USERNAME)[0];

        mockMvc.perform(patch(EXAMPLES_API + PARAM_API)
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(CONTENT_TYPE_JSON)
                .accept(CONTENT_TYPE_JSON)
                .content(PAYLOAD))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenAdminLoggedInWhenCreatingExampleThenReturn201Created() throws Exception {
        var accessToken = obtainToken(ADMIN_USERNAME)[0];

        mockMvc.perform(post(EXAMPLES_API)
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(CONTENT_TYPE_JSON)
                .accept(CONTENT_TYPE_JSON)
                .content(PAYLOAD))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenAgentLoggedInWhenCreatingExampleThenReturn403Error() throws Exception {
        var accessToken = obtainToken(AGENT_USERNAME)[0];

        mockMvc.perform(post(EXAMPLES_API)
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(CONTENT_TYPE_JSON)
                .accept(CONTENT_TYPE_JSON)
                .content(PAYLOAD))
                .andExpect(status().isForbidden());
    }

    private String[] obtainToken(String username) throws Exception {
        var params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", PASSWORD);

        var resultString = performOAuthRequest(params)
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultMap = new JacksonJsonParser().parseMap(resultString);

        return new String[]{
                resultMap.get("access_token").toString(),
                resultMap.get("refresh_token").toString()
        };
    }

    private ResultActions performOAuthRequest(LinkedMultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(post(OAUTH_API)
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_JSON));
    }
}
