package com.xs.dzyxh.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.Department;
import com.xs.dzyxh.manager.IDepartmentManager;

@Service("departmentManager")
public class DepartmentManagerImpl implements IDepartmentManager {
	
	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Department> getDepts() {
		DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
		dc.addOrder(Order.asc("seq"));
		List<Department> depts = (List<Department>) this.hibernateTemplate
				.findByCriteria(dc);
		return depts;
	}

	@Override
	public Department saveDept(Department dept) {
		
		return this.hibernateTemplate.merge(dept);
	}

	@Override
	public void deleteDept(Department dept) {
		
		Department d = getdeptByCode(dept.getBmdm());
		if(d!=null&&d.getId()!=null){
			this.hibernateTemplate.delete(d);
		}
		
	}

	@Override
	public Department getdeptByCode(String bmdm) {
		
		List<Department> depts =(List<Department>) this.hibernateTemplate.find("from Department where bmdm=?", bmdm);
		
		if(depts!=null&&!depts.isEmpty()){
			return depts.get(0);
		}
		return null;
	}
	
	@Override
	public List<String> getSubCodes(String bmdm) {
		
		List<String> codes=new ArrayList<String>();
		codes.add(bmdm);
		List<Department> depts =(List<Department>) this.hibernateTemplate.find("from Department");
		
		List<String> subCode = subCodes(depts,findSubDepts(depts,bmdm));
		
		codes.addAll(subCode);
		
		return codes;
	}
	
	private List<Department> findSubDepts(List<Department> depts,String bmdm){
		
		List<Department> subDepts=new ArrayList<Department>();
		
		for(Department dept : depts){
			if(bmdm.equals(dept.getSjbmdm())){
				subDepts.add(dept);
			}
		}
		
		return subDepts;
	}
	
	private List<String> subCodes(List<Department> allDepts,List<Department> subDepts){
		
		List<String> codes=new ArrayList<String>();
		
		for(Department dept : subDepts){
			codes.add(dept.getBmdm());
			List<Department> ds = findSubDepts(allDepts,dept.getBmdm());
			List<String> subCodes = subCodes(allDepts,ds);
			if(subCodes!=null){
				codes.addAll(subCodes);
			}
		}
		return codes;
	}
	
	
	
	

}
