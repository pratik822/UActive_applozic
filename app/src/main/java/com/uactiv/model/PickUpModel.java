package com.uactiv.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PickUpModel implements Serializable{

	private String activityname = null;
	private ArrayList<PickUpCategory> pickUpCategoryList = null;

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public ArrayList<PickUpCategory> getPickUpCategoryList() {
		return pickUpCategoryList;
	}

	public void setPickUpCategoryList(ArrayList<PickUpCategory> pickUpCategoryList) {
		this.pickUpCategoryList = pickUpCategoryList;
	}
}
