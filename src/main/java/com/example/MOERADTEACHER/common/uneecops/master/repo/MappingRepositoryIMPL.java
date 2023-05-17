package com.example.MOERADTEACHER.common.uneecops.master.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchListReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchResponseVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingResVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchReqVO;
import com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchResVO;
@Repository
public class MappingRepositoryIMPL implements MappingRepository{



	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<RegionStationMappingSearchResponseVO> searchRegionStationMappingList(RegionStationMappingSearchListReqVO reqVO, Pageable pageable) throws Exception {
		int startIndex = pageable.getPageNumber() * pageable.getPageSize();
		StringBuilder select = new StringBuilder("SELECT new com.example.MOERADTEACHER.common.uneecops.master.vo.RegionStationMappingSearchResponseVO(");
		select.append("ms.stationName,");
		select.append("mr.regionName,");
		select.append("rsm.stationCode,");
		select.append("rsm.regionCode, ");
		select.append("rsm.fromDate, ");
		select.append("rsm.toDate, ");
		select.append("rsm.isActive)");
		
		

		StringBuilder from = new StringBuilder();
		from.append("from RegionStationMappingEo rsm inner join RegionEo mr on rsm.regionCode=mr.regionCode inner  join StationEo ms on ms.stationCode=rsm.stationCode");

		String where = "";
		if (reqVO.getRegionName() != null) {
			where += " where mr.regionName =:regionName ";

		}
		if (reqVO.getStationCode() != null) {
			where += where.isEmpty() ? " where rsm.stationCode =:stationCode "
					: " and rsm.stationCode =:stationCode";
		}

		if (reqVO.getStationName() != null) {
			where += where.isEmpty() ? " where ms.stationName =:stationName "
					: " and ms.stationName =:stationName";
		}
		
		if (reqVO.getRegionCode()!=null) {
			where += where.isEmpty() ? " where mr.regionCode =:regionCode "
					: " and mr.regionCode =:regionCode";
		}
		
		select.append(from);
		select.append(where);
		
		select.append(" Order By rsm.createdDate desc ");
		
		
		 Query query = entityManager.createQuery(select.toString(), RegionStationMappingSearchResponseVO.class)
				.setFirstResult(startIndex).setMaxResults(pageable.getPageSize());
		
		String countStr = "Select count (rsm.id) As ct ";
		String qry = countStr + from + where;

		Query query1 = entityManager.createQuery(qry);

		if (reqVO.getRegionName() != null) {
			query.setParameter("regionName", reqVO.getRegionName());
			query1.setParameter("regionName", reqVO.getRegionName());
		}
		if (reqVO.getStationCode() != null) {
			query.setParameter("stationCode", reqVO.getStationCode());
			query1.setParameter("stationCode", reqVO.getStationCode());
		}
		if (reqVO.getStationName() != null) {
			query.setParameter("stationName", reqVO.getStationName());
			query1.setParameter("stationName", reqVO.getStationName());
		}
		if (reqVO.getRegionCode() != null) {
			query.setParameter("regionCode", reqVO.getRegionCode());
			query1.setParameter("regionCode", reqVO.getRegionCode());
		}
		
		List<RegionStationMappingSearchResponseVO> list = query.getResultList();
		List<Long> list2 = query1.getResultList();
		long count = list2.isEmpty() ? 0 : list2.get(0);
		
		
		return new PageImpl<RegionStationMappingSearchResponseVO>(list, pageable, count);
	}

	@Override
	public Page<StationCategoryMappingSearchResVO> searchStationCategoryMappingList(
			StationCategoryMappingSearchReqVO reqVO, Pageable pageable) throws Exception {
		int startIndex = pageable.getPageNumber() * pageable.getPageSize();
		StringBuilder select = new StringBuilder(
				"SELECT new com.example.MOERADTEACHER.common.uneecops.master.vo.StationCategoryMappingSearchResVO(");
		
		select.append("sm.stationName, ");
		select.append("sm.stationCode, ");
		select.append("cm.categoryName, ");
		select.append("scm.fromDate, ");
		select.append("scm.toDate, ");
		select.append("scm.status)");
		StringBuilder from = new StringBuilder();
		from.append(" from StationCategoryMappingEO scm inner join CategoryEo cm on scm.categoryId=cm.id inner  join StationEo sm on sm.stationCode=scm.stationCode ");

		String where = "";
		if (reqVO.getCategoryName() != null) {
			where += " where cm.categoryName =:categoryName ";

		}
		if (reqVO.getStationCode() != null) {
			where += where.isEmpty() ? " where scm.stationCode =:stationCode "
					: " and scm.stationCode =:stationCode";
		}
		if (reqVO.getStationName() != null) {
			where += where.isEmpty() ? " where sm.stationName =:stationName "
					: " and sm.stationName =:stationName";
		}
	
		select.append(from);
		select.append(where);
		
		select.append(" Order By scm.createdDate desc ");
		
		
		 Query query = entityManager.createQuery(select.toString(), StationCategoryMappingSearchResVO.class)
				.setFirstResult(startIndex).setMaxResults(pageable.getPageSize());
		
		String countStr = "Select count (scm.id) As ct ";
		String qry = countStr + from + where;

		Query query1 = entityManager.createQuery(qry);

		if (reqVO.getCategoryName() != null) {
			query.setParameter("categoryName", reqVO.getCategoryName());
			query1.setParameter("categoryName", reqVO.getCategoryName());
		}
		if (reqVO.getStationCode() != null) {
			query.setParameter("stationCode", reqVO.getStationCode());
			query1.setParameter("stationCode", reqVO.getStationCode());
		}
		if (reqVO.getStationName() != null) {
			query.setParameter("stationName", reqVO.getStationName());
			query1.setParameter("stationName", reqVO.getStationName());
		}
		
		List<StationCategoryMappingSearchResVO> list = query.getResultList();
		List<Long> list2 = query1.getResultList();
		long count = list2.isEmpty() ? 0 : list2.get(0);
		return new PageImpl<StationCategoryMappingSearchResVO>(list, pageable, count);
	}

