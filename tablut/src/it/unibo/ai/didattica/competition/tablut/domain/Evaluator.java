package it.unibo.ai.didattica.competition.tablut.domain;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import java.util.*;

public class Evaluator {
    private static final int WHITE_MATERIAL_WEIGHT = 0;
    private static final int KING_POS_WEIGHT = 3;
    private static final int WHITE_PAWNS_POS_WEIGHT = 2;

    //private static final int OPPONENT_WEIGHT = 5;

    private static final int BLACK_PAWNS_POS_WEIGHT = 2;
    private static final int BLACK_MATERIAL_WEIGHT = 1;


    private List<int[]> positionsW = new ArrayList<>();
    private List<int[]> positionsB = new ArrayList<>();
    private List<int[]> emptyCells = new ArrayList<>();
    private int[] kingPos;


    private Scores scores = new Scores();

    public int evaluateState(State state, State.Turn turn){
        kingPos = state.getKingPos();

        positionsW = state.getPawnsPos(State.Pawn.WHITE.toString());
        positionsB = state.getPawnsPos(State.Pawn.BLACK.toString());
        emptyCells = state.getPawnsPos(State.Pawn.EMPTY.toString());

        scores.updateAttackMatrix(kingPos);

        if(turn.equalsTurn("W")){
            if(state.getTurn().equalsTurn("WW")) return 1000;
            if(state.getTurn().equalsTurn("BW")) return -1000;
            return evaluateWhite(state);
        }
        else if(turn.equalsTurn("B")){
            if(state.getTurn().equalsTurn("BW")) return 1000;
            if(state.getTurn().equalsTurn("WW")) return -1000;
            else return evaluateBlack(state);
        }
        else return 0;
    }



    private int evaluateWhite(State state){
        int materialScore = (state.getNumberOf(Pawn.WHITE) * WHITE_MATERIAL_WEIGHT);
        int posScore=0;
        int kingScore=0;

        if(kingPos[0] != -1){ // "-1" handles scores exception (should never happen though, see State class)
            kingScore += (scores.getKingScore(kingPos) * KING_POS_WEIGHT);
        }

        scores.destra = ( (kingPos[0]+1 <8) ? state.getPawn(kingPos[1], kingPos[0]+1).equalsPawn("B") : false);
        scores.sinistra = ( (kingPos[0]-1 >0) ? state.getPawn(kingPos[1], kingPos[0]-1).equalsPawn("B") : false);
        scores.sopra = ( (kingPos[1]-1 >0) ? state.getPawn(kingPos[1]-1, kingPos[0]).equalsPawn("B") : false);
        scores.sotto = ( (kingPos[1]+1 <8) ? state.getPawn(kingPos[1]+1, kingPos[0]).equalsPawn("B") : false);

        for(int[] p : positionsW){
            posScore += (scores.getWhitePawnsScore(p, kingPos, positionsB, state));
        }

        posScore = posScore * WHITE_PAWNS_POS_WEIGHT;
    
        return (materialScore + posScore + kingScore);
    }



    private int evaluateBlack(State state){
        int materialScore = (state.getNumberOf(Pawn.BLACK) * BLACK_MATERIAL_WEIGHT);
        int posScore=0;

        scores.canEscape(kingPos, emptyCells);

        for(int[] p : positionsB){
            posScore += (scores.getBlackPawnsScore(p, kingPos, emptyCells));
        }
        posScore *= BLACK_PAWNS_POS_WEIGHT;

        return materialScore + posScore;
    }
}