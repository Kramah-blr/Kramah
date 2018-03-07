package com.example.anirban.kramah.superAdmin;

import java.util.ArrayList;

/**
 * Created by anirban on 21/2/18.
 */

public class ImageUploadInfo {
    public String groupName,grpID,grpEmail,grppass,grpphn,ownername,grprole;
    public String imageURL;
    public ArrayList<String> gname= new ArrayList<String>();

    public ImageUploadInfo() {

    }
    public ImageUploadInfo(String grp_name,String id,String Email,String password,String phone,String name,String role, String url) {
        this.groupName=grp_name;
        this.grpID=id;
        this.grpEmail=Email;
        this.grppass=password;
        this.grpphn=phone;
        this.ownername=name;
        this.grprole=role;
        this.imageURL= url;
    }
    public void setGroupName(String name){
        this.gname.add(name);
    }
    public ArrayList<String> getGrpName() {
        return gname;
    }
    public String getGroupName(){return groupName; }
    public String getGrpID(){return grpID;}
    public String getGrpEmail(){return grpEmail;}
    public String getGrppass(){return grppass;}
    public String getGrpphn(){return grpphn;}
    public String getOwnername(){return ownername;}
    public String getGrprole(){return grprole;}
    public String getImageURL() {
        return imageURL;
    }
}