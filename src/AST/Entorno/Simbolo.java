/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Entorno;

import static AST.Entorno.Simbolo.Rol.*;

/**
 *
 * @author erick
 */
public class Simbolo 
{
    public String id;        
    public Tipo tipo;
    public Object valor;
    public enum Rol{VAR, FUNCION, METODO, OBJETO, CLASE, ARREGLO,CONSTRUCTOR};
    public Rol rol= VAR;
    public int linea, columna;     
    public int dimensiones;
    
    public Simbolo(Tipo t, String id, int dim, int l, int c)
    {
        this.tipo = t;
        this.id = id;
        this.dimensiones = dim;
        this.linea = l;
        this.columna = c;
    }
    
    public Simbolo()
    {
    }
    
    public Simbolo(Tipo t, String id, Object v )
    {
        this.id = id;
        this.tipo = t;
        this.valor = v;
        String c ="";       
        linea = 0 ; 
        columna = 0;
    }        

    public Simbolo(Tipo t, String id, Object v ,int dim, int l, int col)
    {
        this.id = id;
        this.tipo = t;
        this.valor = v;
        this.dimensiones = dim;
        this.rol = ARREGLO;
        this.linea = l;
        this.columna = col;
    }            
    
    public Simbolo(Tipo t, String id, Object v , int l, int col)
    {
        this.id = id;
        this.tipo = t;
        this.valor = v;
        String c ="";       
        this.linea = l;
        this.columna = col;
    }        
    
    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Object getValor() 
    {
        return valor;
    }

    public Simbolo.Rol getRol() {
        return rol;
    }
    
    public String getMessage()
    {
        return  dimensiones==0?    
                id +"\t"+tipo.nombreTipo()+"\t"+valor+"\t"+this.rol+"\t\t"+linea+"\t"+columna:
                id +"\t"+tipo.nombreTipo()+"\t"+valor+"\t"+this.rol+"\t"+dimensiones+"\t"+linea+"\t"+columna;
                
    }
    
    
    
    
}
