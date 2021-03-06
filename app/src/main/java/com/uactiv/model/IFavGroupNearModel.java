package com.uactiv.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nirmal on 8/2/2016.
 */
public class IFavGroupNearModel implements Serializable {
    private String iduser;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String age;
    private String gender;
    private String latitude;
    private String longitude;
    private String image;
    private String about_yourself;
    private String badge;
    private String count;
    private String facebook_link;
    private String facebookid;
    private String created_on;
    private String radius_limit;
    private String gender_pref;
    private String isreceive_request;
    private String isreceive_notification;
    private String status;
    private String device_type;
    private String device_id;
    private String referral_code;
    private String rating;
    private String rated_count;
    private String user_type;
    private String challenge_count;
    private ArrayList<SkillsModel> skills;


    private String idgroup;
    private String groupname;
    private ArrayList<Members> members;


    private String fav;
    private String ischallenge_badge;
    private String distance;
    private String address;
    private String phone_no;
    private String invite_status;

    public String getInvite_status() {
        return invite_status;
    }

    public void setInvite_status(String invite_status) {
        this.invite_status = invite_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout_yourself() {
        return about_yourself;
    }

    public void setAbout_yourself(String about_yourself) {
        this.about_yourself = about_yourself;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getChallenge_count() {
        return challenge_count;
    }

    public void setChallenge_count(String challenge_count) {
        this.challenge_count = challenge_count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender_pref() {
        return gender_pref;
    }

    public void setGender_pref(String gender_pref) {
        this.gender_pref = gender_pref;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getIdgroup() {
        return idgroup;
    }

    public void setIdgroup(String idgroup) {
        this.idgroup = idgroup;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIschallenge_badge() {
        return ischallenge_badge;
    }

    public void setIschallenge_badge(String ischallenge_badge) {
        this.ischallenge_badge = ischallenge_badge;
    }

    public String getIsreceive_notification() {
        return isreceive_notification;
    }

    public void setIsreceive_notification(String isreceive_notification) {
        this.isreceive_notification = isreceive_notification;
    }

    public String getIsreceive_request() {
        return isreceive_request;
    }

    public void setIsreceive_request(String isreceive_request) {
        this.isreceive_request = isreceive_request;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Members> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Members> members) {
        this.members = members;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getRadius_limit() {
        return radius_limit;
    }

    public void setRadius_limit(String radius_limit) {
        this.radius_limit = radius_limit;
    }

    public String getRated_count() {
        return rated_count;
    }

    public void setRated_count(String rated_count) {
        this.rated_count = rated_count;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public ArrayList<SkillsModel> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SkillsModel> skills) {
        this.skills = skills;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    /***/
    public class Members implements Serializable {
        private String iduser;
        private String firstname;
        private String lastname;
        private String image;
        private String skills;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getIduser() {
            return iduser;
        }

        public void setIduser(String iduser) {
            this.iduser = iduser;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }
    }
}
