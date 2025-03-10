package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private int role;
    private boolean status;
    private String country;

    public User() {
    }

    public User(String username, String email, String country, String role, boolean status, String password) {
        this.username = username;
        this.email = email;
        this.country = country;
        this.role = "admin".equals(role) ? 1 : 0;
        this.status = status;
        this.password = password;
    }

    public User(int id, String username, String email, String country, String role, boolean status, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.country = country;
        this.role = "admin".equals(role) ? 1 : 0;
        this.status = status;
        this.password = password;
    }
    
    public User(int id, String username, String password, String fullname, String email, String phone, String address, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = true; // Default status is active
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getRole() {
        return role;
    }
    
    public void setRole(int role) {
        this.role = role;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public boolean isAdmin() {
    return role == 1;
}
    
    public boolean isUser() {
    return role == 0;
}
}