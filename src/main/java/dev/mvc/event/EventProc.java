package dev.mvc.event;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.event.EventProc")
public class EventProc implements EventProcInter{
	
	@Autowired
	private EventDAOInter EventDAO;

	@Override
	public int create(EventVO eventVO) {
		// TODO Auto-generated method stub
		int cnt = this.EventDAO.create(eventVO);
		return cnt;
	}

	@Override
	public ArrayList<EventVO> list_all() {
		ArrayList<EventVO> list = this.EventDAO.list_all();
	    return list;
	}

	@Override
	public EventVO read(int eventno) {
		EventVO eventVO = this.EventDAO.read(eventno);
	    return eventVO;
	}

	@Override
	public int update_text(EventVO eventVO) {
		int cnt = this.EventDAO.update_text(eventVO);
		return cnt;
	}

	@Override
	public int update_file(EventVO eventVO) {
		int cnt = this.EventDAO.update_file(eventVO);
		return cnt;
	}

	@Override
	public int delete(int eventno) {
		int cnt = this.EventDAO.delete(eventno);
	    return cnt;
	}

}
