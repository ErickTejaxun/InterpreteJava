/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Arreglo;

import AST.Entorno.Tipo;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Arreglo 
{
    public NodoNario raiz;
    public Tipo tipo;
    public ArrayList<Integer> tamaniosDimensiones;
    public int linea, columna;
    
    public Arreglo(ArrayList<Integer> l , Tipo t)
    {
        this.tamaniosDimensiones = l;
        this.tipo = t;   
        raiz = new NodoNario().iniciar(l, tipo, 0);        
    }
    
    public int getSizes()
    {
        return 0;
    }
    
    public Arreglo(NodoNario r)
    {        
        this.raiz = r;
    }  
        
    public String getCadena()
    {
        return raiz.getCadena();
    }
    
    public Object getValor(ArrayList<Integer> l)
    {
        return raiz.getValor(l , 0);
    }
    
    public boolean setValor(ArrayList<Integer> l, Object valor)
    {        
        return raiz.setValor(l, 0, valor);
    }
    
    public Tipo getTipo()
    {
        return tipo;
    }
    public void calcularDimensiones()
    {        
        this.tamaniosDimensiones = raiz.calcularDimensiones();
    }
    
    public int getSize()
    {
        return raiz.contarElementos();
    }
    
    public ArrayList<NodoNario> getArrayLinealizado()
    {
        return raiz.getArrayLinealizado();
    }
    

}
