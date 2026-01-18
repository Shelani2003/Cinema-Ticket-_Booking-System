/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

public class ThreeDMovie extends Movie {

    private double extraCharge;

    public ThreeDMovie(String name, String date, String time, double extraCharge) {
        super(name, date, time); // inherit name, date, time
        this.extraCharge = extraCharge;
    }

    public double getExtraCharge() {
        return extraCharge;
    }

    @Override
    public String toString() {
        return super.getName() + " (3D) | Date: " + super.getDate() + " | Time: " + super.getTime()
               + " | Extra Charge: Rs." + extraCharge;
    }
}
