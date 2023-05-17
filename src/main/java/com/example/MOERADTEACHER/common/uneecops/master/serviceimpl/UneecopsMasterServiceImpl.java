package com.example.MOERADTEACHER.common.uneecops.master.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MOERADTEACHER.common.uneecops.exception.customException.RegionNameAlreadyExistException;
import com.example.MOERADTEACHER.common.uneecops.exception.customException.SchoolCodeAlreadyExistException;
import com.example.MOERADTEACHER.common.uneecops.master.eo.CategoryEo;
import com.example.MOERADTEACHER.common.uneecops.master.eo.DesignationMasterEO;
import com.example.MOERADTEACHER.common.uneecops.master.eo.RegionEo;
import com.example.MOERADTEACHER.common.uneecops.master.eo.SchoolMasterEO;
import com.example.MOERADTEACHER.common.uneecops.master.eo.StaffTypeMasterEO;
import com.example.MOERADTEACHER.common.uneecops.master.eo.StationEo;
import com.example.MOERADTEACHER.common.uneecops.master.eo.SubjectMasterEO;
import com.example.MOERADTEACHER.common.uneecops.master.repo.CategoryMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.DesignationMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.RegionMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SchoolMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.StaffTypeMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.repo.StationMasterRepo;
import com.example.MOERADTEACHER.common.uneecops.master.repo.SubjectMasterRepository;
import com.example.MOERADTEACHER.common.uneecops.master.service.UneecopsMasterService;
import com.example.MOERADTEACHER.common.uneecops.master.utils.UneecopsDateUtils;
import com.example.MOERADTEACHER.common.uneecops.master.vo.CategoryMasterVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationUpdateMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionMasterVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterUpdateReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeUpdateMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationMasterVo;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterUpdateReqVO;

//import com.example.MOERADTEACHER.common.uneecops.exception.customException.RegionNameAlreadyExistException;
//import com.example.MOERADTEACHER.common.uneecops.exception.customException.SchoolCodeAlreadyExistException;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.CategoryEo;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.DesignationMasterEO;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.RegionEo;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.SchoolMasterEO;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.StaffTypeMasterEO;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.StationEo;
//import com.example.MOERADTEACHER.common.uneecops.master.eo.SubjectMasterEO;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.CategoryMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.DesignationMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.RegionMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SchoolMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.StaffTypeMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.StationMasterRepo;
//import com.example.MOERADTEACHER.common.uneecops.master.repo.SubjectMasterRepository;
//import com.example.MOERADTEACHER.common.uneecops.master.service.UneecopsMasterService;
//import com.example.MOERADTEACHER.common.uneecops.master.utils.UneecopsDateUtils;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.CategoryMasterVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.DesignationUpdateMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionMasterVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolMasterUpdateReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypeUpdateMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.StationMasterVo;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterReqVO;
//import com.example.MOERADTEACHER.common.uneecops.master.vo.SubjectMasterUpdateReqVO;

@Service
public class UneecopsMasterServiceImpl implements UneecopsMasterService {
	@Autowired
	private StationMasterRepo stationMasterRepo;

	@Autowired
	private RegionMasterRepo regionMasterRepo;

	@Autowired
	private CategoryMasterRepository categoryMasterRepository;

	@Autowired
	private SchoolMasterRepo schoolMasterRepo;
	@Autowired
	private StaffTypeMasterRepository staffTypeMasterRepository;
	@Autowired
	private DesignationMasterRepository designationMasterRepository;
	@Autowired
	private SubjectMasterRepository subjectMasterRepository;

	@Override
	public void saveStationMaster(StationMasterVo req) {

		List<StationEo> eoData = stationMasterRepo.findAll();
		if (eoData.size() > 0) {
			for (StationEo eo : eoData) {
				if (req.getStationName().equalsIgnoreCase(eo.getStationName())) {
					throw new SchoolCodeAlreadyExistException(
							eo.getStationName() + " station name is already present.");
				}
				if (req.getStationCode().equals(eo.getStationCode())) {
					throw new SchoolCodeAlreadyExistException(
							eo.getStationCode() + " station code is already present.");
				}

			}
		}
		StationEo stationEo = new StationEo();
		stationEo.setStationCode(req.getStationCode());
		stationEo.setStationName(req.getStationName().toUpperCase());
		stationEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		stationEo.setActive(req.getStatus());
		stationMasterRepo.save(stationEo);

	}

