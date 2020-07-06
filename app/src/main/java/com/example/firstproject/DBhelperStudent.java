package com.example.firstproject;

public class DBhelperStudent {
    public String Gender;
    public String Email;
    public String Phonenumber;
    public String studentClass;
    public String Address;
    public String Studentname;

    public DBhelperStudent() {
    }

    public DBhelperStudent(String gender, String email, String phonenumber, String studentClass, String address, String studentname) {

        Gender = gender;
        Email = email;
        Phonenumber = phonenumber;
        this.studentClass = studentClass;
        Address = address;
        Studentname = studentname;
    }

}