	@Override
	public Page<SchoolStationMappingSearchResVO> searchSchoolStationMappingList(SchoolStationMappingSearchReqVO reqVO,
			Pageable pageable) {
		int startIndex = pageable.getPageNumber() * pageable.getPageSize();
		StringBuilder select = new StringBuilder(
				"SELECT new com.example.MOERADTEACHER.common.uneecops.master.vo.SchoolStationMappingSearchResVO(");
		
		select.append("sm.schoolName, ");
		select.append("sm.shift, ");
		select.append("ms.stationName, ");
		select.append("ssm.stationCode, ");
		select.append("ssm.schoolCode, ");
		select.append("ssm.fromDate, ");
		select.append("ssm.toDate, ");
		select.append("ssm.isActive)");
	

		StringBuilder from = new StringBuilder();
		from.append("from SchoolStationMappingEo ssm  inner join StationEo ms on ssm.stationCode=ms.stationCode inner join SchoolMasterEO sm on sm.schoolCode=ssm.schoolCode");

		String where = "";
		if (reqVO.getStationName() != null) {
			where += " where ms.stationName =:stationName ";

		}
		if (reqVO.getStationCode() != null) {
			where += where.isEmpty() ? " where ssm.stationCode =:stationCode "
					: " and ssm.stationCode =:stationCode";
		}
		if (reqVO.getSchoolName() != null) {
			where += where.isEmpty() ? " where sm.schoolName =:schoolName "
					: " and sm.schoolName =:schoolName";
		}

		
		select.append(from);
		select.append(where);
		
		select.append(" Order By ssm.createdDate desc ");
		
		
		 Query query = entityManager.createQuery(select.toString(), SchoolStationMappingSearchResVO.class)
				.setFirstResult(startIndex).setMaxResults(pageable.getPageSize());
		
		String countStr = "Select count (ssm.id) As ct ";
		String qry = countStr + from + where;

		Query query1 = entityManager.createQuery(qry);

		if (reqVO.getStationName() != null) {
			query.setParameter("stationName", reqVO.getStationName());
			query1.setParameter("stationName", reqVO.getStationName());
		}
		if (reqVO.getStationCode() != null) {
			query.setParameter("stationCode", reqVO.getStationCode());
			query1.setParameter("stationCode", reqVO.getStationCode());
		}
		if (reqVO.getSchoolName() != null) {
			query.setParameter("schoolName", reqVO.getSchoolName());
			query1.setParameter("schoolName", reqVO.getSchoolName());
		}
		
		
		List<SchoolStationMappingSearchResVO> list = query.getResultList();
		List<Long> list2 = query1.getResultList();
		long count = list2.isEmpty() ? 0 : list2.get(0);
		
		
		return new PageImpl<SchoolStationMappingSearchResVO>(list, pageable, count);

	}

	@Override
	public Page<StaffTypePostMappingResVO> searchStaffTypePostMappingList(StaffTypePostMappingSearchReqVO reqVO,
			Pageable pageable) {
		int startIndex = pageable.getPageNumber() * pageable.getPageSize();
		StringBuilder select = new StringBuilder(
				"SELECT new com.example.MOERADTEACHER.common.uneecops.master.vo.StaffTypePostMappingResVO(");
		
		select.append("st.staffType, ");
		select.append("d.postCode, ");
		select.append("d.postName, ");
		select.append("stp.status)");
	

		StringBuilder from = new StringBuilder();
		from.append("from StaffTypePostMappingEO stp  inner join StaffTypeMasterEO st on stp.staffTypeId=st.id inner join DesignationMasterEO d on d.id=stp.designationId");

		String where = "";
		if (reqVO.getStaffType() != null) {
			where += " where st.staffType =:staffType ";

		}
		if (reqVO.getPostCode() != null) {
			where += where.isEmpty() ? " where d.postCode =:postCode "
					: " and d.postCode =:postCode";
		}
		if (reqVO.getPostName() != null) {
			where += where.isEmpty() ? " where d.postName =:postName "
					: " and d.postName =:postName";
		}

		
		select.append(from);
		select.append(where);
		
		select.append(" Order By stp.createdDate desc ");
		
		
		 Query query = entityManager.createQuery(select.toString(), StaffTypePostMappingResVO.class)
				.setFirstResult(startIndex).setMaxResults(pageable.getPageSize());
		
		String countStr = "Select count (stp.id) As ct ";
		String qry = countStr + from + where;

		Query query1 = entityManager.createQuery(qry);

		if (reqVO.getStaffType() != null) {
			query.setParameter("staffType", reqVO.getStaffType());
			query1.setParameter("staffType", reqVO.getStaffType());
		}
		if (reqVO.getPostCode() != null) {
			query.setParameter("postCode", reqVO.getPostCode());
			query1.setParameter("postCode", reqVO.getPostCode());
		}
		if (reqVO.getPostName() != null) {
			query.setParameter("postName", reqVO.getPostName());
			query1.setParameter("postName", reqVO.getPostName());
		}
		
		
		List<StaffTypePostMappingResVO> list = query.getResultList();
		System.out.println("list"+ list.size());
		List<Long> list2 = query1.getResultList();
		long count = list2.isEmpty() ? 0 : list2.get(0);
		
		
		return new PageImpl<StaffTypePostMappingResVO>(list, pageable, count);

	
		
	}
	
}