	@Override
	public void saveRegionMaster(RegionMasterVo req) {
		List<RegionEo> eoData = regionMasterRepo.findAll();
		if (eoData.size() > 0) {
			System.out.println("saveRegionMaster --> ");
			for (RegionEo eo : eoData) {
				if (req.getRegionName().equalsIgnoreCase(eo.getRegionName())) {
					throw new RegionNameAlreadyExistException(eo.getRegionName() + " region name is already present.");
				}

				if (req.getRegionCode().equals(eo.getRegionCode())) {
					throw new RegionNameAlreadyExistException(eo.getRegionCode() + " region code is already present.");
				}
			}
		}
		RegionEo regionEo = new RegionEo();
		regionEo.setRegionCode(req.getRegionCode());
		regionEo.setRegionName(req.getRegionName().toUpperCase());
		regionEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		regionEo.setActive(req.getStatus());
		regionMasterRepo.save(regionEo);

	}

	@Override
	public void saveStationCategoryMaster(CategoryMasterVo req) {
		List<CategoryEo> eoData = categoryMasterRepository.findAll();
		if (eoData.size() > 0) {
			for (CategoryEo eo : eoData) {
				if (req.getCategoryName().equalsIgnoreCase(eo.getCategoryName())) {
					throw new SchoolCodeAlreadyExistException(
							eo.getCategoryName() + " category name is already present.");
				}

			}
		}

		CategoryEo categoryEo = new CategoryEo();
		categoryEo.setId(req.getId());
		categoryEo.setCategoryName(req.getCategoryName().toUpperCase());
		categoryEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		categoryEo.setActive(req.getStatus());
		categoryMasterRepository.save(categoryEo);

	}

	private void validationForSchoolShift(SchoolMasterReqVO reqVO) {
		List<SchoolMasterEO> eoData = schoolMasterRepo.findAllBySchoolCode(reqVO.getSchoolCode());
		if (eoData.size() > 0) {
			for (SchoolMasterEO eo : eoData) {
				if (reqVO.getSchoolCode().equals(eo.getSchoolCode())
						&& reqVO.getShift().equalsIgnoreCase(eo.getShift())) {

					throw new SchoolCodeAlreadyExistException(
							"School code " + eo.getSchoolCode() + " for " + eo.getShift() + " is already present.");

				}
			}
		}
	}

	@Override
	public void saveSchoolMaster(SchoolMasterReqVO reqVO) {

		validationForSchoolShift(reqVO);

		System.out.println("reqVo " + reqVO.toString());
		SchoolMasterEO schoolEo = new SchoolMasterEO();
		schoolEo.setSchoolCode(reqVO.getSchoolCode());
		schoolEo.setSchoolName(reqVO.getSchoolName().toUpperCase());
		schoolEo.setShift(reqVO.getShift().toUpperCase());
		schoolEo.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		schoolEo.setSchoolStatus(reqVO.getStatus());
		schoolMasterRepo.save(schoolEo);
	}

	@Transactional
	@Override
	public void updateStationMaster(@Valid StationMasterVo stationVo) {
		StationEo stationEo = stationMasterRepo.findByStationCode(stationVo.getStationCode());
		
		

		List<StationEo> eoData = stationMasterRepo.findAll();
		if (eoData.size() > 0) {
			for (StationEo eo : eoData) {
				if (stationVo.getStationName().equalsIgnoreCase(eo.getStationName())) {
					if (stationVo.getStatus().equals(eo.isActive())) {
						throw new SchoolCodeAlreadyExistException(stationVo.getStationName() + " station name already present");
					}else {
						stationEo.setActive(stationVo.getStatus());
					}
				}
			}
		}
		stationEo.setStationName(stationVo.getStationName().toUpperCase());
		stationEo.setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		stationMasterRepo.save(stationEo);
	}

	@Transactional
	@Override
	public void updateRegionMaster(RegionMasterVo regionVo) {

		RegionEo regionEo = regionMasterRepo.findByRegionCode(regionVo.getRegionCode());
		List<RegionEo> eoData = regionMasterRepo.findAll();
		if (eoData.size() > 0) {
			for (RegionEo eo : eoData) {
				if (regionVo.getRegionName().equalsIgnoreCase(eo.getRegionName())) {
					if(regionVo.getStatus().equals(eo.isActive())) {
					throw new RegionNameAlreadyExistException(eo.getRegionName() + " region name is already present.");
				}else {
					regionEo.setActive(regionVo.getStatus());
				}
			}

			}
		}
		
		regionEo.setRegionName(regionVo.getRegionName().toUpperCase());
		regionEo.setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		regionMasterRepo.save(regionEo);
	}

	@Transactional
	@Override
	public void updateSchoolMaster(@Valid SchoolMasterUpdateReqVO reqVO) {
		SchoolMasterEO schoolEo = schoolMasterRepo.findById(reqVO.getId()).get();
		List<SchoolMasterEO> eoData = schoolMasterRepo.findAll();
		if (eoData.size() > 0) {
			for (SchoolMasterEO eo : eoData) {
				if (reqVO.getSchoolName().equalsIgnoreCase(eo.getSchoolName()) && reqVO.getShift().equalsIgnoreCase(eo.getShift())) {
					if(reqVO.getStatus().equals(eo.isSchoolStatus())) {
					throw new SchoolCodeAlreadyExistException(eo.getSchoolName() + " school name is already present.");
				}else {
					schoolEo.setSchoolStatus(reqVO.getStatus());
				}
			}

			}
		}
		
		schoolEo.setSchoolName(reqVO.getSchoolName().toUpperCase());
		schoolEo.setShift(reqVO.getShift().toUpperCase());
		schoolEo.setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		schoolMasterRepo.save(schoolEo);
	}

