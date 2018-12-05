package com.example.duskagk.memotest;

public class Memo {

    private int _id;
    private String mname;
    private String mcon;

    public int get_id(){
        return _id;
    }
    public String getMname(){
        return mname;
    }
    public String getCon(){
        return mcon;
    }

    public void set_id(int id){
        this._id=id;
    }
    public void setMname(String name){
        this.mname=name;
    }
    public void setMcon(String mcon){
        this.mcon=mcon;
    }
}
