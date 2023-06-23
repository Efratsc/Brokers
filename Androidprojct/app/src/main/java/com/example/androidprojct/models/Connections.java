package com.example.androidprojct.models;

public class Connections {
    private int id;
    private int userId1;
    private int userId2;
    private String connectionType;

    public Connections(int id, int userId1, int userId2, String connectionType) {
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.connectionType = connectionType;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