	@Transactional
	@Override
	public void updateStationCategoryMaster(@Valid CategoryMasterVo categoryVo) {
		List<CategoryEo> eoData = categoryMasterRepository.findAll();
		if (eoData.size() > 0) {
			for (CategoryEo eo : eoData) {
				if (categoryVo.getCategoryName().equalsIgnoreCase(eo.getCategoryName())) {
					throw new SchoolCodeAlreadyExistException(
							eo.getCategoryName() + " category name is already present.");
				}

			}
		}
		CategoryEo eo = categoryMasterRepository.findById(categoryVo.getId()).get();
		eo.setCategoryName(categoryVo.getCategoryName().toUpperCase());
		eo.setActive(categoryVo.getStatus());
		eo.setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		categoryMasterRepository.save(eo);
	}

	@Override
	public void saveStaffTypeMaster(@Valid StaffTypeMasterReqVO staffTypeMasterReqVO) {

		System.out.println("StaffTypeMasterReqVO ->" + staffTypeMasterReqVO.getStaffType());
		StaffTypeMasterEO staffTypeEO = new StaffTypeMasterEO();
		staffTypeEO.setStaffType(staffTypeMasterReqVO.getStaffType().toUpperCase());
		staffTypeEO.setStatus(staffTypeMasterReqVO.isStatus());
		staffTypeEO.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		staffTypeMasterRepository.save(staffTypeEO);
	}

	@Transactional
	@Override
	public void updateStaffTypeMaster(@Valid StaffTypeUpdateMasterReqVO staffTypeUpdateReqVO) {
		Optional<StaffTypeMasterEO> eo = staffTypeMasterRepository.findById(staffTypeUpdateReqVO.getId());
		eo.get().setStaffType(staffTypeUpdateReqVO.getStaffType());
		eo.get().setStatus(staffTypeUpdateReqVO.getStatus());
		eo.get().setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());

	}

	@Override
	public void saveDesignationMaster(@Valid DesignationMasterReqVO designationMasterReqVO) {
		DesignationMasterEO designationEO = new DesignationMasterEO();
		designationEO.setPostCode(designationMasterReqVO.getPostCode().toUpperCase());
		designationEO.setPostName(designationMasterReqVO.getPostName().toUpperCase());
		designationEO.setStatus(true);
		designationEO.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		designationMasterRepository.save(designationEO);
	}

	@Transactional
	@Override
	public void updateDesignationMaster(@Valid DesignationUpdateMasterReqVO designationUpdateReqVO) {
		Optional<DesignationMasterEO> designationMasterEO = designationMasterRepository
				.findById(designationUpdateReqVO.getId());
		designationMasterEO.get().setPostCode(designationUpdateReqVO.getPostCode().toUpperCase());
		designationMasterEO.get().setPostName(designationUpdateReqVO.getPostName().toUpperCase());
		designationMasterEO.get().setStatus(designationUpdateReqVO.getStatus());
		designationMasterEO.get().setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());
	}

	@Override
	public void saveSubjectMaster(@Valid SubjectMasterReqVO subjectMasterReqVO) {
		SubjectMasterEO subMasterEO = new SubjectMasterEO();
		subMasterEO.setSubjectCode(subjectMasterReqVO.getSubjectCode().toUpperCase());
		subMasterEO.setSubjectName(subjectMasterReqVO.getSubjectName().toUpperCase());
		// subMasterEO.setDescription(subjectMasterReqVO.getDescription());
		subMasterEO.setStatus(true);
		subMasterEO.setCreatedDate(UneecopsDateUtils.getCurrentTimeStamp());
		subjectMasterRepository.save(subMasterEO);
	}

	@Transactional
	@Override
	public void updateSubjectMaster(@Valid SubjectMasterUpdateReqVO subUpdateReqVO) {
		Optional<SubjectMasterEO> subMasterEO = subjectMasterRepository.findById(subUpdateReqVO.getId());
		subMasterEO.get().setSubjectCode(subUpdateReqVO.getSubjectCode().toUpperCase());
		subMasterEO.get().setSubjectName(subUpdateReqVO.getSubjectName().toUpperCase());
//		subMasterEO.get().setDescription(subUpdateReqVO.getDescription());
		subMasterEO.get().setStatus(subUpdateReqVO.getStatus());
		subMasterEO.get().setUpdatedDate(UneecopsDateUtils.getCurrentTimeStamp());

	}

}
