package in.kkpit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.kkpit.entity.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor,Integer>{
	
	// select * from counsellor_tbl where email=:email
	@Query(value="select * from counsellor_tbl where email=:email",nativeQuery=true)
	public Counsellor findByemail(String email);
	
	
	@Query(value="select * from counsellor_tbl where email=:email and pwd=:pwd",nativeQuery=true)
	public Counsellor findByemailAndpwd(String email,String pwd);
}
