package com.example.MOERADTEACHER.common.uneecops.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MOERADTEACHER.common.uneecops.master.service.SanctionedPostService;
import com.example.MOERADTEACHER.common.uneecops.master.utils.UneeApiResMsgEnum;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostFechListRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostListRequestVo2;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateListRequestVo;

//import com.example.MOERADTEACHER.common.uneecops.master.service.SanctionedPostService;
//import com.example.MOERADTEACHER.common.uneecops.master.utils.UneeApiResMsgEnum;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostFechListRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostListRequestVo2;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateListRequestVo;

@RestController
@RequestMapping("unee-api/v1/master")
public class UneecopsMasterSanctionedPostMappingController {
	
	
	@Autowired
	private SanctionedPostService sanctionedPostService;
	
	@PostMapping("/save-school-sanctioned-post")
	public ResponseEntity<?> saveSchoolSanctionedPostDetail(@RequestBody SanctionedPostRequestVo sanctionedPostRequestVo)
	{
		sanctionedPostService.saveSchoolSanctionedPostDetail(sanctionedPostRequestVo);
		return new ResponseEntity<>(UneeApiResMsgEnum.SUCCESS,HttpStatus.OK);
	}
	
	@PostMapping("/fetch-list-of-school-sanctioned-post")
	public ResponseEntity<?> fetchlistOfSchoolSanctionedPostDetail(@RequestBody SanctionedPostFechListRequestVo sanctionedPostFechListRequestVo,Pageable pageable) throws Exception
	{
		return new ResponseEntity<>(sanctionedPostService.fetchlistOfSchoolSanctionedPostDetail(sanctionedPostFechListRequestVo,pageable),HttpStatus.OK);
	}
	
	
	@PostMapping("/save-school-sanctioned-post-v2")
	public ResponseEntity<?>saveSchoolSanctionedPostDetail2(@RequestBody SanctionedPostListRequestVo2 SanctionedPostListRequestVo2)
	{
		System.out.println("SanctionedPostListRequestVo2->"+SanctionedPostListRequestVo2);
		sanctionedPostService.saveSchoolSanctionedPostDetailV2(SanctionedPostListRequestVo2);
		return new ResponseEntity<>(UneeApiResMsgEnum.SUCCESS,HttpStatus.OK);
	}
	
	@PostMapping("/update-school-sanctioned-post-detail")
	public ResponseEntity<?>updateSchoolSanctionedPostDetail(@RequestBody SanctionedPostUpdateListRequestVo sanctionedPostUpdateListRequestVo )
	{
		sanctionedPostService.updateSchoolSanctionedPostDetail(sanctionedPostUpdateListRequestVo);
		return new ResponseEntity<>(UneeApiResMsgEnum.SUCCESS,HttpStatus.OK);
	}
	
	@PostMapping("/school-code-exist-in-sanctioned-post")
	public ResponseEntity<?> checkSanctionedPostSchoolCoeExistOrNot(@RequestBody SanctionedPostSchoolCodeCheckRequestVo sanctionedPostSchoolCodeCheckRequestVo)
	{
	return new ResponseEntity<>(sanctionedPostService.CheckSanctionedPostSchoolCodeExistOrNot(sanctionedPostSchoolCodeCheckRequestVo),HttpStatus.OK);
	}

}

