package com.example.androidprojct.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("transaction_id")
    private int transactionId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("amount")
    private double amount;

    @SerializedName("transaction_type")
    private String transactionType;

    // Constructor
    public Transaction(int transactionId, int userId, double amount, String transactionType) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}

