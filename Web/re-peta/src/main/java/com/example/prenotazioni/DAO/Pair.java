package com.example.prenotazioni.DAO;

public class Pair<D, C> {
    private D d;
    private C c;
    
    public Pair(D d, C c) {
        this.d = d;
        this.c = c;
    }

    public D getD() {
        return d;
    }

    public void setC(C c) {
        this.c = c;
    }

    public void setD(D d) {
        this.d = d;
    }


    public C getC() {
        return c;
    }

    @Override
    public String toString() {
        return "" + d + c;
    }
}
