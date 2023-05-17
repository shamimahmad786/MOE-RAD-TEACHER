package com.example.MOERADTEACHER.common.uneecops.master.serviceimpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.MOERADTEACHER.common.uneecops.master.eo.SanctionedPostEo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SanctionedPostFetchListRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SanctionedPostRepository;
import com.example.MOERADTEACHER.common.uneecops.master.service.SanctionedPostService;
import com.example.MOERADTEACHER.common.uneecops.master.utils.UneecopsDateUtils;
import com.example.MOERADTEACHER.common.uneecops.master.vo.DesiginationMasterRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostFechListRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostListRequestVo2;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo2;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostResponseVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckResponeseVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateListRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterRequestVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterRequestVo;

//import com.example.MOERADTEACHER.common.uneecops.master.eo.SanctionedPostEo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SanctionedPostFetchListRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SanctionedPostRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.service.SanctionedPostService;
//import com.example.MOERADTEACHER.common.uneecops.master.utils.UneecopsDateUtils;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.DesiginationMasterRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostFechListRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostListRequestVo2;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostRequestVo2;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostResponseVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostSchoolCodeCheckResponeseVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateListRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SanctionedPostUpdateRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterRequestVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterRequestVo;

@Service
public class SanctionedPostServiceImpl implements SanctionedPostService {

	@Autowired
	private SanctionedPostRepository sanctinedPostRepository;
	
	
	@Autowired
	private SanctionedPostFetchListRepository sanctionedPostFetchListRepository;

	@Override
	public void saveSchoolSanctionedPostDetail(SanctionedPostRequestVo sanctionedPostRequestVo) {

		List<StaffTypeMasterRequestVo> listOfStaffTypeMasterRequestVo = sanctionedPostRequestVo
				.getListOfStaffTypeMasterRequestVo();
		for (StaffTypeMasterRequestVo staffTypeMasterRequestVo : listOfStaffTypeMasterRequestVo) {
			SanctionedPostEo sactionedPostEo = new SanctionedPostEo();
			sactionedPostEo.setSchoolCode(sanctionedPostRequestVo.getSchoolCode());
			sactionedPostEo.setStaffTypeId(staffTypeMasterRequestVo.getStaffTypeId());
			List<DesiginationMasterRequestVo> listdesigineDesiginationMasterRequestVo = staffTypeMasterRequestVo
					.getListOfDesiginationMasterRequestVo();
			for (DesiginationMasterRequestVo desiginationMasterRequestVo : listdesigineDesiginationMasterRequestVo) {
				sactionedPostEo.setPostId(desiginationMasterRequestVo.getPostId());

				List<SubjectMasterRequestVo> listOfSubjectMasterRequestVo = desiginationMasterRequestVo
						.getListOfSubjectMasterRequestVo();
				for (SubjectMasterRequestVo subjectMasterReqVO : listOfSubjectMasterRequestVo) {
					sactionedPostEo.setSubjectId(subjectMasterReqVO.getSubjectId());
					sactionedPostEo.setSanctionedPost(subjectMasterReqVO.getSanctionedPost());
					sactionedPostEo.setOccupiedPost(subjectMasterReqVO.getOccupiedPost());
					sactionedPostEo.setSurplus(subjectMasterReqVO.getSurplus());
					sactionedPostEo.setVacant(subjectMasterReqVO.getVacant());
				}
			}
			;
			sactionedPostEo.setActive(true);
			
			sactionedPostEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
			sanctinedPostRepository.save(sactionedPostEo);
		}

	}

	@Override
	public Page<SanctionedPostResponseVo> fetchlistOfSchoolSanctionedPostDetail(SanctionedPostFechListRequestVo sanctionedPostFechListRequestVo,Pageable pageable) throws Exception {
		
		return sanctionedPostFetchListRepository.fetchListOfSanctionedPostResponseVo(sanctionedPostFechListRequestVo,pageable);
	}

	@Override
	public SanctionedPostSchoolCodeCheckResponeseVo CheckSanctionedPostSchoolCodeExistOrNot(SanctionedPostSchoolCodeCheckRequestVo sanctionedPostSchoolCodeCheckRequestVo)
	{
		SanctionedPostSchoolCodeCheckResponeseVo sanctionedPostSchoolCodeCheckResponeseVo = new SanctionedPostSchoolCodeCheckResponeseVo();
	List<SanctionedPostEo> list = sanctinedPostRepository.findBySchoolCode(sanctionedPostSchoolCodeCheckRequestVo.getSchoolCode());
		System.out.println(list+"list data");
		if(list.isEmpty())
		{
			sanctionedPostSchoolCodeCheckResponeseVo.setMessage("ENTRY-NOT-FOUND");
			return sanctionedPostSchoolCodeCheckResponeseVo;
		}
		else
			sanctionedPostSchoolCodeCheckResponeseVo.setMessage("ENTRY-FOUND");
		
	return sanctionedPostSchoolCodeCheckResponeseVo;
	
	}

	@Override
	public void saveSchoolSanctionedPostDetailV2(SanctionedPostListRequestVo2 sanctionedPostListRequestVo2) {
		
		
		List<SanctionedPostRequestVo2> list =sanctionedPostListRequestVo2.getSanctionedPostRequestVo2();
		for(SanctionedPostRequestVo2 listReq:list)
		{
			SanctionedPostEo sactionedPostEo = new SanctionedPostEo();
			
			sactionedPostEo.setSchoolCode(listReq.getSchoolCode());
			sactionedPostEo.setPostId(listReq.getPostId());
			sactionedPostEo.setStaffTypeId(listReq.getStaffTypeId());
			sactionedPostEo.setSubjectId(listReq.getSubjectId());
			sactionedPostEo.setOccupiedPost(listReq.getOccupiedPost());
			sactionedPostEo.setSanctionedPost(listReq.getSanctionedPost());
			sactionedPostEo.setSurplus(listReq.getSurplus());
			sactionedPostEo.setVacant(listReq.getVacant());
			sactionedPostEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
			sactionedPostEo.setActive(true);
			sanctinedPostRepository.save(sactionedPostEo);
			

		}
		
		
	}

	@Override
	public void updateSchoolSanctionedPostDetail(SanctionedPostUpdateListRequestVo sanctionedPostUpdateListRequestVo) {
		
	List<SanctionedPostUpdateRequestVo> list=	sanctionedPostUpdateListRequestVo.getListOfSanctionedPostUpdateRequestVo();
	
	for(SanctionedPostUpdateRequestVo data:list)
	{
	SanctionedPostEo sanctionedPostEo= 	sanctinedPostRepository.findById(data.getSactionedPostId()).get();
	sanctionedPostEo.setOccupiedPost(data.getOccupiedPost());
	sanctionedPostEo.setVacant(data.getVacant());
	sanctionedPostEo.setSurplus(data.getSurplus());
	sanctionedPostEo.setSanctionedPost(data.getSanctionedPost());
	sanctinedPostRepository.save(sanctionedPostEo);
	}
	}

	
	
	}

