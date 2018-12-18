package readinglist;

import java.util.Optional;//+

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;//+
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//+
public class SecurityConfig extends WebSecurityConfigurerAdapter { //extends WebSecurityConfigurerAdapter

  @Autowired
  private ReaderRepository readerRepository;
  
  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
        .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("READER")
        .and()
        .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("READER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/").access("hasRole('READER')")
        .antMatchers("/**").permitAll()
      .and()
      .formLogin()
        //.passwordParameter("a").usernameParameter("b")//+
        .loginPage("/login")
        .failureUrl("/login?error=true");
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  /*
  @Override
  protected void configure(
              AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        	    return readerRepository.findById(username).get();
        	    
        	*
        	   Optional<Reader> userDetails = readerRepository.findById(username);
               if(userDetails.isPresent()) {
                   return userDetails.get();
               }
               return "A";
            *
        	Optional<Reader> cp = readerRepository.findById(username);//+Reader
        	if (cp.get() != null) {//+
                return cp.get();
              }
            return cp.orElseThrow(
        	        () ->  new UsernameNotFoundException("Unable to get Account with Code = " + username)
        	);
        	//+UserDetails userDetails = readerRepository.findOne(username);
          *if (userDetails != null) {
            return userDetails;
          }
          throw new UsernameNotFoundException("User '" + username + "' not found.");*
  
        }
      }).passwordEncoder(new BCryptPasswordEncoder());//+.passwordEncoder(new BCryptPasswordEncoder())
  }*/
}
