/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis.Grafica;

import java.util.ArrayList;
import java.util.LinkedList;



/**
 *
 * @author erick
 */
public class NodoReporte 
{
    private String tipo;
    private String value;
    private int columna;
    private int linea;    
    private LinkedList<NodoReporte> hijos = new LinkedList();

    public NodoReporte(String tipo, String valor, LinkedList<NodoReporte> lista, int l, int c)
    {
        this.tipo = tipo;
        this.value = valor;
        this.hijos = lista;
        this.linea = l;
        this.columna = c;
    }
    public NodoReporte(String tipo, String valor, int l, int c)
    {
        this.tipo = tipo;
        this.value = valor;        
        this.linea = l;
        this.columna = c;
    }  
    public NodoReporte(String tipo, int l, int c)
    {
        this.tipo = tipo;
        this.value = tipo;        
        this.linea = l;
        this.columna = c;
    }     
    
    public NodoReporte() 
    {
        this.value="";
    }
     
    
    public NodoReporte(String value) 
    {
        this.value=value;
        this.tipo = value;
    }
    public NodoReporte(String value, int id) 
    {
        this.value=value;
        this.linea = id;
        this.tipo=value;
    }
//    public NodoReporte(String value, int id, int x) 
//    {
//        this.value=value;
//        this.linea = id;
//        this.tipo=value;
//    }    
    public NodoReporte(String value, NodoReporte hijo) 
    {
        this.value=value;
        this.tipo=value;        
        this.hijos.add(hijo);
    }    
    
    public NodoReporte(String tipo, int id, String value) 
    {
        this.tipo=tipo;
        this.value=value;
        this.linea = id;
    }    

    public String getTipo() {
        return tipo;
    }

    public String getValue() {
        return value;
    }

    public int getColumna() {
        return columna;
    }

    public int getLinea() {
        return linea;
    }

    public LinkedList<NodoReporte> getHijos() {
        return hijos;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setHijos(LinkedList<NodoReporte> hijos) {
        this.hijos = hijos;
    }

    public void pop(NodoReporte nuevo)
    {
        if(nuevo!=null)
        {
            this.hijos.add(nuevo);
        }
    }    
    
    public void add(NodoReporte nuevo)
    {
        if(nuevo!=null)
        {
            this.hijos.add(nuevo);
        }
    }
    
    public void addLista(NodoReporte nuevo)
    {
        if(nuevo!=null)
        {
            for(NodoReporte aux: nuevo.getHijos())
            {
                this.hijos.add(aux);
            }
        }
    }
    


    
    
}
