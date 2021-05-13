package it.unibo.ai.didattica.competition.tablut.domain;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Scores {
    private int[][] kingPawn;
    private int[][] whitePawns;
    private int[][] blackPawns;
    //private int[][] blackPawnsDefense;
    private int[][] blackPawnsAttack;
    private List<int[]> exits;
    public boolean canEscape = false;
    public boolean destra = false;
    public boolean sinistra = false;
    public boolean sopra = false;
    public boolean sotto = false;
    private static final int PUNTEGGIO_ALTO = 5;
    private static final int PUNTEGGIO_MEDIO = 3;

    public Scores(){
        exits = new ArrayList<>();

        kingPawn = new int[][]{
            {  0, 10, 10, 0, 0, 0, 10, 10, 0 },
             { 10, 3, 3, 1, 0, 1, 3, 3, 10 },
             { 10, 3, 3, 3, 2, 3, 3, 3, 10 },
              { 0, 1, 3, 2, 2, 2, 3, 1, 0 },
              { 0, 0, 2, 2, 0, 2, 2, 0, 0 },
              { 0, 1, 3, 2, 2, 2, 3, 1, 0 },
             { 10, 3, 3, 3, 2, 3, 3, 3, 10 },
             { 10, 3, 3, 1, 0, 1, 3, 3, 10 },
            { 0, 10, 10, 0, 0, 0, 10, 10, 0 }
        };
        
        whitePawns = new int[][]{
            { 0, 1, 1, 0, 0, 0, 1, 1, 0 },
            { 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 1, 1, 0, 1, 1, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            { 0, 1, 1, 0, 0, 0, 1, 1, 0 }
       };
       
        blackPawns = new int[][]{
            { 0, 1, 1, 0, 0, 0, 1, 1, 0 },
            { 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 1, 1, 0, 1, 1, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            { 0, 1, 1, 0, 0, 0, 1, 1, 0 }
        };
        
        /*blackPawnsDefense = new int[][]{
            { 0, 5, 5, 0, 0, 0, 5, 5, 0 },
            { 5, 4, 4, 0, 0, 0, 4, 4, 5 },
            { 5, 4, 0, 0, 0, 0, 0, 4, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 4, 0, 0, 0, 0, 0, 4, 5 },
            { 5, 4, 4, 0, 0, 0, 4, 4, 5 },
            { 0, 5, 5, 0, 0, 0, 5, 5, 0 }
        };*/

        // it's updated according to the king's position
        blackPawnsAttack = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
    }

    // IMPROVE THESE:
    public int getBlackPawnsScore(int[] pos, int[] king, List<int[]> emptyCells){
        int total = blackPawns[pos[1]][pos[0]] + (!canEscape ? blackPawnsAttack[pos[1]][pos[0]] : 0); // matrice di attacco
        total += blockEscape(pos, king)*2; // copertura uscite

        return total;
    }

    private int blockEscape(int[]pos, int[] king){
        if(canEscape){
            // uses exits
            for(int[] exit : exits){
                if(exit[0] == 0){ // riga 1
                    if(pos[1] == king[1] && pos[0] < king[0]){
                        return 5;
                    }
                }
                if(exit[0] == 8){ // riga 9
                    if(pos[1] == king[1] && pos[0] > king[0]){
                        return 5;
                    }
                }
                if(exit[1] == 0){ // colonna 1
                    if(pos[0] == king[0] && pos[1] < king[1]){
                        return 5;
                    }
                }
                if(exit[1] == 8){ // colonna 9
                    if(pos[0] == king[0] && pos[1] > king[1]){
                        return 5;
                    }
                }
            }
        }
        return 0;
    }

    public void updateAttackMatrix(int[] king){

        // init
        for(int[] row : blackPawnsAttack) Arrays.fill(row, 0);

        if(king[1] >0 && king[1] <8 && king[0] >0 && king[0] <8){
            // croce (+) 1
            blackPawnsAttack[king[1] + 1][king[0]] = 4;
            blackPawnsAttack[king[1] - 1][king[0]] = 4;
            blackPawnsAttack[king[1]][king[0] + 1] = 4;
            blackPawnsAttack[king[1]][king[0] - 1] = 4;
            // diagonali 1
            blackPawnsAttack[king[1] + 1][king[0] + 1] = 2;
            blackPawnsAttack[king[1] - 1][king[0] + 1] = 2;
            blackPawnsAttack[king[1] - 1][king[0] - 1] = 2;
            blackPawnsAttack[king[1] + 1][king[0] - 1] = 2;
        }
        if(king[1] >1 && king[1] <7 && king[0] >1 && king[0] <7){
            // croce (+) 2
            blackPawnsAttack[king[1] + 2][king[0]] = 2;
            blackPawnsAttack[king[1] - 2][king[0]] = 2;
            blackPawnsAttack[king[1]][king[0] + 2] = 2;
            blackPawnsAttack[king[1]][king[0] - 2] = 2;
            // cavalli
            blackPawnsAttack[king[1] + 2][king[0] + 1] = 1;
            blackPawnsAttack[king[1] - 2][king[0] + 1] = 1;
            blackPawnsAttack[king[1] - 2][king[0] - 1] = 1;
            blackPawnsAttack[king[1] + 2][king[0] - 1] = 1;
            blackPawnsAttack[king[1] + 1][king[0] + 2] = 1;
            blackPawnsAttack[king[1] - 1][king[0] + 2] = 1;
            blackPawnsAttack[king[1] - 1][king[0] - 2] = 1;
            blackPawnsAttack[king[1] + 1][king[0] - 2] = 1;
        }
    }

    public void canEscape(int[] king, List<int[]> emptyCells){
        
        for(int i = king[0]+1; i<9; i++){ // verso destra
            if(!contiene(emptyCells, new int[]{i, king[1]})) break;
            if(i==8 && ( king[1]==1 || king[1]==2 || king[1]==6 || king[1]==7 )) exits.add(new int[]{ i, king[1] });
        }
        for(int i = king[0]-1; i>=0; i--){ // verso sinistra
            if(!contiene(emptyCells, new int[]{i, king[1]})) break;
            if(i==1 && ( king[1]==1 || king[1]==2 || king[1]==6 || king[1]==7 )) exits.add(new int[]{ i, king[1] });
        }
        for(int j = king[1]+1; j<9; j++){ // verso basso
            if(!contiene(emptyCells, new int[]{king[0], j})) break;
            if(j==8 && ( king[0]==1 || king[0]==2 || king[0]==6 || king[0]==7 )) exits.add(new int[]{ king[0], j });
        }
        for(int j = king[1]-1; j>=0; j--){ // verso alto
            if(!contiene(emptyCells, new int[]{king[0], j})) break;
            if(j==0 && ( king[0]==1 || king[0]==2 || king[0]==6 || king[0]==7 )) exits.add(new int[]{ king[0], j });
        }

        canEscape = (exits.isEmpty() ? false : true);

        exits.clear();
    }

    private boolean contiene(List<int[]> empty, int[] x){
        for(int[] el : empty){
            if(Arrays.equals(el, x)) return true;
        }
        return false;
    }

    public int getKingScore(int[] pos){
        int total = kingPawn[pos[1]][pos[0]]; // punteggio dalla matrice

        return total;
    }

    public int getWhitePawnsScore(int[] pos, int[] king, List<int[]> positionsB, State state){
        int total = whitePawns[pos[1]][pos[0]];
        
        // punti su diagonali 1 del re per proteggerlo
        total += underAttack(pos, king, state);

        return total;
    }

    private int underAttack(int[] pos,int[] king, State state){
        //int basso = 1;

        // DESTRA
        if(destra){
            // distanza 2 dal re
            if(pos[0] == king[0] && pos[1] == king[1]+2){
                return PUNTEGGIO_ALTO;
            }
            // diagonali attorno al nero
            if(pos[1] == king[1]-1 && pos[0] == king[0]+1 ||
            pos[1] == king[1]+1 && pos[0] == king[0]+1){
                return PUNTEGGIO_MEDIO;
            }
            // posizione opposta per evitare la cattura del re
            if(!sinistra && pos[0]==king[0] && pos[1]==king[1]-1){
                return PUNTEGGIO_ALTO;
            }
            // punti su tutta la riga
            //if(pos[1] == king[1]+1) return basso;
        }

        // SINISTRA
        if(sinistra){
            // distanza 2 dal re
            if(pos[0] == king[0] && pos[1] == king[1]-2){
                return PUNTEGGIO_ALTO;
            }
            // diagonali attorno al nero
            if(pos[1] == king[1]-1 && pos[0] == king[0]-1 ||
            pos[1] == king[1]+1 && pos[0] == king[0]-1){
                return PUNTEGGIO_MEDIO;
            }
            // posizione opposta per evitare la cattura del re
            if(!destra && pos[0]==king[0] && pos[1]==king[1]+1){
                return PUNTEGGIO_ALTO;
            }
            // punti su tutta la riga
            //if(pos[1] == king[1]-1) return basso;
        }

        // SOPRA
        if(sopra){
            // distanza 2 dal re
            if(pos[1] == king[1] && pos[0] == king[0]-2){
                return PUNTEGGIO_ALTO;
            }
            // diagonali attorno al nero
            if(pos[1] == king[1]-1 && pos[0] == king[0]+1 ||
            pos[1] == king[1]-1 && pos[0] == king[0]-1){
                return PUNTEGGIO_MEDIO;
            }
            // posizione opposta per evitare la cattura del re
            if(!sotto && pos[0]==king[0]+1 && pos[1]==king[1]){
                return PUNTEGGIO_ALTO;
            }
            // punti su tutta la colonna
            //if(pos[0] == king[0]-1) return basso;
        }

        // SOTTO
        if(sotto){
            // distanza 2 dal re
            if(pos[1] == king[1] && pos[0] == king[0]+2){
                return PUNTEGGIO_ALTO;
            }
            // diagonali attorno al nero
            if(pos[1] == king[1]+1 && pos[0] == king[0]+1 ||
            pos[1] == king[1]+1 && pos[0] == king[0]-1){
                return PUNTEGGIO_MEDIO;
            }
            // posizione opposta per evitare la cattura del re
            if(!sopra && pos[0]==king[0]-1 && pos[1]==king[1]){
                return PUNTEGGIO_ALTO;
            }
            // punti su tutta la colonna
            //if(pos[0] == king[0]+1) return basso;
        }

        return 0;
    }
}
