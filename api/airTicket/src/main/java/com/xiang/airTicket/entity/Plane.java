package com.xiang.airTicket.entity;

import com.xiang.airTicket.enumeration.PlaneType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 飞机实体
@Entity
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 名称

    private String icon; // 图标

    private PlaneType planeType = PlaneType.SMALL; // 飞机机型

    // 总列数
    private Integer totalCol;

    // 总行数
    private Integer totalRow;

    private Integer totalSeat;

    @ManyToOne
    private AirlineCompany airlineCompany; // 所属公司

    @OneToMany(cascade = CascadeType.ALL)
    private List<ShipSpace> shipSpaces = new ArrayList<>(); // 舱位集合

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public List<ShipSpace> getShipSpaces() {
        return shipSpaces;
    }

    public void setShipSpaces(List<ShipSpace> shipSpaces) {
        this.shipSpaces = shipSpaces;
    }

    public interface AirlineCompanyJsonView {}

    public interface ShipSpaceJsonView {}

    public Integer getTotalCol() {
        return totalCol;
    }

    public void setTotalCol(Integer totalCol) {
        this.totalCol = totalCol;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }


    public Integer getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(Integer totalSeat) {
        this.totalSeat = totalSeat;
    }
}
