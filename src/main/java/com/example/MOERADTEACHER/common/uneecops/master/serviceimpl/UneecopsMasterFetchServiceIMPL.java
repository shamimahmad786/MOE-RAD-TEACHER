package com.example.MOERADTEACHER.common.uneecops.master.serviceimpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.MOERADTEACHER.common.uneecops.master.repo.CategoryMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.DesignationMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.MappingRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.RegionMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SchoolMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.StaffTypeMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.StationMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SubjectMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.service.UneecopsMasterFetchService;
import com.example.MOERADTEACHER.common.uneecops.master.vo.ActiveOrInactiveReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.CategoryMasterResVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationMasterResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionMasterResVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchListReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchResponseVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationMasterResVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterResVO;

//import com.example.MOERADTEACHER.common.uneecops.master.repo.CategoryMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.DesignationMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.MappingRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.RegionMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SchoolMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.StaffTypeMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.StationMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SubjectMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.service.UneecopsMasterFetchService;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.ActiveOrInactiveReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.CategoryMasterResVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationMasterResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionMasterResVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchListReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchResponseVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingSearchReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchResVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StationMasterResVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterResVO;

@Service
public class UneecopsMasterFetchServiceIMPL implements UneecopsMasterFetchService {

	@Autowired
	private StationMasterRepo stationMasterRepo;
	@Autowired
	private RegionMasterRepo  regionMasterRepo;
	@Autowired
	private CategoryMasterRepository categoryMasterRepository; 

	@Autowired
	private MappingRepository mappingRepository;

	@Autowired
	private SchoolMasterRepo schoolMasterRepo;
	@Autowired
	private StaffTypeMasterRepository staffTypeMasterRepository;
	@Autowired
	private DesignationMasterRepository designationMasterRepository;
	@Autowired
	private SubjectMasterRepository subjectMasterRepository;

	

	@Override
	public List<StationMasterResVo> fetchListOfActiveInactiveStation(ActiveOrInactiveReqVO reqVO) {
		
		if(reqVO.getStatus()==null)
		{
		return stationMasterRepo.findStationCodeAndStationName(true);
		}
		else 
			return stationMasterRepo.findStationCodeAndStationName(reqVO.isStatus());
	}

	@Override
	public List<RegionMasterResVo> findListOfActiveInactiveRegion(ActiveOrInactiveReqVO reqVO) {
		
		if(reqVO.getStatus()==null)
		{
		return regionMasterRepo.findRegionCodeAndRegionName(true);
		}
		else
			return regionMasterRepo.findRegionCodeAndRegionName(reqVO.isStatus());
	}

	@Override
	public List<CategoryMasterResVo> findListOfActiveInactiveCategory(ActiveOrInactiveReqVO reqVO) {
		
		if(reqVO.getStatus()==null)
		{
		return categoryMasterRepository.findCategoryNameAndId(true);
		}
		else
			return categoryMasterRepository.findCategoryNameAndId(reqVO.isStatus());
	}

	@Override
	public List<SchoolMasterResVO> findListOfActiveInactiveSchools(ActiveOrInactiveReqVO reqVO) {
		
		if(reqVO.getStatus()==null)
		{
		return schoolMasterRepo.findSchoolCodeAndSchoolName(true);
		}
		else
			return schoolMasterRepo.findSchoolCodeAndSchoolName(reqVO.isStatus());
	}
	
	@Override
	public List<DesignationMasterResVO> findListOfActiveInactiveDesignations(ActiveOrInactiveReqVO reqVO) {	
		return designationMasterRepository.findActiveAndInactiveDesigantions(reqVO.isStatus());
	}

	@Override
	public List<StaffTypeMasterResVO> findListOfActiveInactiveStaffType(ActiveOrInactiveReqVO reqVO) {
		return staffTypeMasterRepository.findActiveAndInactiveStaffType(reqVO.isStatus());
	}
	
	@Override
	public List<SubjectMasterResVO> findListOfActiveInactiveSubject(ActiveOrInactiveReqVO reqVO) {
		return subjectMasterRepository.findActiveAndInactiveSubject(reqVO.isStatus());
	}
	
	@Override
	public List<StationMasterResVo> fetchListOfAllStation() {
		return stationMasterRepo.findListOfStationCodeAndStationName();
		
	}

	@Override
	public List<RegionMasterResVo> fetchListOfAllRegion() {
		return regionMasterRepo.findListOfRegionCodeAndRegionName();
	}

	@Override
	public List<CategoryMasterResVo> fetchListOfAllCategory() {
		return categoryMasterRepository.findListOfCategories();
	}

	@Override

	public List<SchoolMasterResVO> fetchListOfAllSchools() {
		return schoolMasterRepo.findListOfSchools();
	}

	@Override
	public List<StaffTypeMasterResVO> fetchListOfAllStaffType() {	
		return staffTypeMasterRepository.findListOfStaffType();
	}
	
	@Override
	public List<DesignationMasterResVO> fetchListOfDesignations() {		
		return designationMasterRepository.findListOfDesignation();
	}
	@Override
	public List<SubjectMasterResVO> fetchListOfSubjects() {		
		return subjectMasterRepository.findListOfSubjects();
	}


	
	@Override
	public Page<RegionStationMappingSearchResponseVO> findRegionStationMappingList(RegionStationMappingSearchListReqVO reqVO, Pageable pageable) throws Exception {
		return mappingRepository.searchRegionStationMappingList(reqVO, pageable);
	}


	@Override
	public Page<StationCategoryMappingSearchResVO> findStationCategoryMappingList(
			StationCategoryMappingSearchReqVO reqVO, Pageable pageable)throws Exception {
		return mappingRepository.searchStationCategoryMappingList(reqVO, pageable);
	}

	@Override
	public Page<SchoolStationMappingSearchResVO> findSchoolStationMappingList(
			SchoolStationMappingSearchReqVO reqVO, Pageable pageable) {
		return mappingRepository.searchSchoolStationMappingList(reqVO, pageable);
	
	}

	@Override
	public Page<StaffTypePostMappingResVO> findStaffTypePostMappingList(StaffTypePostMappingSearchReqVO reqVO,
			Pageable pageable) {
		
		return mappingRepository.searchStaffTypePostMappingList(reqVO, pageable);
	}
	
}
