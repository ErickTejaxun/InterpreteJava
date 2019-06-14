/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Funcion;

import AST.Entorno.Tipo;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Constructor extends Funcion
{
    
    public Constructor(ArrayList<String> mod, String id, ArrayList<ParametroFormal> lpf, Instruccion bloque, int l, int c) {
        super(mod, new Tipo(id), id, lpf, l, c);
        this.instrucciones = bloque;
    }
    
    public Constructor(ArrayList<String> mod, String id, ArrayList<ParametroFormal> lpf, int l, int c) {
        super(mod, new Tipo(id), id, lpf, l, c);        
    }    
    
}
