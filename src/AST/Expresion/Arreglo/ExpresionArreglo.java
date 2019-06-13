/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Arreglo;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class ExpresionArreglo implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public ArrayList<Expresion> expresiones;
    public NodoNario raiz;
    
    public ExpresionArreglo(Tipo t, ArrayList<Expresion> e,int l, int c)
    {
        this.tipo =t;
        this.expresiones= e;
        this.linea = l;
        this.columna = c;
        
    }
    
    public ExpresionArreglo(NodoNario r, int l, int c)
    {
        this.raiz =r;
        this.linea = l;
        this.columna = c;
        
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        if(raiz!=null)
        {
            Arreglo arrTmp = new Arreglo(raiz.generacion(tipo, ent));
            arrTmp.raiz.generacion(tipo, ent);
            arrTmp.calcularDimensiones();
            return arrTmp;
        }
        else
        {
            ArrayList<Integer> tamanios = new ArrayList<Integer>();        
            for(Expresion e: expresiones)
            {
                Object tmp = e.getValor(ent);
                if(e.getTipo().isNumeric())
                {
                    switch(e.getTipo().typeprimitive)
                    {
                        case INT:
                                tamanios.add((int)tmp);
                            break;
                        case DOUBLE:
                                Utilidades.Singlenton.registrarError("Error en tamaños", "No es posible convertir de DOUBLE a int." , ErrorC.TipoError.SEMANTICO, linea, columna);  
                                return null;                        
                        case CHAR:
                                int auxiliar = (char)tmp;
                                tamanios.add(auxiliar);
                            break;
                    }
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Error en tamaños", "Los tamaños de cada dimensión debe ser de tipo entero." , ErrorC.TipoError.SEMANTICO, linea, columna);    
                    return null;
                }
            }        
            return new Arreglo(tamanios, tipo);              
        }        
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
