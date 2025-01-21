package in.kkpit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kkpit.dto.DashboardResponse;
import in.kkpit.entity.Counsellor;
import in.kkpit.entity.Enquiry;
import in.kkpit.repo.CounsellorRepo;
import in.kkpit.repo.EntityRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService{
	
	@Autowired
	private CounsellorRepo counsellorRepo;
	
	@Autowired
	private EntityRepo enquiryRepo;

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor=counsellorRepo.save(counsellor);
		if(null != savedCounsellor.getCounsellor_id()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String Email, String pwd) {
	Counsellor counsellor=counsellorRepo.findByemailAndpwd(Email, pwd);
		return counsellor;
	}

	@Override
	public DashboardResponse getDashboardResponse(Integer counsellorId) {
		DashboardResponse response=new DashboardResponse();
		List<Enquiry> enqList=enquiryRepo.getEnquriesByCounsellorId(counsellorId);
		int totalEnq=enqList.size();
		
		int enrolledEnqs=enqList.stream()
								.filter(e->e.getEnqStatus().equals("Enrolled"))
								.collect(Collectors.toList())
								.size();
		int lostEnqs=enqList.stream()
				.filter(e->e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList())
				.size();
		int openEnqs=enqList.stream()
				.filter(e->e.getEnqStatus().equals("Open"))
				.collect(Collectors.toList())
				.size();
		
		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setLostEnqs(lostEnqs);
		response.setOpenEnqs(openEnqs);
		
		return response;
	}

	@Override
	public Counsellor findByEmail(String email) {
		
		return counsellorRepo.findByemail(email);
		
	}

}
