package com.ziasy.xmppchatapplication.database;

import com.ziasy.xmppchatapplication.User;

import java.util.Comparator;

public class EmployeeModel {
    private String employee_id, emaploye_name, employee_image, employee_device_id, employee_number, employee_user_group, employee_status, employee_window_status;

    public EmployeeModel(String employee_id, String emaploye_name, String employee_image, String employee_device_id, String employee_number, String employee_user_group, String employee_status, String employee_window_status) {
        this.employee_id = employee_id;
        this.emaploye_name = emaploye_name;
        this.employee_image = employee_image;
        this.employee_device_id = employee_device_id;
        this.employee_number = employee_number;
        this.employee_user_group = employee_user_group;
        this.employee_status = employee_status;
        this.employee_window_status = employee_window_status;
    }

    public String getEmployee_id() {

        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmaploye_name() {
        return emaploye_name;
    }

    public void setEmaploye_name(String emaploye_name) {
        this.emaploye_name = emaploye_name;
    }

    public String getEmployee_image() {
        return employee_image;
    }

    public void setEmployee_image(String employee_image) {
        this.employee_image = employee_image;
    }

    public String getEmployee_device_id() {
        return employee_device_id;
    }

    public void setEmployee_device_id(String employee_device_id) {
        this.employee_device_id = employee_device_id;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(String employee_number) {
        this.employee_number = employee_number;
    }

    public String getEmployee_user_group() {
        return employee_user_group;
    }

    public void setEmployee_user_group(String employee_user_group) {
        this.employee_user_group = employee_user_group;
    }

    public String getEmployee_status() {
        return employee_status;
    }

    public void setEmployee_status(String employee_status) {
        this.employee_status = employee_status;
    }

    public String getEmployee_window_status() {
        return employee_window_status;
    }

    public void setEmployee_window_status(String employee_window_status) {
        this.employee_window_status = employee_window_status;
    }
    public static Comparator<EmployeeModel> StuNameComparator = new Comparator<EmployeeModel>() {

        public int compare(EmployeeModel s1, EmployeeModel s2) {
            String StudentName1 = s1.getEmaploye_name().toUpperCase();
            String StudentName2 = s2.getEmaploye_name().toUpperCase();
            //ascending order
            //return StudentName1.compareTo(StudentName2);
            //descending order
            return StudentName1.compareTo(StudentName2);
        }
    };

}
