package com.jwy.bjtumidas.db;

public class PaiGoods extends Goods {
    private int gMaxPrice;
    private long gDeaLine;

    public int getgMaxPrice() {
        return gMaxPrice;
    }

    public void setgMaxPrice(int gMaxPrice) {
        this.gMaxPrice = gMaxPrice;
    }

    public long getgDeaLine() {
        return gDeaLine;
    }

    public void setgDeaLine(long gDeaLine) {
        this.gDeaLine = gDeaLine;
    }

    @Override
    public String toString() {
        return super.toString() + "  PaiGoods [gMaxPrice=" + gMaxPrice + ", gDeaLine=" + gDeaLine
                + "]";
    }

    public PaiGoods(int gMaxPrice, long gDeaLine) {
        super();
        this.gMaxPrice = gMaxPrice;
        this.gDeaLine = gDeaLine;
    }

    public PaiGoods() {
        super();
        // TODO Auto-generated constructor stub
    }

}
