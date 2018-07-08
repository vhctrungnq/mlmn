package controller ;
 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	
	  @RequestMapping("/hello")
	   public ModelAndView hello()
{
	 
	      return new   ModelAndView("hello"); 

	   }
	  @RequestMapping("/hello1")
	   public ModelAndView hello1()
{
	 
	      return new   ModelAndView("hello"); 

	   }
	  
	  
		
}

 