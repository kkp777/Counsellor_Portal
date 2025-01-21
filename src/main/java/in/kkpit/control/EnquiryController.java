package in.kkpit.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.kkpit.dto.ViewEnqFilterRequest;
//import ch.qos.logback.core.model.Model;
import in.kkpit.entity.Enquiry;
import in.kkpit.service.EnqiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private EnqiryService enqService;
	
	@GetMapping("/enquiry")
	public String enquiryPage(Model model) {
		Enquiry enqObj=new Enquiry();
		model.addAttribute("enquiry",enqObj);
		return "enquiry";
	}
	
	@GetMapping("/editEnq")
	public String editEnq(@RequestParam("enqId") Integer enqId, Model model) {
	    Enquiry enq = enqService.getEnquiryById(enqId);
	    model.addAttribute("enquiry", enq);
	    return "enquiry";
	}

	
	@PostMapping("/filter-enqs")
	public String filterEnquries(ViewEnqFilterRequest viewEnqFilterRequest,HttpServletRequest request,Model model) {
		
		HttpSession session=request.getSession(false);
		Integer counsellorId=(Integer)session.getAttribute("counsellorId");
		
		List<Enquiry>allEnqList=enqService.getEnquiryWithFilter(viewEnqFilterRequest,counsellorId);
		model.addAttribute("enqiries",allEnqList);
		
		return "viewEnqPage";
	}
	
	@GetMapping("/viewEnqPage")
	public String viewEnwPage(HttpServletRequest req,Model model) {
		
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer)session.getAttribute("counsellorId");
		List<Enquiry> allEnqList=enqService.getAllEnqiry(counsellorId);
		model.addAttribute("enqiries",allEnqList);
		
		ViewEnqFilterRequest filterReq=new ViewEnqFilterRequest();
		model.addAttribute("viewEnqFilterRequest",filterReq);
		return "viewEnqPage";
	}
	
	@PostMapping("/addEnq")
	public String enquiryHandlePage(Enquiry enquiry,HttpServletRequest req,Model model)throws Exception {
		HttpSession session=req.getSession(false);
	Integer counsellorId=(Integer)session.getAttribute("counsellorId");
	boolean isSaved=enqService.addEnqiry(enquiry, counsellorId);
	if(isSaved) {
		model.addAttribute("smsg","Enquiry Added...");
	}
	else {
		model.addAttribute("emsg","Failed to Enquiry Added...");
	
	}
	Enquiry enqObj=new Enquiry();
	model.addAttribute("enquiry",enqObj);
		return "enquiry";
	}
}
