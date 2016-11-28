package com.xs.dzyxh.manager.driverimg;

import java.util.List;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;

public interface IDrivingPhotoManager {
	public DrivingPhoto getDrivingPhotoById(DrivingPhoto dri);
	public List<String> getDrivingPhotoIds(DrivingPhoto base,Integer page,Integer rows);
	public List<DrivingPhoto> getDrivingPhotos(final DrivingPhoto dir,final Integer page,final Integer rows);
}
