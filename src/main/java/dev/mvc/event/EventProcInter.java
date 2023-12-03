package dev.mvc.event;

import java.util.ArrayList;

public interface EventProcInter {
	
	public int create(EventVO eventVO);

	  
	  public ArrayList<EventVO> list_all();
	  
	  
	  public EventVO read(int eventno);
	  
	  
	 
	  public int update_text(EventVO eventVO);

	  public int update_file(EventVO eventVO);
	 
	
	  public int delete(int eventno);

}
