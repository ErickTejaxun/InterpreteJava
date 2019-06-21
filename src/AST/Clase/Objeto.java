/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import static AST.Entorno.Simbolo.Rol.*;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Expresion.Funcion.Constructor;
import AST.Expresion.Funcion.DeclaracionAtributo;
import AST.Expresion.Funcion.Funcion;
import AST.Instruccion.Instruccion;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Objeto extends Simbolo implements Instruccion, Expresion
{    
    public ArrayList<Funcion> listaFunciones;
    public ArrayList<Constructor> listaConstructores;
    public ArrayList<DeclaracionAtributo> listaAtributos;
    public ArrayList<Atributo> atrib;
    public ArrayList<Objeto> listaClaseMiembros;
    public String padre;
    public ArrayList<String> listaHijos;
    public ArrayList<String> listaModificadores;    
    public String claseCreadora;
    public Entorno entornoObjeto;
         
    public Objeto ()
    {
        this.listaAtributos = new ArrayList<>();
        this.listaConstructores = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaClaseMiembros  = new ArrayList<>();
        this.padre = "";
        this.listaHijos  = new ArrayList<>();
        this.listaModificadores = new ArrayList<>();;       
        this.rol = CLASE;        
    }
        
    public Objeto(int l, int c)
    {
        this.listaAtributos = new ArrayList<>();
        this.listaConstructores = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaClaseMiembros  = new ArrayList<>();
        this.padre = "";
        this.listaHijos  = new ArrayList<>();
        this.listaModificadores = new ArrayList<>();;       
        this.linea = l;
        this.columna = c;
        this.rol = CLASE;
    }
    
    public Objeto(ArrayList<String> lmod, String nombre, String padre, ArrayList<Funcion> lf)
    {
        this.listaModificadores = lmod;
        this.id = nombre;
        this.padre = padre;
        this.listaFunciones = lf;
    }
        
    public void setId(String id)
    {
        this.id = id;
        this.tipo = new Tipo(id);
    }
    
    public void setListaFunciones(ArrayList<Funcion> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }

    public void setListaConstructores(ArrayList<Constructor> listaConstructores) {
        this.listaConstructores = listaConstructores;
    }

    public void setListaAtributos(ArrayList<DeclaracionAtributo> listaAtributos) {
        this.listaAtributos = listaAtributos;
    }

    public void setListaClaseMiembros(ArrayList<Objeto> listaClaseMiembros) {
        this.listaClaseMiembros = listaClaseMiembros;
    }

    public void setListaHijos(ArrayList<String> listaHijos) {
        this.listaHijos = listaHijos;
    }

    public void setModificadores(ArrayList<String> modificadores) {
        this.listaModificadores = modificadores;
    }
    
    public void setClaseOrigen(String clase)
    {
        this.claseCreadora = clase;
    }
    
    
    public void addAtributo(DeclaracionAtributo atrib)
    {
        this.listaAtributos.add(atrib);
    }
    public void addFuncion(Funcion f)
    {
        if(f instanceof Constructor)
        {
            this.listaConstructores.add((Constructor) f);
            return;
        }
        
        this.listaFunciones.add(f);      
    }
    public void addConstructor(Constructor c)
    {
        this.listaConstructores.add(c);
    }

    public void setPadre(String p)
    {
        this.padre = p;
    }

    @Override
    public Object ejectuar(Entorno entorno) 
    {
        /*Tenemos que ir a tomar todas las */
        
        entorno.insertar(this);
        return this;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

    @Override
    public Object getValor(Entorno entorno) 
    {        
        
        if(!padre.equals(""))
        {
            Simbolo simboloPadre = entorno.getGlobal().obtener(padre);
            if(simboloPadre==null)
            {
                Utilidades.Singlenton.registrarError(padre,"No se ha encontrado la clase padre.", ErrorC.TipoError.SEMANTICO, linea,columna);
            }
            else
            {
                if(simboloPadre instanceof Objeto)
                {
                    Objeto clasePadre = (Objeto)simboloPadre;
                    clasePadre.getValor(entorno);
                }
            }
        }
        
        /*Tenemos que crear un nuevo entorno parcial donde guardar las mierdas de clase*/
        for(Funcion f: this.listaFunciones)
        {
            /*Verificamos si hay que sobreescribir la funcion*/
            if(f.estaSobreescrito())
            {
                Simbolo fOriginal = entorno.getFuncion(f.id);
                if(fOriginal==null)
                {
                    Utilidades.Singlenton.registrarError(f.nombre,"No existe la función que se va a sobreescribir", ErrorC.TipoError.SEMANTICO, f.linea,f.columna);
                }
                else
                {
                    entorno.quitarSimbolo(f.id);
                    f.ejectuar(entorno);
                }
            } 
            else
            {
                f.ejectuar(entorno);
            }
        }  
        /*Declaracion de cada uno de los atributos*/
        for(DeclaracionAtributo dec: this.listaAtributos)
        {
            dec.ejectuar(entorno);
        }
        /*Ahora recorremos todos sus métodos para encontrar el principal*/
        for(Funcion f: this.listaFunciones)
        {
            if(f.isPrincipal())
            {                        
                return f.getValor(new Entorno(entorno,entorno.ventana));
            }
        }
        return null;
    }
    
}
