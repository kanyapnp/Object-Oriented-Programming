package battleship;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.BacktrackingStrategy;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de resolver el problema del BattleShip con un CSP
 *
 * @author Yeferson Gaitan Gomez
 */
public class CSPBattleShip {
    
  private final List<Variable> cells = new ArrayList();
  private final Domain ships;
  private final Constraint sumRows;
  private final Constraint sumCols;
  
  private final CSP csp;
  
  /**
   * Constructor de la clase CSPBattleShip
   * 
   * @param sizeBoard entero indica el tamaño del tablero n x n
   * @param typeShips arreglo indica el tamaño de cada barco
   * @param row arreglo indica la suma total de cada fila
   * @param col arreglo indica la suma total de cada columna
   */
  public CSPBattleShip(int sizeBoard, int[] typeShips, int[] row, int[] col) {
    
    // Genero todas las variables del tablero -> Xi
    for (int i = 0; i < sizeBoard * sizeBoard; i++) {
      Variable Xi = new Variable("X_"+i);
      cells.add(Xi);
    }
    
    csp = new CSP(cells);
    
    // Genero el dominio para las variables
    // dominio = {0, 1, 2, 3, ... n} -> dominio[i] = id del barco
    int numShips = typeShips.length;
    List<Integer> domain = new ArrayList();
    for (int i = 0; i <= numShips; i++) domain.add(i);
    ships = new Domain(domain);
    
    // Configuro el dominio para todas la variables del tablero
    for (int i = 0; i < sizeBoard * sizeBoard; i++) {
      csp.setDomain(cells.get(i), ships);
    }
    
    sumRows = new TotalSumRowConstraint(cells, row);
    sumCols = new TotalSumColConstraint(cells, col);
    
    csp.addConstraint(sumRows);
    csp.addConstraint(sumCols);
    
  }
  
  public Assignment solver() {
    Assignment results = new BacktrackingStrategy().solve(csp);
    return results;
  }
    
}