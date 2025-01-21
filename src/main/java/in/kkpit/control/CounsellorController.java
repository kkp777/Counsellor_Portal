package in.kkpit.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.kkpit.dto.DashboardResponse;
//import ch.qos.logback.core.model.Model;
import in.kkpit.entity.Counsellor;
import in.kkpit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	@Autowired	
	private CounsellorService counsellorService;
	
	@GetMapping("/")
	public String GoHome(Model model) {
		Counsellor cobj=new Counsellor();
		
		//Sending Data from controller to UI
		model.addAttribute("counsellor",cobj);
		
		//return view name
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String Dashboard(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession(false);
		Integer counsellorId=(Integer)session.getAttribute("counsellorId");
		
		DashboardResponse d=counsellorService.getDashboardResponse(counsellorId);
		model.addAttribute("dashboard",d);
			
		return "dashboard";
	}
	
	@PostMapping("/login")
	public String login(Counsellor counsellor,HttpServletRequest request,Model model) {
		Counsellor c=counsellorService.login(counsellor.getEmail(),counsellor.getPwd());
		if(c==null) {
			model.addAttribute("emsg","Invalid Credential");
			return "index";
		}
		else {
			
//			valid login store counsellor Id in seesion
			
			HttpSession session=request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellor_id());
			
			return "redirect:/dashboard";
		}
		
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor c=new Counsellor();
		
		//sending data from controoler to UI
		model.addAttribute("counsellor",c);
		return "register";
	}
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor,Model model) {
		
		Counsellor byEmail=counsellorService.findByEmail(counsellor.getEmail());
		if(byEmail != null) {
			model.addAttribute("emsg","Duplicate Email ...");
			return "register";
		}
		boolean isRegister=counsellorService.register(counsellor);
		if(isRegister) {
			//success
			model.addAttribute("smsg","Registration Successfull");
		}
		else {
			//error
			model.addAttribute("emsg","Registration Failed!...");
		}
		return "register";
	}
	
	@GetMapping("/logout")
	public String Logout(HttpServletRequest req) {
		
		//get Existing session invalidate it
		HttpSession session=req.getSession(false);
		session.invalidate();
		
		//Redirect to index page
		return "redirect:/";
		
	}
}
