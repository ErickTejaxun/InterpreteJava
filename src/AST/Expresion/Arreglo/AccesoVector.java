/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Arreglo;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class AccesoVector implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public ArrayList<Expresion> listaExpresiones;    
    public Expresion origen;
    
    public AccesoVector(Expresion e, ArrayList<Expresion> list, int l, int c)
    {
       this.origen = e;
       this.listaExpresiones =list;
       this.linea =  l;
       this.columna = c;
    }
    
    @Override    
    public Object getValor(Entorno ent) 
    {        
        Object valor = origen.getValor(ent);
        if(valor!=null)
        {
            if(valor instanceof Arreglo)
            {
                Arreglo arregloActual = (Arreglo)valor;                
                tipo = arregloActual.getTipo();
                /*Comprobamos que coincidan las dimensiones*/                
                if(arregloActual.tamaniosDimensiones.size() < listaExpresiones.size())
                {
                    Utilidades.Singlenton.registrarError("Error de dimensiones", "El número de dimensiones no coincide con el valor del arreglo." , ErrorC.TipoError.SEMANTICO, linea, columna); 
                    return null;
                }
                ArrayList<Integer> coordenasActules = new ArrayList<Integer>();
                for(Expresion exp : listaExpresiones)
                {
                    Object resultado = exp.getValor(ent);
                    if(resultado==null){return null;};
                    if(exp.getTipo().isNumeric())
                    {
                        if(exp.getTipo().typeprimitive == INT)
                        {
                            coordenasActules.add((int)resultado);
                        }
                        else
                        if(exp.getTipo().typeprimitive == CHAR)
                        {
                            int aux = (char)resultado;
                            coordenasActules.add(aux);
                        }   
                        else
                        {                                                       
                            Utilidades.Singlenton.registrarError("Indice", "Se requiere un valor entero, no es posible convertir de double a int." , ErrorC.TipoError.SEMANTICO, exp.linea(), exp.columna());  
                        }
                    }
                    else
                    {
                        Utilidades.Singlenton.registrarError("indice", "El valor debe ser de tipo numerico." , ErrorC.TipoError.SEMANTICO, exp.linea(), exp.columna());  
                        return null;
                    }
                } 
                return arregloActual.getValor(coordenasActules);
            }
            else
            {
                Utilidades.Singlenton.registrarError("Error", "El valor no es un arreglo." , ErrorC.TipoError.SEMANTICO, linea, columna);  
            }
        }
        return null;
    }

    @Override
    public Tipo getTipo() 
    {
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
