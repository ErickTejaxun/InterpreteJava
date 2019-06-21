/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Expresion.Funcion.Constructor;
import AST.Expresion.Funcion.ParametroFormal;
import AST.Expresion.Variable;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Instancia implements Expresion
{
    public Tipo tipo;
    public ArrayList<Expresion> parametrosActuales;    
    public int linea, columna;
    
    public Instancia(Tipo t, ArrayList<Expresion> lp, int l, int c)
    {
        this.tipo = t;
        this.parametrosActuales = lp;
        this.linea = l;
        this.columna = c;
    }
    
    public Instancia(Tipo t, int l, int c)
    {
        this.tipo = t;
        this.parametrosActuales = new ArrayList<>();
        this.linea = l;
        this.columna = c;
    }        
    
    @Override
    public Object getValor(Entorno entorno) 
    {
        /*Hay que buscar */
        Simbolo sim = entorno.getGlobal().obtener(this.tipo.nombreTipo());
        if(sim==null)
        {
            Utilidades.Singlenton.registrarError(this.tipo.nombreTipo(),"No se ha encontrado la clase.", ErrorC.TipoError.SEMANTICO, linea, columna);
            return null;
        }
        /*Verificamos que sea una clase*/
        if(sim instanceof Clase)
        {
            Clase clase = (Clase)sim;
            clase.entornoClase = new Entorno(entorno.getGlobalClase(),entorno.ventana);
            clase.getValor(clase.entornoClase);
            Objeto nuevaInstancia = new Objeto();
            nuevaInstancia.entornoObjeto = clase.entornoClase;
            nuevaInstancia.setClaseOrigen(this.tipo.nombreTipo());
            nuevaInstancia.linea = this.linea;
            nuevaInstancia.columna = this.columna;
            nuevaInstancia.listaClaseMiembros = (ArrayList<Objeto>) clase.listaClaseMiembros.clone();
            nuevaInstancia.listaModificadores = (ArrayList<String>) clase.modificadores.clone();
            nuevaInstancia.valor = nuevaInstancia;
            nuevaInstancia.tipo = new Tipo(clase.id);
            
            /*Ahora buscamos el contrusctor, si no hay constructor, mandamos un null ;) */
            String firma = clase.id;
            ArrayList<Object> resultados = new ArrayList<>();
            ArrayList<Tipo> tipos = new ArrayList<>()  ;         
            for(Expresion e:parametrosActuales)
            {
                if(e instanceof Variable)
                {
                    Variable var = (Variable)e;
                    if(entorno.obtener(var.id)==null)
                    {
                        Utilidades.Singlenton.registrarError(var.id, "Variable no declarada" , ErrorC.TipoError.SEMANTICO, var.linea, var.columna);
                        return null;
                    }
                    else
                    {
                        Object tmp = e.getValor(entorno); 
                        if(tmp!=null)
                        {
                            firma +="$"+e.getTipo().nombreTipo().toLowerCase();
                            resultados.add(tmp);
                        }                     
                    }

                }            
                else
                {
                    Object tmp = e.getValor(entorno); 
                    if(tmp!=null)
                    {
                        firma +="$"+e.getTipo().nombreTipo().toLowerCase();
                        resultados.add(tmp);
                    }                    
                }

            }      
            /*Buscamos la función*/            
            Simbolo sfuncion = nuevaInstancia.entornoObjeto.obtener(firma);
            if(sfuncion==null)
            {
                Utilidades.Singlenton.registrarError(clase.id, "No se ha encontrado el constructor solicitado "+firma , ErrorC.TipoError.SEMANTICO, linea, columna);               
                return null;
            }
            if(sfuncion instanceof Constructor)
            {
                Constructor constructor = (Constructor)sfuncion;
                int cont = 0;                
                for(Object item:resultados)
                {
                    /*String nombre = constructor.parametrosFormales.get(cont).id;
                    Simbolo s = nuevaInstancia.entornoObjeto.obtener(nombre);
                    s.valor = item;*/                    
                    ParametroFormal formal = constructor.parametrosFormales.get(cont);                    
                    formal.valor =item;
                    cont++;
                }                
                /*Cremos un nuevo entorno*/
                constructor.getValor(new Entorno(nuevaInstancia.entornoObjeto,entorno.ventana));
                
            }
            return nuevaInstancia;            
        }                
        return null;
    }

    @Override
    public Tipo getTipo() 
    {
        return tipo;
    }

    @Override
    public int linea() 
    {
        return linea;
    }

    @Override
    public int columna() 
    {
        return columna;
    }
    
}
