package com.xs.dzyxh.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.entity.vehimg.VehImg;
import com.xs.dzyxh.manager.IVehImgManager;

@Service("vehImgManager")
public class VehImgManagerImpl implements IVehImgManager {

	@Resource(name = "vehImgHibernateTemplate")
	private HibernateTemplate vehImgHibernateTemplate;

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate sysHibernateTemplate;

	@Override
	public void saveImg(VehImg vehImg) {
		vehImgHibernateTemplate.save(vehImg);
	}

	@Override
	public List<VehImg> getVehImgByLshOfzc(String lsh) {

		List<VehImg> imgs = (List<VehImg>) this.vehImgHibernateTemplate.find(
				"select new VehImg(id,lsh, ywlx,clsbdh, hphm,hpzl, zplx,pssj) from VehImg where lsh=? and zpzt=?", lsh,
				"0");

		return imgs;
	}

	@Override
	public void updateVehImgZpztOfzplx(String lsh, String zplx) {

		List<VehImg> imgs = (List<VehImg>) this.vehImgHibernateTemplate.find(
				"select new VehImg(id,lsh, ywlx,clsbdh, hphm,hpzl, zplx,pssj) from VehImg where lsh=? and zplx=?", lsh,
				zplx);

		for (VehImg img : imgs) {
			img.setZpzt("1");
			this.vehImgHibernateTemplate.update(img);
		}

	}

	@Override
	public List<BaseParams> getZpzl(String ywlx) {

		List<BaseParams> params = (List<BaseParams>) sysHibernateTemplate.find("from BaseParams where type=?", ywlx);

		return params;
	}

	@Override
	public VehImg getImgById(Integer id) {

		VehImg vehImg = this.vehImgHibernateTemplate.load(VehImg.class, id);

		Hibernate.initialize(vehImg);

		return vehImg;
	}

	@Override
	public List<Map<String,Object>> getYwListByClsbdh(final String clsbdh) {

		return  this.vehImgHibernateTemplate.execute(new HibernateCallback<List<Map<String,Object>>>() {
			@Override
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException {
				return session.createSQLQuery(
						"select max(hphm) as hphm ,max(hpzl) hpzl,clsbdh,max(ywlx) as ywlx,lsh,to_char(min(pssj),'yyyy-MM-dd') as pssj  from tm_veh_img where clsbdh=:clsbdh group by clsbdh,lsh")
						.setParameter("clsbdh", clsbdh).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}
		});
	}

}
