/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp;

import AST.Nodo;
import AST.Ent.*;

/**
 *
 * @author erick
 */
public interface Expresion extends Nodo
{       
    Object getValor(Entorno ent);
    Tipo getTipo();    
}
