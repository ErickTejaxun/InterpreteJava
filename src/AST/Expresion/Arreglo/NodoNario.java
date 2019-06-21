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
public class NodoNario 
{
    public ArrayList<NodoNario> hijos;
    public Object valor;
    public Tipo tipo;
    public int linea, columna;
    
    public NodoNario()
    {
        
    }
    
    public NodoNario(Object v)
    {
        this.valor = v;
    }
    public NodoNario(ArrayList<NodoNario> l)
    {
        this.hijos = l;
    }
    
    public void addHijo(NodoNario hijo)
    {
        if(hijos == null)
        {
            hijos = new ArrayList<NodoNario>();
        }
        hijos.add(hijo);
    }
    
    public NodoNario generacion(Tipo tipo, Entorno entorno)
    {
        this.tipo = tipo;
        for(NodoNario hijo: hijos)
        {
            if(hijo.hijos==null)            
            {
                if(hijo.valor!=null)
                {
                    if(hijo.valor instanceof Expresion)
                    {
                        Expresion tmp = ((Expresion)hijo.valor);
                        hijo.valor = tmp.getValor(entorno);
                        if(!tmp.getTipo().nombreTipo().toLowerCase().equals(tipo.nombreTipo().toLowerCase()))
                        {
                            if(tipo.isNumeric() && tmp.getTipo().isNumeric())
                            {
                                switch(tipo.typeprimitive)
                                {
                                    case DOUBLE:
                                        Double d = tmp.getTipo().isDouble()? (Double)hijo.valor: (tmp.getTipo().isInt()? (int)hijo.valor:(char)hijo.valor);
                                        hijo.valor = d;
                                        break;
                                    case INT:
                                        if(tmp.getTipo().isDouble())
                                        {
                                            Utilidades.Singlenton.registrarError("Error de tipos", "No es posible convertir un valor DOUBLE a INT" , ErrorC.TipoError.SEMANTICO, tmp.linea(), tmp.columna());  
                                            return null;
                                        }
                                        int i = tmp.getTipo().isInt()? (int)hijo.valor:(char)hijo.valor;
                                        hijo.valor = i;
                                        break;
                                    case CHAR:
                                        if(!tmp.getTipo().isChar())
                                        {
                                            Utilidades.Singlenton.registrarError("Error de tipos", "No es posible convertir un valor "+tipo.nombreTipo()+" a CHAR." , ErrorC.TipoError.SEMANTICO, tmp.linea(), tmp.columna());  
                                            return null;                                        
                                        }
                                }
                            }
                            else
                            {
                                Utilidades.Singlenton.registrarError("Error de tipos", "Se espera un valor de tipo "+tipo.nombreTipo() +" y se ha enviado uno de tipo "+tmp.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, tmp.linea(), tmp.columna());  
                                return null;                            
                            }
                        }
                    }                    
                }
            }
            else
            {
               hijo.generacion(tipo, entorno); 
            }
        }
        return this;
    }
    
    
    public ArrayList<Integer> calcularDimensiones()
    {
        ArrayList<Integer> l = new ArrayList<Integer>();
        int dimensiones = this.tamanio();
        for(int i = 0 ; i < dimensiones ; i++)
        {
            l.add(0);
        }        
        return l;
    }
    public int tamanio()
    {
        if(isHoja())
        {
            return 0;
        }
        else
        {
            return hijos.get(0).tamanio() + 1;
        }
    }
    
    
    public NodoNario iniciar(ArrayList<Integer> lista, Tipo tipo,  int nivel)
    {
        if(nivel==lista.size())
        {
            switch(tipo.nombreTipo().toLowerCase())
            {
                case "int":
                    valor =0;
                    break;
                case "double":
                    valor = 0.0;
                    break;
                case "string":
                    valor = "";
                    break;
                case "char":
                    valor = ' ';
                    break;
                case "bool":
                    valor = false;
                    break;
                default:
                    valor = null;
                    break;
            }
            return this;
        }
        else
        {
            hijos = new ArrayList<>();
            for(int i = 0; i < lista.get(nivel); i++)
            {
                hijos.add(new NodoNario().iniciar(lista, tipo, nivel+1));
            }
        }
        return this;
    }        
    public boolean isHoja()
    {
        return hijos==null;
    }
    
    public String getCadena()
    {
        String cadena = "";
        if(isHoja())
        {
            if(valor==null)
            {
                return "nulo";
            }            
            return valor.toString();
        }
        else
        {
            cadena += "{";
            String cadenaTemp = "";
            for(NodoNario hijo:hijos)
            {
                if(cadenaTemp.equals(""))
                {
                    cadenaTemp += hijo.getCadena();
                }
                else
                {
                    cadenaTemp += ","+hijo.getCadena();
                }
            }
            cadena += cadenaTemp;
            cadena += "}";
        }
        return cadena;
    }

    public boolean setValor(ArrayList<Integer> l, int nivel, Object valor)
    {
        if(l.size()  == nivel)
        {
            this.valor  = valor;
            return true;
        }        
        else
        {
            NodoNario tmp = hijos.get(l.get(nivel));
            return tmp==null? true: tmp.setValor(l, nivel+1, valor);            
        }                
    }
    
    
    public Object getValor(ArrayList<Integer> l, int nivel)
    {
        if(l.size() == nivel)
        {
            if(isHoja())
            {                
                return this.valor;
            }
            else
            {             
                Arreglo tmp = new Arreglo(this);
                tmp.tipo = tipo;
                tmp.calcularDimensiones();
                return tmp;
            }
            
        }        
        else
        {   
            if(l.get(nivel)>hijos.size())
            {            
                Utilidades.Singlenton.registrarError("indice", "Error de desborde de índice de acceso al arreglo." , ErrorC.TipoError.SEMANTICO,
                        Utilidades.Singlenton.linea, Utilidades.Singlenton.columna);  
                return null;
            }
            NodoNario tmp = hijos.get(l.get(nivel));
            return tmp==null? null: tmp.getValor(l, nivel+1);
        }                
    }
    
    public int contarElementos()
    {
        if(isHoja())
        {
            return 1;
        }
        else
        {
            int numeroHijos = 0;
            for(NodoNario hijo: hijos)
            {
                numeroHijos += hijo.contarElementos();
            }
            return numeroHijos;

        }        
    }
    
    public ArrayList<NodoNario> getArrayLinealizado()
    {
        ArrayList<NodoNario> lista = new ArrayList<NodoNario>();
        if(isHoja())
        {
            lista.add(this);
            return lista;
        }
        else
        {
            for(NodoNario hijo: hijos)
            {
                ArrayList<NodoNario> tmp = hijo.getArrayLinealizado();
                for(NodoNario hijo1 : tmp)
                {
                    lista.add(hijo1);
                }
            }
        }
        return lista;
    }
}
