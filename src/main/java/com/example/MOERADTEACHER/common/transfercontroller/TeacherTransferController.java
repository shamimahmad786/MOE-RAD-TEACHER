package com.example.MOERADTEACHER.common.transfercontroller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.MOERADTEACHER.common.bean.TransferDcBeans;
import com.example.MOERADTEACHER.common.transferbean.Transfer;
import com.example.MOERADTEACHER.common.transfermodel.TeacherTransferDetails;
import com.example.MOERADTEACHER.common.transfermodel.TransferKVTeacherDetails;
import com.example.MOERADTEACHER.common.transferservice.TeacherTransferImpl;
import com.example.MOERADTEACHER.common.util.ApiPaths;
import com.example.MOERADTEACHER.common.util.CustomResponse;
import com.example.MOERADTEACHER.common.util.NativeRepository;
import com.example.MOERADTEACHER.common.util.QueryResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(ApiPaths.TeacherTransferCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeacherTransferController {

	@Autowired
	NativeRepository nativeRepository;
	
	@Autowired
	TeacherTransferImpl teacherTransferImpl;
	
	@RequestMapping(value = "/saveTransferDCTCPoints", method = RequestMethod.POST)
	public ResponseEntity<TeacherTransferDetails> saveTransferDCTCPoints(@RequestBody String data) throws Exception {
		
		ObjectMapper mapperObj = new ObjectMapper();
		TeacherTransferDetails trsBean = new TeacherTransferDetails();
		try {
		 trsBean = mapperObj.readValue(data, new TypeReference<TeacherTransferDetails>() {
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return ResponseEntity.ok(teacherTransferImpl.saveTransferDCTCPoints(trsBean));
	}
	
	
	@RequestMapping(value = "/getTeacherTransferDetails", method = RequestMethod.POST)
	public ResponseEntity<TransferDcBeans> saveTeacherTransfer(@RequestBody String data,
			@RequestHeader("username") String username) throws Exception {
		
		String kvCode="";
		String teacherId ="";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			  JsonNode jsonNode = objectMapper.readTree(data);
			  //{"kvCode":"9999","teacherId":1385}
			  kvCode = jsonNode.get("kvCode").asText();
			  teacherId = jsonNode.get("teacherId").asText();
			  
			  System.out.println("kv_code: " + kvCode);
			  System.out.println("teacherId: " + teacherId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("data: " + data);
		  String QUERY ="select ksm.school_type ,tp.teacher_employee_code,tp.teacher_id ,tp.teacher_dob, ttp.absence_days_one, teacher_disability_yn , ttp.personal_status_mdgd , ttp.personal_status_dfpd , tp.marital_status ,tp.spouse_status , ttp.personal_status_spd ,   tp.teacher_gender , ttp.memberjcm from public.teacher_transfer_profile ttp , public.teacher_profile tp , kv.kv_school_master ksm where ttp.teacher_id = tp.teacher_id  and  ttp.teacher_id = '"+teacherId.toString()+"' and ksm.kv_code =tp.kv_code and ksm.kv_code ='"+kvCode.toString()+"'" ;
//	  System.out.println(QUERY.toString());
	  
		  System.out.println("getTeacherTransferDetails data  "+data);
	      
		  
	  String QUERYstation =" select *, DATE_PART('day', work_end_date::timestamp - work_start_date::timestamp) as no_of_days from (\r\n"
  	  		+ "				 	select ksm.station_code , work_start_date , coalesce(work_end_date,'2023-06-30') as work_end_date, \r\n"
  	  		+ "				 	teacher_id   \r\n"
  	  		+ "				 	from 	public.teacher_work_experience twe , kv.kv_school_master ksm \r\n"
  	  		+ "				 	where teacher_id = '"+teacherId.toString()+"'"
  	  		+ "				 	and ksm.kv_code = twe.udise_sch_code \r\n"
  	  		+ "				 	order by work_start_date \r\n"
  	  		+ "				 	) aa order by work_start_date " ;

	        
	  
	  
	  List<Transfer> transfers = new ArrayList<>();
	  TransferDcBeans dcObj=new TransferDcBeans();
    // Open a connection
    try {
    	
    	
    	
  	  DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
//  	  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(QUERY);
  	  
  	  System.out.println(QUERY);
  	  
  	QueryResult	rs=nativeRepository.executeQueries(QUERY);
        
  	  int i = 0;
  	  
  	  int teacher_disability_yn= 0;
  	  int personal_status_mdgd = 0;
  	  int personal_status_dfpd =0;
  	  int lessThanThreeYearRetire=0;
  	  
  	  int gender =0;
  	  int marital_status = 4;
  	  int spouseEmployementStatus=5;
  	  
  	  int personal_status_spd = 0 ; // Single Parent
  	  int teacher_gender = 0;  // Woman Employee
  	  int memberjcm = 0;
  	  String idsStartWith ="(0";
  	  String idsEndsWith =")" ;
  	  
  	  // Current Station Staying Period
  	  
  	  try {
  		  
  		  
  	QueryResult	qr=nativeRepository.executeQueries(QUERYstation);
  		  
//  		  Statement stmtStationDuration = conn.createStatement();
//  		  ResultSet rsStationDuration = stmtStationDuration.executeQuery(QUERYstation);
	          
  	System.out.println("size--->"+qr.getRowValue().size());
  	
  	for(int j=0; j<qr.getRowValue().size();j++) {
//  		System.out.println(j);
//  		System.out.println(String.valueOf(qr.getRowValue().get(j).get("station_code")));
//  		System.out.println(qr.getRowValue().get(j).get("work_start_date"));
//  		System.out.println(qr.getRowValue().get(j).get("work_end_date"));
  		//System.out.println("no of days--->"+Math.round(Double.parseDouble((String.valueOf(qr.getRowValue().get(j).get("no_of_days"))))));
  		//System.out.println(String.valueOf(qr.getRowValue().get(j).get("work_start_date").toString()));
  		
  		SimpleDateFormat sObj=	new SimpleDateFormat("yyyy-MM-dd");
  		
  	  Date date1=sObj.parse(String.valueOf(qr.getRowValue().get(j).get("work_start_date").toString()));  
  		
  	 // System.out.println(date1);
  	  
//  		System.out.println(new Date(String.valueOf(qr.getRowValue().get(j).get("work_start_date").toString())));
//  		Date workEndData=null;
//  		if(qr.getRowValue().get(j).get("work_end_date") !=null) {
//  			workEndData=new Date(String.valueOf(qr.getRowValue().get(j).get("work_end_date")));
//  		}
  		try {
  		transfers.add(new Transfer(String.valueOf(qr.getRowValue().get(j).get("station_code")), sObj.parse(String.valueOf(qr.getRowValue().get(j).get("work_start_date").toString())),sObj.parse(String.valueOf(qr.getRowValue().get(j).get("work_end_date").toString())), (int)Double.parseDouble((String.valueOf(qr.getRowValue().get(j).get("no_of_days"))))));
  	
  		}catch(Exception ex) {
  			ex.printStackTrace();
  		}
  		}
  	
  	
//	          while (qr.getRowValue().) {
//	       	   transfers.add(new Transfer(rsStationDuration.getString("station_code"), rsStationDuration.getDate("work_start_date"), rsStationDuration.getDate("work_start_date"), rsStationDuration.getInt("no_of_days")));
//	       	   
//	          } 
  	  }catch (Exception e) {
  		  e.printStackTrace();
  	  }
  	  int continuousStay = calculateContinuousStay(transfers);   /// Contineous Stay At Current Station
  	
  	  
  	 // System.out.println("continuousStay---->"+continuousStay);
  	  
  	  // 
  	  
  	  List<Transfer> highestThreeRows = getHighestThreeRows(transfers);
  	  
  	  int returnStay = calculateReturnStay(highestThreeRows); 
  	  
  	  
  	  //System.out.println("returnStay--->"+returnStay);
  	  
  	  
  	  for(int k=0;k<rs.getRowValue().size();k++) {
  	  
        
//       while (rs.next()) {
      	 ++i;
      	 String inString ="";
      	// System.out.println(" Teacher ID " + rs.getString("teacher_id"));
      	 
      	 DateTime DateOfBirth = formatter.parseDateTime(String.valueOf(rs.getRowValue().get(k).get("teacher_dob")));
      	 int  teacherAge =  calculateAge(DateOfBirth);
      	// System.out.println(" DateOfBirth " +DateOfBirth+ " teacherAge "+ teacherAge);
      	 
      	 
      	 // Disability  ID = 3
      	 
      	 System.out.println("teacher_disability_yn---->"+rs.getRowValue().get(k).get("teacher_disability_yn"));
      	 
      	 if(rs.getRowValue().get(k).get("teacher_disability_yn")==null) {
      		 teacher_disability_yn= 0;
      	 }else {
      		 teacher_disability_yn =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("teacher_disability_yn")));
      	 }
      	 // Medical Ground
      	 if(rs.getRowValue().get(k).get("personal_status_mdgd")==null) {
      		 personal_status_mdgd= 0;
      	 }else {
      		 personal_status_mdgd =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("personal_status_mdgd")));
      	 }
      	 // Death in Family
      	 if(rs.getRowValue().get(k).get("personal_status_dfpd")==null) {
      		 personal_status_dfpd= 0;
      	 }else {
      		 personal_status_dfpd =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("personal_status_dfpd")));
      	 }
      	 
      	 // Less Than 3 Years to retire
      	 if (teacherAge >= 57) {
      		 lessThanThreeYearRetire = 1;
      	 }
      	 // Maritial Status
      	 if(rs.getRowValue().get(k).get("marital_status")==null) {  // 1 Married , 4 Single 7 Widow
      		 marital_status= 4;
      	 }else {
      		 marital_status =Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("marital_status")));
      	 }
      	 // Spouse Employment Status 
      	 if (marital_status== 1) {  // ie Person is married
      		 
      		 if(rs.getRowValue().get(k).get("spouse_status")==null) {  // 1 KVS 2 Central 3 State 5 None
      			 spouseEmployementStatus= 5;
	        	 }else {
	        		 spouseEmployementStatus =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("spouse_status")));
	        	 }
      		 
      	 } //  Single Parent
      	 if(rs.getRowValue().get(k).get("personal_status_spd")==null ) {  
      		 personal_status_spd= 0;
      	 }else {
      		personal_status_spd = Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("personal_status_spd")));
      	 }
      	 // Woman Employee
      	 if(rs.getRowValue().get(k).get("teacher_gender")==null) {  
      		 teacher_gender= 0;
      	 }else {
      		 teacher_gender =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("teacher_gender")));
      	 }
      	 // Member of JCM 
      	 if(rs.getRowValue().get(k).get("memberjcm")==null) {  // RJCM = 1 , NJCM = 2 , for other 0 
      		 teacher_gender= 0;
      	 }else {
      		 teacher_gender =  Integer.parseInt(String.valueOf(rs.getRowValue().get(k).get("memberjcm")));
      	 }
      	 
      	 
      	 // Prepare Points  -- DISABILITY ID 3
      	 if(teacher_disability_yn == 1) {
      		 idsStartWith = idsStartWith.concat(",3");
      	 }
      	 // MDGD ID -- 4
      	 if(personal_status_mdgd == 1) {
      		 idsStartWith = idsStartWith.concat(",4");
      	 }
      	 // DFP ID-- 5
      	 if(personal_status_dfpd == 1) {
      		 idsStartWith = idsStartWith.concat(",5");
      	 }
      	 // Less than 3 years to retire -- 6 
      	 if(lessThanThreeYearRetire == 1) {
      		 idsStartWith = idsStartWith.concat(",6");
      	 }
      	
      	 // Spouse Employment Status  ID 7 for KV 8 For Central Govt 9 For State 
      	 if(spouseEmployementStatus < 5) { //
      		 spouseEmployementStatus = spouseEmployementStatus + 6;
      		 String sEmployementStatus = ","+spouseEmployementStatus;
      		 idsStartWith = idsStartWith.concat(sEmployementStatus);
      	 }
      	 // GENDER 2 IE Woman  ID 11
      	 if(teacher_gender == 2) {
      		 idsStartWith = idsStartWith.concat(",11");
      	 }
      	 // JCM Member ID 12 
      	 if(memberjcm > 0 ) {
      		 idsStartWith = idsStartWith.concat(",12");
      	 }
      	 inString  = idsStartWith.concat(idsEndsWith);
      	 System.out.print(inString);
      	 
      }
       
