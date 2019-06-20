/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;
import AST.Expresion.Variable;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class AccesoValor implements Expresion
{
    public int linea, columna;
    public Expresion origen;
    public String nombreAtributo;
    public Tipo tipo;
    public Expresion destino;
    
    public AccesoValor(Expresion d)
    {        
        if(d instanceof Acceso)
        {
            Acceso a = (Acceso)d;
            this.linea = a.linea;
            this.columna = a.columna;
            this.origen = a.origen;
            this.nombreAtributo = a.nombreAtributo;
            this.tipo = a.tipo;
            this.destino = a.destino;            
        }
    }
    
    public AccesoValor(Expresion e, String i, int l, int c)
    {
        this.origen = e;
        this.nombreAtributo = i;
        this.linea = l;
        this.columna = c;
    }
    
    public AccesoValor(Expresion e, Expresion i, int l, int c)
    {
        this.origen = e;
        this.destino = i;
        this.linea = l;
        this.columna = c;
    }    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object originData = origen.getValor(ent);
        if(originData instanceof Arreglo)
        {
            if(nombreAtributo.equals("length"))
            {
                Arreglo arr = (Arreglo)originData;
                return arr.getSizes();
            }
        } 
        else if(originData instanceof Objeto)
        {
            Objeto instancia = (Objeto)originData;
            //Simbolo atributo = instancia.entornoObjeto.obtener(nombreAtributo);
            Object valorAtributo =  null;
            if(destino instanceof Variable)
            {
                Variable var = (Variable)destino;
                valorAtributo = instancia.entornoObjeto.obtener(var.id);
            }                                   
            Simbolo atributo = null;            
            if(valorAtributo instanceof Simbolo){ atributo = (Simbolo)valorAtributo;}
            
            if(atributo==null)
            {
                Utilidades.Singlenton.registrarError(Utilidades.Singlenton.nombreVariable,"El atributo no pertenece a la clase "+instancia.claseCreadora, ErrorC.TipoError.SEMANTICO, linea,columna);
            }
            else
            {
                tipo = atributo.tipo;
                return atributo;
            }
            /*Buscamos el atributo*/
            
        }
        return null;
    }

    @Override
    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }
    
}
