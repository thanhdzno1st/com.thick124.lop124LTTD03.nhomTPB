package com.thick124.lop124LTTD03.nhomTPB.Models;

public class User {
    private String id; // Khóa chính
    private String hoTen; // Họ và tên
    private String avatar; // Đường dẫn hoặc URL avatar
    private String gioiThieu; // Giới thiệu

    // Constructor không tham số
    public User() {
    }

    // Constructor đầy đủ
    public User(String id, String hoTen, String avatar, String gioiThieu) {
        this.id = id;
        this.hoTen = hoTen;
        this.avatar = avatar;
        this.gioiThieu = gioiThieu;
    }

    // Getter và Setter cho các thuộc tính
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    // Phương thức toString() để hiển thị thông tin
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", hoTen='" + hoTen + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gioiThieu='" + gioiThieu + '\'' +
                '}';
    }
}
