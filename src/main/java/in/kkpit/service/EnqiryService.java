package in.kkpit.service;

import java.util.List;

import in.kkpit.dto.ViewEnqFilterRequest;
import in.kkpit.entity.Enquiry;
import jakarta.persistence.Entity;

public interface EnqiryService {
	public boolean addEnqiry(Enquiry enq,Integer CounsellorId )throws Exception;
	public List<Enquiry> getAllEnqiry(Integer CounsellorId);
	public List<Enquiry> getEnquiryWithFilter(ViewEnqFilterRequest filterReq,Integer CounsellorId);
	public Enquiry getEnquiryById(Integer EnqId);
}
