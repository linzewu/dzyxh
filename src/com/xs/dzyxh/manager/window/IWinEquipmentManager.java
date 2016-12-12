package com.xs.dzyxh.manager.window;

import java.util.List;

import com.xs.dzyxh.entity.system.WinEquipment;

public interface IWinEquipmentManager {
	public List<WinEquipment> getWinEquipments(final WinEquipment win, final Integer page, final Integer rows) ;
	public Integer getWinEquipmentCount(final WinEquipment win);
	public void deleteWinEquipment(WinEquipment win) ;
	public WinEquipment saveWinEquipment(WinEquipment win);
	public WinEquipment getWinEquipment(String ip);
}
