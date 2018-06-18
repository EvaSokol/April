package com.april.testproject.config;

import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class AppUserDetailsService implements UserDetailsService {
    protected static com.april.testproject.entity.User user;

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        com.april.testproject.entity.User activeUserInfo = repository.findByEmailContaining(email).get(0);
        GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
        this.user = repository.findByEmailContaining(email).get(0);
        return new User(activeUserInfo.getEmail(),
                activeUserInfo.getPassword(), Arrays.asList(authority));
    }

	public static com.april.testproject.entity.User getUser() {
		return user;
	}
}
