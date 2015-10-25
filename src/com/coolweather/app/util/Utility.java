package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {
	/**
	 * 解析和处理服务器放回的省级数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvincesStr = response.split(",");
			if (allProvincesStr != null && allProvincesStr.length > 0) {
				for (String provinceStr : allProvincesStr) {
					String[] array = provinceStr.split("\\|");
					Province province = new Province();
					province.setProvinceName(array[1]);
					province.setProvinceCode(array[0]);
					coolWeatherDB.saveProvince(province);
				}	
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 解析和处理服务器放回的市级数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCitiesStr = response.split(",");
			if (allCitiesStr != null && allCitiesStr.length > 0) {
				for (String cityStr : allCitiesStr) {
					String[] array = cityStr.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					coolWeatherDB.saveCity(city);
				}	
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 解析和处理服务器放回的县级数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCountiesStr = response.split(",");
			if (allCountiesStr != null && allCountiesStr.length > 0) {
				for (String CountyStr : allCountiesStr) {
					String[] array = CountyStr.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					coolWeatherDB.saveCounty(county);
				}	
			}
			return true;
		}
		return false;
	}
}
