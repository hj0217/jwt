package com.jwt.jwt.config;

import com.jwt.jwt.config.jwt.JwtAuthenticationFilter;
import com.jwt.jwt.filter.MyFilter3;
import com.jwt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * WebSecurityConfigurerAdapter가 Spring Security 5.7.0-M2부터 deprecated 됨.
 * component-based security configuration으로의 사용자 전환 격려 위함.
 * 따라서 아래와 같이 bean으로 등록하여 사용.
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserRepository userRepository;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Security 기본 설정
        http
                                .addFilter(corsConfig.corsFilter())
                                .csrf().disable() //일단 api서버만 개발하니 비활성화 해주기.
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Session을 사용하지 않음! session사용 시 사용자 정보 노출 우려있음. //default

        http                     // @CrossOrigin(인증이 없을 때 사용 O), 시큐리티 필터에 등록 인증(O)
                                .formLogin().disable() // 기본 html방식은 전혀 사용하지 않을 것임!                 //default
                                .httpBasic().disable() // 통신 시 Id/pw를 매번 싣어서 보내줌 <-> Bearer(Token base)  //default
                                .apply(new MyCustomDsl()); // 커스텀 필터 등록
        //인증
        //http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class); // SecurityFilterChain(1) 먼저 가동 => before/after로 써줘서 실행 순서 조정할 수 있음.


        //권한 부여
        http.authorizeRequests().antMatchers("api/v1//user/**").authenticated()
                                    .antMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                                    .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                    .antMatchers("/api/v1/admin/**").hasRole("USER")
                                    .antMatchers("/other-public-endpoint/**").permitAll();
        return http.build();
    }


    // 커스텀 필터
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager));
                    //.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

}
