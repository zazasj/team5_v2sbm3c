package dev.mvc.favproduct;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("dev.mvc.favproduct.FavProductProc")
public class FavProductProc implements FavProductProcInter{

    
    
    
	@Autowired
	private FavProductDAOInter favproductDAO;
	
	@Override
	public int create(FavProductVO favproductVO) {
	  int cnt = this.favproductDAO.create(favproductVO);
	  return cnt;
	}
	 
	@Override
	public ArrayList<FavProductVO> list_by_memberno(int memberno) {
	  ArrayList<FavProductVO> list = this.favproductDAO.list_by_memberno(memberno);
	  return list;
	}
	
	@Override
	public int delete(int favID) {
	  int cnt = this.favproductDAO.delete(favID);
	  return cnt;
	}
	
	
	@Override
    public int checkIfFavorite(HashMap<String, Object> map) {
        return favproductDAO.checkIfFavorite(map);
    }
}
