package com.xs.dzyxh.manager.tongan;

import com.xs.dzyxh.entity.tongan.TjUser;

public interface ITonGanManager {
	public void addData(Object obj);
	public TjUser getTjUser(final String xm,final String yymc);
}
