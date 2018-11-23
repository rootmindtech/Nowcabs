package com.rootmind.nowcabs;

import java.io.Serializable;

public class Group implements Serializable {

    public String groupRefNo = null;
    public String groupID = null;
    public String riderRefNo = null;
    public String riderID = null;
    public String name = null;
    public String status=null;

    public String getGroupRefNo() {
        return groupRefNo;
    }

    public void setGroupRefNo(String groupRefNo) {
        this.groupRefNo = groupRefNo;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getRiderRefNo() {
        return riderRefNo;
    }

    public void setRiderRefNo(String riderRefNo) {
        this.riderRefNo = riderRefNo;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
