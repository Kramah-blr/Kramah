package com.example.anirban.kramah.groupadmin;

import java.util.ArrayList;

/**
 * Created by anirban on 2/3/18.
 */

public class group_admin_data {
    private String name;
    private String email,id,password,phn,role,url,ownername;
    private ArrayList<String> time = new ArrayList<String>();




    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getPhn() {
        return phn;
    }

    public String getRole() {
        return role;
    }
    public String getUrl() {
        return url;
    }
    public String getOwnername() {
        return ownername;
    }
    public ArrayList<String> getTime(){
        return time;
    }

    public void setName(String newName) {
        name=newName;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public  void setId(String newId) {
        id=newId;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public void setPhn(String phn) {
        this.phn=phn;
    }

    public void setRole(String role) {
        this.role=role;
    }
    public void setUrl(String url) {
        this.url=url;
    }
    public void setOwnername(String ownername) {
        this.ownername=ownername;
    }

    public void setTime(String time){
        this.time.add(time);
    }


}
