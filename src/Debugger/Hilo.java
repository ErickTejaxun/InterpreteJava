/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Debugger;

import AST.AST;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Hilo implements Runnable
{
  public ArrayList<Integer> breakPoints;
  public AST programa;
    
  public Hilo(ArrayList<Integer> p, AST raiz)
  {
      this.breakPoints = p;
      this.programa = raiz;
  }
  @Override
  public void run()
  {      
      /*Ejecutamos */
      
  }
}
