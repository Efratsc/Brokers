package com.example.androidprojct.models;

public class User {

        private int user_id;
        private int service_id;
        private String first_name;
        private String last_name;
        private String phone_number;
        private String kebele;
        private String woreda;
        private String email;

        private String username;
        private String user_password;


        public User() {
        }
    public User(String first_name, String last_name, String phone_number, String kebele, String woreda, String email, String username, String user_password) {

            this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.kebele = kebele;
        this.woreda = woreda;
        this.email = email;
        this.username = username;
        this.user_password = user_password;
    }

    public User(int user_id,String email,  String firstName, String phone) {
            this.user_id=user_id;
        this.first_name = first_name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public int getId() {
            return user_id;
        }

        public void setId(int id) {
            this.user_id = id;
        }

        public int getServiceId() {
            return service_id;
        }

        public void setServiceId(int serviceId) {
            this.service_id = serviceId;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getKebele() {
            return kebele;
        }

        public void setKebele(String kebele) {
            this.kebele = kebele;
        }

        public String getWoreda() {
            return woreda;
        }

        public void setWoreda(String woreda) {
            this.woreda = woreda;
        }

        /*public String getFamilyMember() {
            return familyMember;
        }

        public void setFamilyMember(String familyMember) {
            this.familyMember = familyMember;
        }*/

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

       /* public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }*/

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

}
