package org.yugh.gatewaynew.config;

/**
 * //
 *
 * @author  余根海
 * @creation  2019-04-09 18:17
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
//@Slf4j
//@Configuration
public class GatewayServerConfig {

    /*extends ResourceServerConfigurerAdapter

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and().securityContext().disable()
                .authorizeRequests()
                .antMatchers(permitAllUrlProperties.getPermitAllPatterns()).permitAll()
                .anyRequest().authenticated();
      //  log.info("============> 配置的白名单Url:{}", permitAllUrlProperties.getPermitAllPatterns());
    }

        */
}