//  	  }
//  	absence_days_one;
  	  
  	dcObj.setDcStayAtStation(continuousStay);
  	dcObj.setDcReturnStation(returnStay);
  	if(rs.getRowValue().get(0).get("personal_status_spd") !=null) {
  	dcObj.setDcPeriodAbsence(Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("absence_days_one"))));
  	}else {
  		dcObj.setDcPeriodAbsence(0);
  	}
  	
  //	System.out.println(dcObj.getDcStayAtStation());
  //	System.out.println(dcObj.getDcPeriodAbsence());
  //	System.out.println(dcObj.getDcReturnStation());
  	dcObj.setDcStayStationPoint((int)Math.floor((dcObj.getDcStayAtStation()+dcObj.getDcReturnStation()-dcObj.getDcPeriodAbsence())/365)*2);
  	
  	
//  	dcObj.setDcStayStationPoint(dcStayStationPoint); To be calculate
  	dcObj.setDcTenureHardPoint(0); //Pending from Bibek Ghos
  	// Disability
  	if(teacher_disability_yn==1) {  
  	dcObj.setDcPhysicalChallengedPoint(-20);
//  	set point from master
  	}else {
  		dcObj.setDcPhysicalChallengedPoint(0);
//  		dcObj.setDcPhysicalChallengedPoint();	
  	}
  	// Medical Ground or Death of Family
  	if(personal_status_mdgd==1 || personal_status_dfpd==1) {
  	dcObj.setDcMdDfGroungPoint(-14); 
//  	set point from master
  	}else {
  		dcObj.setDcMdDfGroungPoint(0);
  	}
  	// LTR - Less than 3 years to retire
  	if(lessThanThreeYearRetire==1) {
  	dcObj.setDcLtrPoint(-6);   
//  	set point from master
  	}else {
  		dcObj.setDcLtrPoint(0);
  	}
  	
  Map<Integer,Object> spousePointObj=new HashMap<Integer,Object>();
  
  List<Integer> applicatblePoint=new ArrayList<Integer>();
  
  
  spousePointObj.put(1, -10);  // same station
  spousePointObj.put(2, -8);  // same station
  spousePointObj.put(3, -6); //same station
  spousePointObj.put(4, -4); // Gender Female
  spousePointObj.put(6, -12);  	
  
  
  
  	Integer spouseJobType=null;
  	Boolean spouseAtSameStation=false;
  	
  	System.out.println("Marirtal--->"+rs.getRowValue().get(0).get("marital_status"));
  	
  	if(rs.getRowValue().get(0).get("marital_status") !=null &&  String.valueOf(rs.getRowValue().get(0).get("marital_status")).equalsIgnoreCase("1")) {
  		spouseJobType=Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("spouse_status")));
  		System.out.println("spouseJobType-->"+spouseJobType);
  		if(spouseJobType !=null && spouseJobType !=5) {
  		 spouseAtSameStation =checkSpouseAtSameStation(Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("teacher_id"))));	
  	System.out.println(spouseAtSameStation);
  		}
  		
  		if(spouseAtSameStation) {
  			applicatblePoint.add(spouseJobType);	
  		}
  		
  		
  	}
  	
  	Integer teacherGender =  Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("teacher_gender")));
  	
  	if(teacherGender==2) {
  		applicatblePoint.add(4);
  	}
  	
  	if(personal_status_spd==1) {
  		applicatblePoint.add(6);
  	}
  	
  	Integer minValue=0;
  	System.out.println("applicatblePoint"+applicatblePoint);
  	if(applicatblePoint.size()>0) {
 for(int l=0;l<applicatblePoint.size();l++) {
	Integer dyValue=Integer.parseInt(String.valueOf(spousePointObj.get(applicatblePoint.get(l))));	
	if(minValue>dyValue) {
		minValue=dyValue;
	}
 }
  	}
  	
  	//System.out.println("min Value---->"+minValue);
  	if(minValue==-4) {
  		dcObj.setDcNonSopouseSinglePoint(minValue);
  	}else if(minValue ==-12) {
  		dcObj.setDcSinglePoint(minValue);
  	}
  	dcObj.setDcSpousePoint(minValue);
  	
  	System.out.println("min Value---->"+minValue);
  	
  	Integer schoolType=Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("school_type")));
  	
  	System.out.println("schoolType---->"+schoolType);
  	
  	try {
  		memberjcm=Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("memberjcm")));
  		System.out.println("memberjcm---->"+memberjcm);
  	}catch(Exception e) {
  		memberjcm = 0;
  	}
  	 
  	 
  
  	
  	if((schoolType==2 || schoolType==3) && (memberjcm==1 || memberjcm==2)) {
  		dcObj.setDcRjcmNjcmPoint(-6);
  	}else {
  		dcObj.setDcRjcmNjcmPoint(0);
  	}
  	
  	dcObj.setKvCode(String.valueOf(rs.getRowValue().get(0).get("kv_code")));
  	dcObj.setTeacherId(Integer.parseInt(String.valueOf(rs.getRowValue().get(0).get("teacher_id"))));
  	dcObj.setTeacherEmployeeCode(String.valueOf(rs.getRowValue().get(0).get("teacher_employee_code")));
  	
  	System.out.println("Employee code--->"+String.valueOf(rs.getRowValue().get(0).get("teacher_employee_code")));
  	
  	dcObj.setCreatedDateTime(new Date());
  	dcObj.setUpdateDateTime(new Date());
  	
  	Integer maxAmoungFive=0;
  	
  	List<Integer> findMax=new ArrayList<Integer>();
  	findMax.add(dcObj.getDcPhysicalChallengedPoint());
  	findMax.add(dcObj.getDcMdDfGroungPoint());
  	findMax.add(dcObj.getDcLtrPoint());
  	findMax.add(dcObj.getDcSpousePoint());
  	findMax.add(dcObj.getDcRjcmNjcmPoint());
  	
  	System.out.println(findMax);
  	
  	maxAmoungFive=findMax.stream().min(Comparator.naturalOrder()).get();
