package dzyxh;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.xs.common.Sql2WordUtil;

public class Sql2WordUtilTest extends BaseJunit4Test {
	
	@Resource(name="driimgHibernateTemplate")
	HibernateTemplate  driimgHibernateTemplate ;
	
	@Test  
	public void test() throws Exception {
		
		String sql ="select * from TM_DRIVING_PHOTO where id ='2c2881f158dd1d2f0158dd1ecac20025'";
		
		Sql2WordUtil.sql2WordUtilCase("\\2016最新机动车驾驶人身体条件证明.docx", sql, driimgHibernateTemplate, "tt.jpg");
		
	}

}
