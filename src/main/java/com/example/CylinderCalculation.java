/*
* File: CylinderCalculation.java
* Author: Vámosi László Ádám
* Copyright: 2025, Vámosi László Ádám
* Group: Szoft I-N
* Date: 2025-05-20
* GitHub: https://github.com/vamosilaszloadam/
* Licenc: MIT
*/

package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CylinderCalculation {
    private Double radius;
    private Double height;
    private Double surface;
    public CylinderCalculation() {
    }
    public CylinderCalculation(Double radius, Double height) {
        this.radius = radius;
        this.height = height;
    }
    public Double getRadius() {
        return radius;
    }
    public void setRadius(Double radius) {
        this.radius = radius;
    }
    public Double getHeight() {
        return height;
    }
    public void setHeight(Double height) {
        this.height = height;
    }
    public Double getSurface() {
        return surface;
    }
    public void setSurface(Double surface) {
        this.surface = surface;
    }
    public void calcSurfaceArea(){
        this.surface=2*Math.PI*Math.pow(radius,2)+2*Math.PI*radius*height;
        //this.surface=2*Math.PI*radius*(radius+height);
    }
    public void calcSurfaceArea(Double radius,Double height){
        this.surface=2*Math.PI*Math.pow(radius,2)+2*Math.PI*radius*height;
        //this.surface=2*Math.PI*radius*(radius+height);
    }
    public void roundSurfaceResult(){
        this.surface=(double)Math.round(this.surface);
    }
    public void roundSurfaceResult(Integer decimalPlaces){
        //this.surface=Math.round(this.surface*100.0)/100.0;
        //this.surface=Math.round(this.surface*Math.pow(10,decimalPlaces))/Math.pow(10,decimalPlaces);
        BigDecimal bd=new BigDecimal(this.surface);
        bd=bd.setScale(decimalPlaces,RoundingMode.DOWN);
        this.surface=bd.doubleValue();
    }
}
