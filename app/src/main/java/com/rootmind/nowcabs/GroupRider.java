package com.rootmind.nowcabs;

import java.io.Serializable;

public class GroupRider implements Serializable {


    public String groupRefNo = null;
    public String groupID = null;
    public String riderRefNo = null;
    public String riderID = null;
    public String linkRiderRefNo = null;
    public String linkRiderID = null;
    public String publicView = null;
    public String status = null;

    public Rider rider=null;

    public Group group=null;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }





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

    public String getLinkRiderRefNo() {
        return linkRiderRefNo;
    }

    public void setLinkRiderRefNo(String linkRiderRefNo) {
        this.linkRiderRefNo = linkRiderRefNo;
    }

    public String getLinkRiderID() {
        return linkRiderID;
    }

    public void setLinkRiderID(String linkRiderID) {
        this.linkRiderID = linkRiderID;
    }

    public String getPublicView() {
        return publicView;
    }

    public void setPublicView(String publicView) {
        this.publicView = publicView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
