package com.chelun.soa.entity;

public class CityEntity {
	private int id;
	private int pid;
	private String name;
	private String citycode;
	private String adcode;
	private int level;
	private float lng;
	private float lat;
	private int is_open;
	private int cjjl_id;
	private int old_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getAdcode() {
		return adcode;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public int getIs_open() {
		return is_open;
	}
	public void setIs_open(int is_open) {
		this.is_open = is_open;
	}
	public int getCjjl_id() {
		return cjjl_id;
	}
	public void setCjjl_id(int cjjl_id) {
		this.cjjl_id = cjjl_id;
	}
	public int getOld_id() {
		return old_id;
	}
	public void setOld_id(int old_id) {
		this.old_id = old_id;
	}
	@Override
	public String toString() {
		return "CityEntity [id=" + id + ", pid=" + pid + ", name=" + name + ", citycode=" + citycode + ", adcode="
				+ adcode + ", level=" + level + ", lng=" + lng + ", lat=" + lat + ", is_open=" + is_open + ", cjjl_id="
				+ cjjl_id + ", old_id=" + old_id + "]";
	}
}
