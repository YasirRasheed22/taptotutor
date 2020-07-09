package com.example.firstproject;

public class DBhelperStudent {
    public String ProfileImage;
    public String FullName;
    public String StudentClass;
    public String Address;
    public String Phonenumber;
    public String Email;
    public String Gender;

    public DBhelperStudent() {
    }

    public DBhelperStudent(String profileImage, String fullName, String studentClass, String address, String phonenumber, String email, String gender) {
        ProfileImage = profileImage;
        FullName = fullName;
        StudentClass = studentClass;
        Address = address;
        Phonenumber = phonenumber;
        Email = email;
        Gender = gender;
    }


}