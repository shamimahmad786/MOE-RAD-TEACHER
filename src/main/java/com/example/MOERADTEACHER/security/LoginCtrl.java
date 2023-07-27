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
			  
			  ArrayList<String>  ar =new ArrayList<String>();
			  ar.add("53921");
			  ar.add("2647");
			  ar.add("2508");
			  ar.add("31780");
			  ar.add("33657");
			  ar.add("22561");
			  ar.add("1370");
			  ar.add("40316");
			  ar.add("27469");
			  ar.add("1480");
			  ar.add("2243");
			  ar.add("1454");
			  ar.add("27372");
			  ar.add("79634");
			  ar.add("30042");
			  ar.add("2132");
			  ar.add("1697");
			  ar.add("40353");
			  ar.add("42381");
			  ar.add("1496");
			  ar.add("1171");
			  ar.add("1582");
			  ar.add("2237");
			  ar.add("7165");
			  ar.add("83683");
			  ar.add("1607");
			  ar.add("2571");
			  ar.add("26147");
			  ar.add("2573");
			  ar.add("2381");
			  ar.add("46159");
			  ar.add("70622");
			  ar.add("55018");
			  ar.add("68787");
			  ar.add("76804");
			  ar.add("55452");
			  ar.add("45811");
			  ar.add("7509");
			  ar.add("62782");
			  ar.add("18659");
			  ar.add("44494");
			  ar.add("13637");
			  ar.add("47810");
			  ar.add("51743");
			  ar.add("43414");
			  ar.add("30279");
			  ar.add("2915");
			  ar.add("59710");
			  ar.add("48238");
			  ar.add("33616");
			  ar.add("25868");
			  ar.add("2388");
			  ar.add("56641");
			  ar.add("52868");
			  ar.add("44088");
			  ar.add("8537");
			  ar.add("44148");
			  ar.add("55326");
			  ar.add("27602");
			  ar.add("22534");
			  ar.add("7184");
			  ar.add("56450");
			  ar.add("3335");
			  ar.add("22260");
			  ar.add("51879");
			  ar.add("3698");
			  ar.add("1186");
			  ar.add("49864");
			  ar.add("3047");
			  ar.add("2461");
			  ar.add("3068");
			  ar.add("3583");
			  ar.add("10187");
			  ar.add("2044");
			  ar.add("56413");
			  ar.add("47907");
			  ar.add("2806");
			  ar.add("35913");
			  ar.add("3128");
			  ar.add("56416");
			  ar.add("45108");
			  ar.add("35117");
			  ar.add("54647");
			  ar.add("60487");
			  ar.add("47430");
			  ar.add("2884");
			  ar.add("81854");
			  ar.add("73654");
			  ar.add("5926");
			  ar.add("69287");
			  ar.add("81077");
			  ar.add("78092");
			  ar.add("83272");
			  ar.add("44772");
			  ar.add("55674");
			  ar.add("21531");
			  ar.add("52930");
			  ar.add("22753");
			  ar.add("52821");
			  ar.add("69606");
			  ar.add("5267");
			  ar.add("45931");
			  ar.add("47891");
			  ar.add("5292");
			  ar.add("15521");
			  ar.add("21432");
			  ar.add("21544");
			  ar.add("21475");
			  ar.add("21637");
			  ar.add("22558");
			  ar.add("42068");
			  ar.add("7907");
			  ar.add("8716");
			  ar.add("44207");
			  ar.add("46101");
			  ar.add("43426");
			  ar.add("69159");
			  ar.add("27329");
			  ar.add("48451");
			  ar.add("3373");
			  ar.add("73302");
			  ar.add("49547");
			  ar.add("59903");
			  ar.add("74895");
			  ar.add("7872");
			  ar.add("61785");
			  ar.add("48572");

		

		







//			  
			  for(int i=0;i<ar.size();i++) {
			  
			  try {
//System.out.println(ar.get(i));
				QueryResult q2=nativeRepository.executeQueries("select teacher_id from teacher_profile where teacher_employee_code='"+ar.get(i)+"'");
				
				String query1="update public.teacher_profile set  verify_flag='TA' where teacher_employee_code='"+ar.get(i)+"'";
		
				nativeRepository.updateQueries(query1);
				nativeRepository.updateQueries("update public.teacher_form_status set final_status='TA', form5_status='EC'  where teacher_id='"+String.valueOf(q2.getRowValue().get(0).get("teacher_id"))+"'");
				nativeRepository.updateQueries("update transfer.transfer_teacher_check set unfrez_flag='Y'  where teacher_id='"+String.valueOf(q2.getRowValue().get(0).get("teacher_id"))+"'");
//				
				System.out.println("teacherId--->"+String.valueOf(q2.getRowValue().get(0).get("teacher_id")));
			mp.put("status", 1);
			
			  }catch(Exception ex) {
				  System.out.println(ar.get(i));
				  mp.put("status", 0);
				  ex.printStackTrace();
			  }
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
