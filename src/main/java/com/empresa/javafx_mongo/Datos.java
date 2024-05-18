package com.empresa.javafx_mongo;

import java.util.Date;

public class Datos {
    private String _id;
    private String name;
    private String email;
    private String text;
    private Date date;

    public Datos(String _id, String name, String email, String text, Date date) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.text = text;
        this.date = date;
    }

    // getters and setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}