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
import AST.Nodo;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Clase extends Simbolo implements Instruccion, Expresion
{    
    public ArrayList<Funcion> listaFunciones;
    public ArrayList<Constructor> listaConstructores;
    public ArrayList<DeclaracionAtributo> listaAtributos;
    public ArrayList<Atributo> atrib;
    public ArrayList<Clase> listaClaseMiembros;
    public String padre;
    public ArrayList<String> listaHijos;
    public ArrayList<String> modificadores;    
         
    public Clase ()
    {
        this.listaAtributos = new ArrayList<>();
        this.listaConstructores = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaClaseMiembros  = new ArrayList<>();
        this.padre = "";
        this.listaHijos  = new ArrayList<>();
        this.modificadores = new ArrayList<>();;       
        this.rol = CLASE;        
    }
    
    
    public Clase(int l, int c)
    {
        this.listaAtributos = new ArrayList<>();
        this.listaConstructores = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaClaseMiembros  = new ArrayList<>();
        this.padre = "";
        this.listaHijos  = new ArrayList<>();
        this.modificadores = new ArrayList<>();;       
        this.linea = l;
        this.columna = c;
        this.rol = CLASE;
    }
    
    public Clase(ArrayList<String> lmod, String nombre, String padre, ArrayList<Funcion> lf)
    {
        this.modificadores = lmod;
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

    public void setListaClaseMiembros(ArrayList<Clase> listaClaseMiembros) {
        this.listaClaseMiembros = listaClaseMiembros;
    }

    public void setListaHijos(ArrayList<String> listaHijos) {
        this.listaHijos = listaHijos;
    }

    public void setModificadores(ArrayList<String> modificadores) {
        this.modificadores = modificadores;
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
    public Object getValor(Entorno ent) 
    {        
        /*Este metodo lo voy a utilizar para enviar un clon: método new*/
        return null;
    }
    
}
