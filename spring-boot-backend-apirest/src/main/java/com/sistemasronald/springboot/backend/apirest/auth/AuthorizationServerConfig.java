package com.sistemasronald.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;


    //MÉTODO PARA CONFIGURAR LOS PERMISOS DE LOS ENDPOINTS
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
         security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
    }


    //MÉTODO PARA CONFIGURAR A LOS CLIENTES (APLICACIÓN ANGULAR) QUE SE VAN A CONECTAR CON SUS CREDENCIALES
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("angularapp") //corresponde al username del cliente (aplicación)
        .secret(passwordEncoder.encode("12345")) //contraseña para el cliente (aplicación)
        .scopes("read", "write")//permisos que va tener la aplicación (en este caso va leer datos y escribir información)
        .authorizedGrantTypes("password", "refresh_token") //tipo de autenticación del token
        .accessTokenValiditySeconds(3600)//tiempo de caducidad del token
        .refreshTokenValiditySeconds(3600); //obtenemos un token renovado sin tener que volver a iniciar sesión
    }


    //MÉTODO QUE SE ENCARGA DE LA AUTENTICACIÓN Y DE LA VALIDACIÓN DEL TOKEN
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
        .tokenStore(tokenStore())
        .accessTokenConverter(accessTokenConverter())
        .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA); //Llave que firma.
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA); // Llave que verifica y valida.
        return jwtAccessTokenConverter;
    }

    //
}
