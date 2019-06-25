/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.INT;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;
import AST.Expresion.Variable;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Acceso implements Expresion
{
    public int linea, columna;
    public Expresion origen;
    public String nombreAtributo;
    public Tipo tipo;
    public Expresion destino;
    
    
    public Acceso(Expresion e, String i, int l, int c)
    {
        this.origen = e;
        this.nombreAtributo = i;
        this.linea = l;
        this.columna = c;
    }
    
    public Acceso(Expresion e, Expresion i, int l, int c)
    {
        this.origen = e;
        this.destino = i;
        this.linea = l;
        this.columna = c;
    }    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object originData = null;

        if(origen instanceof Variable)
        {
            Variable var = (Variable)origen;
            if(var.id.equals("this"))
            {
                Objeto obj = new Objeto();
                obj.entornoObjeto =ent.anterior;
                originData = obj;                    
            }
            else
            {
                originData = origen.getValor(ent);
            }            
        } 
        else
        {
            originData = origen.getValor(ent);
        }
        
        if(originData instanceof Arreglo)
        {
            if(nombreAtributo==null)
            {
                nombreAtributo ="";
                if(destino instanceof Variable)
                {
                    Variable var = (Variable)destino;
                    nombreAtributo = var.id;
                    tipo = var.tipo;
                }
            }
            if(nombreAtributo.equals("length"))
            {
                Arreglo arr = (Arreglo)originData;  
                tipo = new Tipo(INT);
                return arr.getSize();
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
                if(valorAtributo instanceof Objeto)
                { 
                    Objeto vObjeto = (Objeto)valorAtributo;
                    tipo = vObjeto.tipo;
                    return vObjeto;
                }
                if(valorAtributo instanceof Simbolo)
                {
                    Simbolo s = (Simbolo)valorAtributo;
                    tipo = s.tipo;
                    return  valorAtributo;
                }
                Utilidades.Singlenton.setVariable(var.id);
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
                return atributo.valor;
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
