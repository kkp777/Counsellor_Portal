package in.kkpit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.kkpit.dto.ViewEnqFilterRequest;
import in.kkpit.entity.Counsellor;
import in.kkpit.entity.Enquiry;
import in.kkpit.repo.CounsellorRepo;
import in.kkpit.repo.EntityRepo;
import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnqiryService{
	
	@Autowired
	EntityRepo enqRepo;
	
	@Autowired
	CounsellorRepo counsellorRepo;
	@Override
	public boolean addEnqiry(Enquiry enq, Integer CounsellorId)throws Exception {
		Counsellor couns=counsellorRepo.findById(CounsellorId).orElse(null);
		if(couns == null) {
			throw new Exception("No Counsellor Found");
		}
		enq.setCounsellor(couns);
		Enquiry save=enqRepo.save(enq);
		if(save.getEnqId()!=null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Enquiry> getAllEnqiry(Integer CounsellorId) {
		return enqRepo.getEnquriesByCounsellorId(CounsellorId);
	}

	@Override
	public List<Enquiry> getEnquiryWithFilter(ViewEnqFilterRequest filterReq, Integer CounsellorId) {
		//QBE Implementation(Dynamic Query Preparation)
		Enquiry enq=new Enquiry();
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		Counsellor c=counsellorRepo.findById(CounsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> of=Example.of(enq);
		List<Enquiry> enqList=enqRepo.findAll(of);
		
		return enqList;
	}

	@Override
	public Enquiry getEnquiryById(Integer EnqId) {
		return enqRepo.findById(EnqId).orElse(null);
		
	}

}
