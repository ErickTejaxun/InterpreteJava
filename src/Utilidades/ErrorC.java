/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 *
 * @author erick
 */
public class ErrorC 
{
    public ErrorC.TipoError tipo;
    public String descripcion;
    public int linea;
    public int columna;
    public String archivo;
    public String id;
    public enum TipoError{LEXICO, SINTACTICO, SEMANTICO};
    
    public ErrorC(ErrorC.TipoError tipo, String descripcion, int linea, int columna)
    {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }
    
    public ErrorC(String id, ErrorC.TipoError tipo, String descripcion, String archivo, int linea, int columna)
    {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
        this.archivo = archivo;
    }     
    
    public ErrorC(ErrorC.TipoError tipo, String descripcion, String archivo, int linea, int columna)
    {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
        this.archivo = archivo;
    }    
    
    public void imprimir()
    {        
        System.out.println(this.getMensaje());
    }
    public String getMensaje()
    {
        return this.id + "\t"+ this.tipo + "\t" + this.descripcion +"\t"+ linea +"\t"+ columna;
    }

    public void setTipo(ErrorC.TipoError tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public ErrorC.TipoError getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getArchivo() {
        return archivo;
    }                    
}
