package com.jdxiang.airTicket.entity;

import java.util.List;

/**
 * 自定义选择座位表
 * 因为座位的行列数唯一
 * 构造函数中接收row 和 col
 * 生成对应的二维数组
 * 取初始化座位集合 将座位row设为一纬下表 col设为二纬下标
 * 获取时直接根据行列返回
 */
public class SelectSeat {

    Seat[][] totalSeat;

    public SelectSeat(int row, int col, List<Seat> initData) {
        this(row, col, initData.toArray(new Seat[initData.size()]));
    }

    public SelectSeat(int row, int col, Seat[] initData) {
        totalSeat = new Seat[row][col];
        for (Seat seat :
                initData) {
            totalSeat[seat.getLine() - 1][seat.getCol() - 1] = seat;
        }
    }


    public Seat findByRowAndCol(int row, int col) {
        return totalSeat[row - 1][col - 1];
    }


}
