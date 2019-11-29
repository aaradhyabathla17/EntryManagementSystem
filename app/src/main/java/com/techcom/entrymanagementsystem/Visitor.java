package com.techcom.entrymanagementsystem;

public class Visitor {
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    String Name;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    String Email;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    String Phone;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    String HostId;
    String time;
    public Visitor()
    {
    }
}
