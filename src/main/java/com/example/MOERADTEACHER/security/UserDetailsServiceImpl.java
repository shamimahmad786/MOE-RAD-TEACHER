package com.example.MOERADTEACHER.security;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Called load user by username--->"+username);
		
		User user = userRepository.findByUsername(username.trim());
		 System.out.println(user);
		if (user == null) {
			return null;
			// commented by shamim
//			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		// Custom to remove {bcrypt}
		user.setPassword(user.getPassword().replace("{bcrypt}", ""));
		System.out.println("After remove bcrypt password--->"+user.getPassword());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("USER")));
	}

//	private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	// System.out.println(username);
//        User developer = userRepository.findByUsername(username);
//        if (developer == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new org.springframework.security.core.userdetails.User(
//        		developer.getUsername(), 
//        		developer.getPassword(), 
//        		Arrays.asList(new SimpleGrantedAuthority("USER")));
//    }
	
	public Map<String,Object> checkPasswordChanged(String userId){
		
		User  usrObj=	userRepository.checkPasswordChanged(userId);
		Map<String,Object> hs=new HashMap<String,Object>();
		System.out.println("usrObj--->"+usrObj);
		
		
		
		if(usrObj !=null) {
			hs.put("status", 1);
		}else {
			hs.put("status", 0);
		}
		
		return hs;
		
	}
}
