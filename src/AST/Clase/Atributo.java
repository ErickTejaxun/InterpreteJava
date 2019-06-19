/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Expresion.Expresion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Atributo extends Simbolo implements Expresion
{
    public ArrayList<String> modificadores;
    

    @Override
    public Object getValor(Entorno ent) 
    {
        
        return null;
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
