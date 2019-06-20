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
        if(sim!=null)
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
            nuevaInstancia.setClaseOrigen(this.tipo.nombreTipo());
            nuevaInstancia.linea = this.linea;
            nuevaInstancia.columna = this.columna;
            nuevaInstancia.listaClaseMiembros = (ArrayList<Objeto>) clase.listaClaseMiembros.clone();
            nuevaInstancia.listaModificadores = (ArrayList<String>) clase.modificadores.clone();
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
