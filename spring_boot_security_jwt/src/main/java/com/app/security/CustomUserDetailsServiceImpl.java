package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entities.UserEntity;
import com.app.repository.UserEntityRepository;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	// dep user dao
	@Autowired
	private UserEntityRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// invoke dao's method to get uer details form DB
		UserEntity user = userRepo.findByEmail(email)
				.orElseThrow(() ->
				new UsernameNotFoundException("Invalid Email !!!!!"));
		//=> user email exists
		return new CustomUserDetails(user);
	}

}
