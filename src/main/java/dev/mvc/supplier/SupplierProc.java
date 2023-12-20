package dev.mvc.supplier;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("dev.mvc.supplier.SupplierProc")
public class SupplierProc implements SupplierProcInter{

	  @Autowired
	  private SupplierDAOInter supplierDAO;
	
	  @Override
	  public int create(SupplierVO supplierVO) {
	    int cnt = this.supplierDAO.create(supplierVO);
	    return cnt;
	  }
	  
	  @Override
	  public ArrayList<SupplierVO> list_all_adminno(int adminno){
	    ArrayList<SupplierVO> list = this.supplierDAO.list_all_adminno(adminno);
	    return list;
	  }
	  
	  /**한창명 넣음
	   */
	  @Override
    public ArrayList<SupplierVO> list_all(){
      ArrayList<SupplierVO> list = this.supplierDAO.list_all();
      return list;
    }
	  
	  @Override
	  public SupplierVO read(int supplierid) {
	    SupplierVO supplierVO = this.supplierDAO.read(supplierid);
	    return supplierVO;
	  }
	  
	  @Override
	  public int delete(int supplierid) {
	    int cnt = this.supplierDAO.delete(supplierid);
	    return cnt;
	  }
	  
	  @Override
	  public int update(SupplierVO supplierVO) {
	    int cnt = this.supplierDAO.update(supplierVO);
	    return cnt;
	  }
}
