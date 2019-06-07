/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis;

/**
 *
 * @author erick
 */
public class lexema 
{
    public String tipo;
    public String valor;
    public int linea;
    public int columna;
    public String archivo;
    
    public lexema(String tipo, String valor, int linea, int columna)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }    
    public lexema(String tipo, String valor, String archivo, int linea, int columna)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.archivo = archivo;
    }    
    public String getMensaje()
    {
        return this.tipo +"\t"+ this.valor +"\t"+ this.linea +"\t"+ this.columna +"\t"+ this.archivo;
    }    
    public void imprimir()
    {
        System.out.println(getMensaje());
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
}
