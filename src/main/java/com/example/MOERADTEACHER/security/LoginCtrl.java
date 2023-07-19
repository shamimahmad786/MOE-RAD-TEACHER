package com.example.MOERADTEACHER.security;

import java.io.FileWriter;
import java.util.ArrayList;
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

import com.example.MOERADTEACHER.common.bean.TeacherProfileBean;
import com.example.MOERADTEACHER.common.intercepts.RSAUtil;
import com.example.MOERADTEACHER.common.modal.TeacherFormStatus;
import com.example.MOERADTEACHER.common.modal.TeacherProfile;
import com.example.MOERADTEACHER.common.util.FixHashing;
import com.example.MOERADTEACHER.common.util.NativeRepository;
import com.example.MOERADTEACHER.common.util.QueryResult;
import com.example.MOERADTEACHER.common.util.StaticReportBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.me.user.UserService.user.modal.User;


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
	
	@Autowired
	NativeRepository nativeRepository;
	
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
	public ResponseEntity<?> login(@RequestBody String data,@RequestHeader("username") String username,HttpServletRequest request) throws Exception {
		String userId=null;
		String password=null;
		String newPassword=null;
		ObjectMapper mapperObj = new ObjectMapper();
		LoginRequest requests=new LoginRequest();
		try {
			requests = mapperObj.readValue(data, new TypeReference<LoginRequest>() {
			});
		}catch(Exception ex) {
//			LOGGER.warn("--message--",ex);
		}
		
		try {
			ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();		
			String privateKeys =  (String) context.getAttribute("_private_key");			
			userId=RSAUtil.decrypt(String.valueOf(requests.getUsername()), privateKeys);
			password=RSAUtil.decrypt(String.valueOf(requests.getPassword()), privateKeys);
			
			System.out.println("userId--->"+userId);
			System.out.println("password--->"+password);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		Map<String, Object> map =new HashMap<String,Object>();
		try {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
		}catch(Exception ex) {
			
			System.out.println("unauthorized");
			
			 return ResponseEntity
		            .status(HttpStatus.UNAUTHORIZED)
		            .body("Invalid userId and password");
		}

		final User user = userRepository.findByUsername(userId);
		final String token = jwtTokenUtil.generateToken(user);
		
		System.out.println("token--->"+user.getUsername());
		
		List<Map<String, Object>> appDetails=getApplicationDetails(user.getUsername(),map);
		
		System.out.println("appDetails---->"+appDetails);
		return ResponseEntity.ok(new TokenResponse(user.getUsername(), token,appDetails,String.valueOf(map.get("firstname")),String.valueOf(map.get("lastname")),String.valueOf(map.get("mobile")),String.valueOf(map.get("email")),String.valueOf(map.get("hashId"))));
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
	  
	
	  
	  
		@RequestMapping(value = "/createKvUser", method = RequestMethod.POST)
		public User createUser(@RequestBody String data) throws Exception {
			ObjectMapper mapperObj = new ObjectMapper();
			
			System.out.println("data--->"+data);
			
			User userdata=new User();
			try {
				userdata = mapperObj.readValue(data, new TypeReference<User>() {
				});
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	
			return userDetailsServiceImpl.createKVUser(userdata);
		}
		
		
		  
		  @RequestMapping(value = "/renamePassword", method = RequestMethod.POST)
			public Map<String,Object> renamePassword(@RequestBody String data,HttpServletRequest request) throws Exception {
			  
			   System.out.println("data---->"+data);
			  
			  ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();		
				String privateKeys =  (String) context.getAttribute("_private_key");
			 String userId = null;	
			  String password = null;
			  String newPassword = null;
			  // System.out.println(data);
			  ObjectMapper mapperObj = new ObjectMapper();
				RenamePassword tdata=new RenamePassword();
				try {
					tdata = mapperObj.readValue(data, new TypeReference<RenamePassword>() {
					});
				}catch(Exception ex) {
					ex.printStackTrace();
//					LOGGER.warn("--message--",ex);
				}
				
				 System.out.println("userId---->"+tdata.getUserId());
				
				try {
					userId=RSAUtil.decrypt(String.valueOf(tdata.getUserId()), privateKeys);
					password=RSAUtil.decrypt(String.valueOf(tdata.getOldPassword()), privateKeys);
					newPassword=RSAUtil.decrypt(String.valueOf(tdata.getNewPassword()), privateKeys);
					
					System.out.println("userId--->"+userId);
					 System.out.println("password--->"+password);
					 System.out.println("newPassword--->"+newPassword);
//					 System.out.println("matched1--->"+matched1);
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
//				return null;
			  return userDetailsServiceImpl.renamePassword(userId,password,newPassword);
//				// System.out.println("userId---->"+userId);
//				return userService.resetPassword(userId);
			}
		  
		  
		  @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
			public Map<String,Object> resetPassword(@RequestBody String userId) throws Exception {
				 System.out.println("userId---->"+userId);
				return userDetailsServiceImpl.resetPassword(userId);
			}
		
		  
		  @RequestMapping(value = "/schoolResetPassword", method = RequestMethod.POST)
			public Map<String,Object> schoolResetPassword(@RequestBody String userId) throws Exception {
				 System.out.println("userId---->"+userId);
				return userDetailsServiceImpl.resetPassword(userId);
			}
		  
		  
		  @RequestMapping(value = "/correctPassword", method = RequestMethod.POST)
		  public Map<String,Object> correctPassword(@RequestBody String empcode) {
//			  System.out.println("data--->"+data);
////			  System.out.println(empcode);
			  Map<String,Object>  mp=new HashMap<String,Object>();
//			  
//			  
//			  try {
//				  QueryResult qruser=  loginNativeRepository.executeQueries("select teacher_employee_code,teacher_id from public.userlist3");
//				 
//				  for(int i=0;i<qruser.getRowValue().size();i++) {
//			
//					  
//					  QueryResult qr1=  loginNativeRepository.executeQueries("select id,username from public.user_details where username='"+qruser.getRowValue().get(i).get("teacher_employee_code")+"'");
//					  
//					  ObjectMapper mapperObj = new ObjectMapper();
//					  try {
//						  
//						  
//							 System.out.println(qr1.getRowValue().size());
//							 Logis mp1=new Logis();
//							 if(qr1.getRowValue().size()>0) {
//								 
//								 
//								 mp1 = mapperObj.convertValue(qr1.getRowValue().get(0), Logis.class);
//								 String query1="update public.teacher_profile set teacher_account_id='"+mp1.getId()+"' , teacher_system_generated_code='"+qruser.getRowValue().get(i).get("teacher_employee_code")+"', verify_flag='SI' where teacher_employee_code='"+qruser.getRowValue().get(i).get("teacher_employee_code")+"'";
////									
//									  nativeRepository.updateQueries(query1);
//									  nativeRepository.updateQueries("update public.teacher_form_status set final_status='SI', form5_status='EC'  where teacher_id='"+qruser.getRowValue().get(i).get("teacher_employee_code")+"'"); 
//									  System.out.println(qruser.getRowValue().get(i).get("teacher_employee_code"));
//							 }
//							 
////							 System.out.println("aaa->"+qr1.getRowValue().get(i));
//			
//							}catch(Exception ex) {
//								ex.printStackTrace();
////								LOGGER.warn("--message--",ex);
//							}
//					  
//					  
//					  
//					  
//					  
//				  }
////				  System.out.println(qr.getRowValue().get(0).get("teacher_employee_code"));
//				  
////				  System.out.println();
//				  
////	  for(int i=0;i<qr.getRowValue().size();i++) {
////		  QueryResult qr1=  loginNativeRepository.executeQueries("select id,username from public.user_details where username='"+qr.getRowValue().get(i).get("teacher_employee_code")+"'");  
////		 System.out.println(qr1.getRowValue().size());
////		 System.out.println(qr1.getRowValue());
////			ObjectMapper mapperObj = new ObjectMapper();
////			ArrayList<Map<String,Integer>> mp=new ArrayList<Map<String,Integer>>();
//			Logis mp1=new Logis();
////			for()
////		 try {
////			 System.out.println(qr1.getRowValue());
////			 System.out.println("aaa->"+qr1.getRowValue().get(i));
////			 mp1 = mapperObj.convertValue(qr1.getRowValue().get(i), Logis.class);
////			}catch(Exception ex) {
////				ex.printStackTrace();
//////				LOGGER.warn("--message--",ex);
////			}
////		 System.out.println(mp1.getTeacher_employee_code());
////		  if(qr1.getRowValue().size()>0) {
////		  String query1="update public.teacher_profile set teacher_account_id='"+mp1.getTeacher_id()+"' , teacher_system_generated_code='"+mp1.getTeacher_employee_code()+"', verify_flag='SI' where teacher_employee_code='"+mp1.getTeacher_employee_code()+"'";
////		
////		  nativeRepository.updateQueries(query1);
////		  nativeRepository.updateQueries("update public.teacher_form_status set final_status='SI', form5_status='EC'  where teacher_id='"+String.valueOf(qr.getRowValue().get(0).get("teacher_id"))+"'");
////		  }
//	  }
//			  catch(Exception ex) {
//				  ex.printStackTrace();
//			  }
			  
			  
			  try {
//			  String query="select id from public.user_details where username='"+empcode+"'";
//			
//			  QueryResult qr=  loginNativeRepository.executeQueries(query);
			Integer ids=0;
			
//			if(qr !=null) {
//			 ids=Integer.parseInt(String.valueOf(qr.getRowValue().get(0).get("id")));
//			}
			
//			if(ids !=0) {
				
				QueryResult q2=nativeRepository.executeQueries("select teacher_id from teacher_profile where teacher_employee_code='"+empcode+"'");
				
				String query1="update public.teacher_profile set  verify_flag='TA' where teacher_employee_code='"+empcode+"'";
		
				nativeRepository.updateQueries(query1);
				nativeRepository.updateQueries("update public.teacher_form_status set final_status='TA', form5_status='EC'  where teacher_id='"+String.valueOf(q2.getRowValue().get(0).get("teacher_id"))+"'");
				
//			}
			mp.put("status", 1);
			
			  }catch(Exception ex) {
				  mp.put("status", 0);
				  ex.printStackTrace();
			  }
//			return mp;
			  
			  
			  
			  
//			  try {
//					FileWriter writer = new FileWriter("E:/input.txt");
//					QueryResult rs=loginNativeRepository.executeQueries("select id,username from public.user_details limit 300");
//					
//					QueryResult rs1=nativeRepository.executeQueries("select teacher_employee_code,teacher_system_generated_code,teacher_account_id from public.teacher_profile limit 3000");
//					
//					ArrayList<HashMap<String,String>> lt=new ArrayList<HashMap<String,String>>();
//					
//					for(int i=0;i<rs.getRowValue().size();i++) {
//						HashMap<String,String> hs=new HashMap<String,String>();
//						for(int j=0;j<rs1.getRowValue().size();j++) {
//						if(String.valueOf(rs.getRowValue().get(i).get("username")).equalsIgnoreCase(String.valueOf(rs1.getRowValue().get(i).get("teacher_employee_code"))) && ((rs1.getRowValue().get(i).get("teacher_system_generated_code") ==null || String.valueOf(rs1.getRowValue().get(i).get("teacher_system_generated_code")).equalsIgnoreCase("") || rs1.getRowValue().get(i).get("teacher_account_id")==null ||  String.valueOf(rs1.getRowValue().get(i).get("teacher_account_id")).equalsIgnoreCase("")   ))){
//							hs.put(String.valueOf(rs1.getRowValue().get(i).get("teacher_employee_code")), String.valueOf(rs.getRowValue().get(i).get("id")));
////							lt.add(hs);
//							System.out.println(hs);
//							 writer.write(hs + "\n"+ "");
//				
//						}
//								
//						}
//					}
//					
//					writer.close();
//					
//				}catch(Exception ex) {
//					ex.printStackTrace();
//				}
//			  
			  
			  
			return mp;	
		  }
	
	  
	
//	map.put("ApplicationDetails",getApplicationDetails(map.get("user_name").toString(),map));
	
	
}
