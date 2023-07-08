package com.example.MOERADTEACHER.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.MOERADTEACHER.common.modal.TeacherFormStatus;
import com.example.MOERADTEACHER.common.modal.TeacherProfile;
import com.example.MOERADTEACHER.common.util.FixHashing;
import com.example.MOERADTEACHER.common.util.QueryResult;
import com.example.MOERADTEACHER.common.util.StaticReportBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.example.MOERADTEACHER.common.util.ApiPaths;

@RestController
//@RequestMapping(ApiPaths.LoginCtrl.CTRL)
@RequestMapping("/api/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = {"http://10.25.26.251:4200/","https://kvsdemo.udiseplus.gov.in/","https://kvsonlinetransfer.kvs.gov.in","http://10.25.26.251:4200","http://10.25.26.10:4200","http://10.25.26.10:6200","http://demo.sdmis.gov.in","http://pgi.seshagun.gov.in","https://pgi.udiseplus.gov.in","http://pgi.udiseplus.gov.in","https://demopgi.udiseplus.gov.in","https://dashboard.seshagun.gov.in/","https://dashboard.udiseplus.gov.in"}, allowedHeaders = "*",allowCredentials = "true")
public class LoginCtrl {
	
	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

//	private final UserServiceImp userServiceImp;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ModelMapper modelMapper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private LoginNativeRepository loginNativeRepository;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	
//	@Bean("jdbc2")
//	public JdbcTemplate createJdbcTemplate2(@Autowired @Qualifier("loginDataSource") DataSource dataSource2){
//	    return new JdbcTemplate(dataSource2);
//	}
	
	public LoginCtrl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.modelMapper = modelMapper;
		this.authenticationManager = authenticationManager;
//		this.userServiceImp = userServiceImp;
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody String data,@RequestHeader("username") String username) throws Exception {
// System.out.println("call  for sigin");@RequestBody LoginRequest request
		ObjectMapper mapperObj = new ObjectMapper();
		LoginRequest request=new LoginRequest();
//		TeacherFormStatus formData=new TeacherFormStatus();
//		TeacherProfile result = null;
		try {
			request = mapperObj.readValue(data, new TypeReference<LoginRequest>() {
			});
		}catch(Exception ex) {
//			LOGGER.warn("--message--",ex);
		}
		
		Map<String, Object> map =new HashMap<String,Object>();
		System.out.println("In login request--->"+request.getUsername());
		System.out.println("Password--->"+request.getPassword());
		
		try {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}catch(Exception ex) {
//			ex.printStackTrace();
			 return ResponseEntity
		            .status(HttpStatus.UNAUTHORIZED)
		            .body("Invalid userId and password");
		}
 System.out.println("After authenticat--->");
		final User user = userRepository.findByUsername(request.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		  System.out.println("token--->"+token);
		
		  List<Map<String, Object>> appDetails=getApplicationDetails(user.getUsername(),map);
		  
		return ResponseEntity.ok(new TokenResponse(user.getUsername(), token,appDetails,String.valueOf(map.get("firstname")),String.valueOf(map.get("lastname")),String.valueOf(map.get("mobile")),String.valueOf(map.get("email")),String.valueOf(map.get("hashId"))));
		
//		return ResponseEntity.ok(new TokenResponse(user.getUsername(), token, user.getParamName(), user.getParamValue(),
//				user.getRoleId(),user.getSchema_name(),user.getStateId(),user.getStateName(),user.getDistrictName(),user.getGroupId()));
	}
	
	
	public   List<Map<String, Object>> getApplicationDetails(String  userName,  Map<String, Object> map ) throws Exception{
		StaticReportBean sobj=new StaticReportBean();
		FixHashing hashObj=new FixHashing();
		QueryResult qrObj;
		
		if(userName.equalsIgnoreCase("sysadmin")) {
			qrObj = loginNativeRepository.executeQueries("select * from iamuser.application_master");
		}else {		
		 qrObj = loginNativeRepository.executeQueries("select * from public.role_user ru,user_details ud where ud.id= ru.user_id and ud.username='"+userName+"'");
		}

		System.out.println("data------->"+qrObj);
		
		if(!userName.equalsIgnoreCase("sysadmin")) {
			System.out.println("For HashId---->"+hashObj.encrypt(String.valueOf(qrObj.getRowValue().get(0).get("user_id"))));
		map.put("firstname", qrObj.getRowValue().get(0).get("firstname"));
		map.put("lastname", qrObj.getRowValue().get(0).get("lastname"));
		map.put("mobile", qrObj.getRowValue().get(0).get("mobile"));
		map.put("email", qrObj.getRowValue().get(0).get("email"));
		map.put("hashId",hashObj.encrypt(String.valueOf(qrObj.getRowValue().get(0).get("user_id"))));
		}else if(userName.equalsIgnoreCase("sysadmin")){
			map.put("firstname", "sysadmin");
			qrObj.getRowValue().get(0).put("role_id", "99");
		}
		return qrObj.getRowValue();
	}
	
	  @RequestMapping(value = "/getKey", method = RequestMethod.POST)
			public ResponseEntity<?> getKey(HttpServletRequest req)  {
				Map<String,Object> mp=new HashMap<String,Object>();
				ServletContext context = ((HttpServletRequest) req).getSession().getServletContext();
				 System.out.println("req--->"+context.getAttribute("_public_key"));
				mp.put("key",context.getAttribute("_public_key"));
				return ResponseEntity.ok(mp);
			}
	  
	  @RequestMapping(value = "/checkPasswordChanged", method = RequestMethod.POST)
		public ResponseEntity<?> checkPasswordChanged(@RequestBody String userId)  {
		  System.out.println("userId---->"+userId);
		  Map<String,Object> mp=new HashMap<String,Object>();
		  try {
			  ObjectMapper mapperObj = new ObjectMapper();
			  mp = mapperObj.readValue(userId, Map.class);
			}catch(Exception ex) {
//				LOGGER.warn("--message--",ex);
			}
		  
			return ResponseEntity.ok(userDetailsServiceImpl.checkPasswordChanged(String.valueOf(mp.get("userId"))));
		} 
	  
	
	
//	map.put("ApplicationDetails",getApplicationDetails(map.get("user_name").toString(),map));
	
	
}
