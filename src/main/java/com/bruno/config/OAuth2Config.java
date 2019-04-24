package com.bruno.config;

import com.bruno.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
@EnableResourceServer
public class OAuth2Config implements AuthorizationServerConfigurer {

    private final AuthenticationManager authenticationManagerBean;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()") // /oauth/token_key endpoint is public
                .checkTokenAccess("isAuthenticated()");// /oauth/check_token endpoint requires authentication
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // defining an in-memory client-id/secret
        clients.inMemory()
                .withClient("bruno")
                .secret("{noop}bernardo") // {noop} tells spring to use the default password encoder
                .authorizedGrantTypes("password", "refresh_token") // users are able to login and refresh their tokens
                .scopes("read", "write", "trust");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManagerBean) // necessary when grant_type = password
                .userDetailsService(customUserDetailsService) // necessary when grant_type = refresh_token
                .tokenStore(tokenStore()); // the tokens will be stored in the memory
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}