//  	for()
//  	if(dcObj.getDcPhysicalChallengedPoint()>dcObj.get) {
//  		
//  	}
  	System.out.println("maxAmoungFive--->"+maxAmoungFive);
  	Integer dc_total=dcObj.getDcStayStationPoint()+maxAmoungFive ;
  	
  	dcObj.setDcTotalPoint(dc_total);
  	
  	// Spouse relaitedPoint
  
  	
  	
  	
  	  
//  	dcObj.set
  	
       
//       conn.close();
  	
		

    }catch(Exception ex) {
    	ex.printStackTrace();
    } 
    
	
    
  	
				return ResponseEntity.ok(dcObj);
	}
	
	
	   public static int calculateAge(DateTime birthDate) {
		   
		    DateTimeFormatter formatter1 = DateTimeFormat.forPattern("dd/MM/yyyy");
		    DateTime Configuredt = formatter1.parseDateTime("30/06/2023");
			Years teacherAge = Years.yearsBetween(birthDate,Configuredt);
	        return teacherAge.getYears();
	    }
	   
	   // Check null for teacher disability
	   public static boolean isNullOrEmpty(String str) {
		   return str== null || str.isEmpty();
	   }
	   
	   
	  public static int calculateContinuousStay(List<Transfer> transfers) {
	        int totalStay = 0;
	        String currentStation = null;
	        int noOfDays= 0;
	        
	        System.out.println("transfers--->"+transfers.size());
	        for (Transfer transfer : transfers) {
	            if (currentStation == null) {
	                // First transfer for the person
	            	System.out.println(transfer.getStation());
	            	currentStation = transfer.getStation();
	            	noOfDays =  transfer.getNoOfDays(); 

	            } else if (transfer.getStation().equals(currentStation)) {
	            	noOfDays= noOfDays + transfer.getNoOfDays();
	            	
	            } else {
	                // Person moved to another station, reset the current stay variables
	                currentStation = transfer.getStation();
	                noOfDays =  transfer.getNoOfDays(); 
	            }
	        }
	        totalStay = noOfDays + 1;
	        System.out.println("totalStay---->"+totalStay);
	        return totalStay;
	    } 
	  
	  public  int calculateReturnStay(List<Transfer> transfers) {
	        int totalStay = 0;
	        int key=0;
       	
	        
	        if (transfers.size() >= 3) {
	            Transfer firstRow = transfers.get(0);
	            Transfer secondRow = transfers.get(1);
	            Transfer thirdRow = transfers.get(2);

	            if (firstRow.getStation().equals(thirdRow.getStation()) && !secondRow.getStation().equals(firstRow.getStation())) {
	            	System.out.println("First and third rows have the same station, and second row has a different station.");
	            	
	            	String strcalculateTenure= calculateReturnStayTenure(secondRow.getStation(),secondRow.getNoOfDays(), secondRow.getStartDate(),1);
	            	String[] TenurseDescript= strcalculateTenure.split(":");
	            	key = Integer.parseInt(TenurseDescript[0]);
	            	totalStay = Integer.parseInt(TenurseDescript[3]);
	            	if (key==1)
	            	 return totalStay;
	                
	                  
	            } else {
	                System.out.println("District pattern doesn't match.");
	            }
	        } else {
	            System.out.println("Not enough transfers to check the district pattern.");
	        }
	        
	        	      
	        return totalStay;
	    } 
	  
	  public  String calculateReturnStayTenure(String StatinCode, int noOfDays, Date startDate, int typeToCalculate) {
	       
	        String returnString ="0:O:0:0";// 0 Means False , O Other Station , 0 No of Year , 0 No of days
	        try {
	        	int category_id = -1;
	        	
	        	String TenureCalculateQry = "select count(*) as no_of_record, category_id  from uneecops.station_category_mapping scm where scm.station_code ='"+StatinCode+"'   "
	        			 +" and '"+startDate +"' between from_date and to_date "
	        			+ "group by category_id ";
//	        	  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//		          Statement stmt = conn.createStatement();
//		          ResultSet rs = stmt.executeQuery(TenureCalculateQry);
		          
	          	QueryResult	rs=nativeRepository.executeQueries(TenureCalculateQry);  
		          if (typeToCalculate == 1) {
	        		
		        	  
		        	  for(int i=0;i<rs.getRowValue().size();i++) {
		        	  
//		        		while(rs.next()) {		        			
		        			category_id =Integer.parseInt(String.valueOf(rs.getRowValue().get(i).get("category_id")));
		        					if(category_id==4) {// For Very Very Hard
					        			double noOf_Year = Double.parseDouble(String.valueOf(noOfDays))/ 364;
					        			if (noOf_Year < 2.0 ) { // Did not Complete 2 Year 
					        				returnString = "1:VH:"+noOf_Year+":"+noOfDays;// 1 = True , VH Very Hard ,  
					        				return returnString;
					        			}
					        			
					        		}else { // For all other category it must be 3
					        			double noOf_Year = Double.parseDouble(String.valueOf(noOfDays))/ 364;
					        			if (noOf_Year < 3.0 ) { 
					        				returnString = "1:O:"+noOf_Year+":"+noOfDays;
					        				return returnString;
					        			}
					        		}
		        					return returnString;		
//		        		}
		        		
		        	  }
		        		
		        		  return returnString;
	        		
	        	}
	        	
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	        finally {
	        
	        }
	      
	        return returnString;	
	     
	    } 
	  
	    public static List<Transfer> getHighestThreeRows(List<Transfer> transfers) {
	        return transfers.stream()
	                .sorted(Comparator.comparingInt(Transfer::getRowNumber).reversed())
	                .limit(3)
	                .collect(Collectors.toList());
	    }
	    
	    
	    public boolean checkSpouseAtSameStation(Integer teacherId) {
	    	
	    	QueryResult	spouseStation=nativeRepository.executeQueries("select spouse_station_code from teacher_profile tp where tp.teacher_id =318"); 
	    	QueryResult	teacherStation=nativeRepository.executeQueries("select station_code from kv.kv_school_master ksm where kv_code in (select kv_code from teacher_profile tp where tp.teacher_id =318) ");
	       
	    	System.out.println(spouseStation.getRowValue().get(0).get("spouse_station_code"));
	    	System.out.println(teacherStation.getRowValue().get(0).get("station_code"));
	    	if(spouseStation.getRowValue().get(0).get("spouse_station_code") !=null && teacherStation.getRowValue().get(0).get("station_code") !=null) {
	    		if(String.valueOf(spouseStation.getRowValue().get(0).get("spouse_station_code")).equalsIgnoreCase(String.valueOf(teacherStation.getRowValue().get(0).get("station_code")))) {
	    			return true;
	    		}else {
	    			return false;		
	    		}
	    	}else {
	    		return false;	
	    	}
					
	    }
	    
	
}
