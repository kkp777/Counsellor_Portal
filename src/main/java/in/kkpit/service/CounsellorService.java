package in.kkpit.service;

import in.kkpit.dto.DashboardResponse;
import in.kkpit.entity.Counsellor;

public interface CounsellorService {
	public Counsellor findByEmail(String email);
	public boolean register(Counsellor counsellor);
	public Counsellor login(String Email,String pwd);
	public DashboardResponse getDashboardResponse(Integer counsellorId);
}
