package com.fin.test.dimin.Entity;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tcity")
public class tcity {
    @Id
    @GeneratedValue
    private Integer cityId;//编号
    private Integer provinceId;//年龄
    private String cityCode;//名字
    private String cityName;//名字

    public tcity() {
    }

    public tcity(Integer cityId, Integer provinceId, String cityCode, String cityName) {
        this.cityId=cityId;
        this.provinceId = provinceId;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
