/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejerc3arrays2dinformatica;

import Utilidades.Fecha;

/**
 *
 * @author dam
 */
public class Empleado {

    private String nombre;
    private Zona zona;
    private int estado;
    private Fecha fAlta;
    private float[] ventas = new float[6];

    
    public Empleado(String nombre, Zona zona, int estado, Fecha fAlta, float[] ventas) {
        this.nombre = nombre;
        this.zona = zona;
        this.estado = estado;
        this.fAlta = fAlta;
        this.ventas = ventas;
    }
    
    public Empleado(String nombre, Zona zona, int estado, Fecha fAlta) {
        this.nombre = nombre;
        this.zona = zona;
        this.estado = estado;
        this.fAlta = fAlta;
    }

    public String getNombre() {
        return nombre;
    }

    public Zona getZona() {
        return zona;
    }

    public int getEstado() {
        return estado;
    }

    public Fecha getfAlta() {
        return fAlta;
    }

    public float[] getVentas() {
        return ventas;
    }
    
    public void setVentaMes(int pos, float venta){
        ventas[pos] = venta;
    }
    
    public void setVentas(float[] ven){
        ventas = ven;
    }
    
    public float getVenta(int mes) {
        float ventas = 0;
        if (mes < this.ventas.length) {
            ventas = this.ventas[mes];
        }
        return ventas;
    }

    public float sumaVentas() {
        float suma = 0;
        for (int b = 0; b < ventas.length; b++) {
            suma += ventas[b];
        }
        return suma;
    }
    
    public float sueldoBruto(float sumaVentas) {
        float sueldo;
        sueldo = zona.getSueldo() + sumaVentas * 0.1f;
        int quin = fAlta.diffDiasHoy(fAlta) / (365*5);
        sueldo = sueldo + (quin * 10);
        return sueldo;
    }
}
