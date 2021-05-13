package it.unibo.ai.didattica.competition.tablut.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

/**
 * 
 * @author A. Piretti, Andrea Galassi
 *
 */
public class TablutRandomClient extends TablutClient {

	private int gameType;
	private int depth;
	private MyGame myGame;
	private Evaluator eval = new Evaluator();
	private List<int[]> pawns = new ArrayList<int[]>();
	private List<int[]> empty = new ArrayList<int[]>();
	private String myself;

	public TablutRandomClient(String player, String name, int gameChosen, int timeout, String ipAddress, int depth)
			throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
		this.depth = depth;
		gameType = gameChosen;
	}

	public TablutRandomClient(String player, String name, int timeout, String ipAddress, int depth)
			throws UnknownHostException, IOException {
		this(player, name, 4, timeout, ipAddress, depth);
	}

	public TablutRandomClient(String player, int timeout, String ipAddress, int depth)
			throws UnknownHostException, IOException {
		this(player, "random", 4, timeout, ipAddress, depth);
	}

	public TablutRandomClient(String player, int depth) throws UnknownHostException, IOException {
		this(player, "random", 4, 60, "localhost", depth);
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		int gametype = 4;
		String role = "";
		String name = "random";
		String ipAddress = "localhost";
		int timeout = 60;
		int depthDefault = 2; // default depth

		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			System.out.println(args[0]);
			role = (args[0]);
		}
		if (args.length == 2) {
			System.out.println(args[1]);
			timeout = Integer.parseInt(args[1]);
		}
		if (args.length == 3) {
			ipAddress = args[2];
		}
		if (args.length == 4) {
			depthDefault = Integer.parseInt(args[3]);
		}
		System.out.println("Selected client: " + args[0]);

		TablutRandomClient client = new TablutRandomClient(role, name, gametype, timeout, ipAddress, depthDefault);
		client.run();
	}

	@Override
	public void run() {

		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		State state;

		Game rules = null;
		switch (this.gameType) {
		case 1:
			state = new StateTablut();
			rules = new GameTablut();
			break;
		case 2:
			state = new StateTablut();
			rules = new GameModernTablut();
			break;
		case 3:
			state = new StateBrandub();
			rules = new GameTablut();
			break;
		case 4:
			state = new StateTablut();
			state.setTurn(State.Turn.WHITE);
			rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
			System.out.println("Ashton Tablut game");
			break;
		default:
			System.out.println("Error in game selection");
			System.exit(4);
		}

		System.out.println("You are player " + this.getPlayer().toString() + "!");

		myGame = new MyGame();

		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				// if e1 is of kind "SocketTimeoutException": close for timout exceeded
				if(e1.getClass().getName().equals("java.net.SocketTimeoutException")){
					System.out.println("-- timeout exceeded :(");
					System.exit(1);
				}
				
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Current state:");
			state = this.getCurrentState();
			System.out.println(state.toString());
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
			}

			if (this.getPlayer().equals(Turn.WHITE)) {
				myself = "W";

				// GET ALIVE PAWNS:
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					// ------------------------------- WHITE
					// ----------------------------------------------------------------------------------
					
					// GET VALID MOVES:
					List<Action> validMoves = getValidMoves(state, State.Turn.WHITE);

					// GET BEST MOVE:
					Action bestMove = getBestMove(State.Turn.WHITE, state, validMoves);

					if (bestMove.isKing()) {
						System.out.println(bestMove.toString());
					}
					try {
						this.write(bestMove);
						// writes action to server

						myGame.addMove(bestMove);

					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					pawns.clear();
					empty.clear();

				}
				// Turno dell'avversario
				else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
					System.out.println("Waiting for your opponent move... ");
				}
				// ho vinto
				else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				}
				// ho perso
				else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				}
				// pareggio
				else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}
				// ------------------------------- BLACK
				// ------------------------------------------------------------------------------------
			} else { // black moves and I'm black

				myself = "B";

				// Mio turno
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					// GET VALID MOVES:
					List<Action> validMoves = getValidMoves(state, State.Turn.BLACK);

					// GET BEST MOVE:
					Action bestMove = getBestMove(State.Turn.BLACK, state, validMoves);

					// random move for testing:
					// Action bestMove = validMoves.get(new Random().nextInt(validMoves.size()));

					try {
						this.write(bestMove);
						// writes action to server

						myGame.addMove(bestMove);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					pawns.clear();
					empty.clear();
				}

				else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
					System.out.println("Waiting for your opponent move... ");
				} else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}
			}
		}
	}

	private List<Action> getValidMoves(State state, State.Turn turn) {
		int[] buf;

		if (turn.equalsTurn(State.Turn.WHITE.toString())) {
			for (int i = 0; i < state.getBoard().length; i++) {
				for (int j = 0; j < state.getBoard().length; j++) {
					if (state.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString())
							|| state.getPawn(i, j).equalsPawn(State.Pawn.KING.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						pawns.add(buf); // here go [i, j]s of white pawns
					} else if (state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						empty.add(buf); // here go [i, j]s of blank cells
					}
				}
			}
			return getWhiteValidMoves(state);
		} else {
			for (int i = 0; i < state.getBoard().length; i++) {
				for (int j = 0; j < state.getBoard().length; j++) {
					if (state.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						pawns.add(buf); // here go [i, j]s of white pawns
					} else if (state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						empty.add(buf); // here go [i, j]s of blank cells
					}
				}
			}
			return getBlackValidMoves(state);
		}
	}

	private Action getBestMove(State.Turn colour, State state, List<Action> validMoves) {
		int score = 0;
		int bestScore = Integer.MIN_VALUE ;
		State child;
		Action bestMove = null;

		for (Action move : validMoves) {

			child = state.clone();
			child = movePawn(child, move);

			// remember that default depth is 2
			score = minimax(child, 2, bestScore, Integer.MAX_VALUE , false);

			/*System.out.println("-- RISULTATO MINIMAX in getbBestMove\n-- score: " + score + (move.isKing() ? " RE!" : ""));
			System.out.println(move.toString());
			System.out.println("___");*/

			if (score > bestScore) {
				bestScore = score;
				//System.out.println("---- nuovo bestScore! "+bestScore);
				bestMove = move;
			}
		}
		//System.out.println("---- score scelto! "+bestScore);
		//System.out.println(bestMove.toString());
		return bestMove;
	}

	private int minimax(State state, int depth, int alpha, int beta, boolean maximising) {
		// EXIT CONDITIONS:

		if ((depth <= 0) || state.getTurn().equalsTurn("WW") || state.getTurn().equalsTurn("BW")) { // 0 depth or wins
			if(myself.equals("B"))
				return eval.evaluateState(state, State.Turn.BLACK);
			if(myself.equals("W")){

				return eval.evaluateState(state, State.Turn.WHITE);
			}
		}

		// draw
		//

		// -- EXIT CONDITIONS

		if(maximising){ // MAX FASE:
			int newAlpha = Integer.MIN_VALUE;
			State nextState;
			int eva;

			List<Action> nextMoves = getValidMoves(state, state.getTurn());

			for(Action move : nextMoves){
				// -- clone state and make move
				nextState = state.clone();
				nextState = movePawn(nextState, move);

				// -- get newAlpha with max between newAlpha and minimax with false (not maximising)
				eva = minimax(nextState, depth -1, alpha, beta, false);

				// set alpha with max between alpha and newAlpha
				newAlpha = Math.max(eva, newAlpha);

				alpha = Math.max(alpha, newAlpha);

				// check alpha better than beta, if so return alpha
				if(alpha >= beta){
					return alpha;
				}
			}

			return newAlpha;
		}

		else{ // MIN FASE:
			int newBeta = Integer.MAX_VALUE;
			State nextState;
			int eva;

			List<Action> nextMoves = getValidMoves(state, state.getTurn());

			for(Action move : nextMoves){
				// -- clone state and make move
				nextState = state.clone();
				nextState = movePawn(nextState, move);

				// -- get newAlpha with max between newBeta and minimax with true (maximising)
				eva = minimax(nextState, depth -1, alpha, beta, true);

				// set beta with min between beta and newBeta
				newBeta = Math.min(newBeta, eva);

				beta = Math.min(beta, newBeta);

				// check alpha better than beta, if so return beta
				if(alpha >= beta){
					return beta;
				}
			}
			return newBeta;
		}
	}

	private State checkCapture(State state, Action a, State.Turn turn) {
		if (turn.equalsTurn(State.Turn.WHITE.toString())) {
			state = myGame.checkCaptureBlackPawnRight(state, a);
			state = myGame.checkCaptureBlackPawnLeft(state, a);
			state = myGame.checkCaptureBlackPawnUp(state, a);
			state = myGame.checkCaptureBlackPawnDown(state, a);
			state = myGame.checkCaptureBlackKingRight(state, a);
			state = myGame.checkCaptureBlackKingLeft(state, a);
			state = myGame.checkCaptureBlackKingDown(state, a);
			state = myGame.checkCaptureBlackKingUp(state, a);
		} else if (turn.equalsTurn(State.Turn.BLACK.toString())) {
			state = myGame.checkCaptureWhite(state, a);
		}
		return state;
	}

	private State movePawn(State state, Action a) {
		State.Pawn pawn = state.getPawn(a.getRowFrom(), a.getColumnFrom());
		State.Pawn[][] newBoard = state.getBoard();

		// libero il trono o una casella qualunque
		if (a.getColumnFrom() == 4 && a.getRowFrom() == 4) {
			newBoard[a.getRowFrom()][a.getColumnFrom()] = State.Pawn.THRONE;
		} else {
			newBoard[a.getRowFrom()][a.getColumnFrom()] = State.Pawn.EMPTY;
		}

		// metto nel nuovo tabellone la pedina mossa
		newBoard[a.getRowTo()][a.getColumnTo()] = pawn;
		// aggiorno il tabellone
		state.setBoard(newBoard);
		// cambio il turno
		if (state.getTurn().equalsTurn(State.Turn.WHITE.toString())) {
			// check win
			state = checkCapture(state, a, State.Turn.WHITE);
			
			if (!state.getTurn().equalsTurn(State.Turn.WHITEWIN.toString())) state.setTurn(State.Turn.BLACK);
		} else {
			// check win
			state = checkCapture(state, a, State.Turn.BLACK);
			
			if (!state.getTurn().equalsTurn(State.Turn.BLACKWIN.toString())) state.setTurn(State.Turn.WHITE);
		}

		return state;
	}

	private List<Action> getBlackValidMoves(State state) {
		List<Action> validMoves = new ArrayList<>();
		String from, to;
		boolean isKing = false;

		for (int[] pawn : pawns) {
			from = state.getBox(pawn[0], pawn[1]);
			for (int[] dest : empty) {
				to = state.getBox(dest[0], dest[1]);
				isKing = false;
				try {
					Action temp = new Action(from, to, State.Turn.BLACK, isKing);
					if (myGame.checkMove(state, temp)) {
						validMoves.add(temp);
					}
				} catch (Exception e) {
					System.out.println("errore:");
					e.printStackTrace();
				}
			}
		}
		pawns.clear();
		empty.clear();
		return validMoves;
	}

	private List<Action> getWhiteValidMoves(State state) {
		List<Action> validMoves = new ArrayList<>();
		String from, to;
		boolean isKing = false;

		for (int[] pawn : pawns) {
			from = state.getBox(pawn[0], pawn[1]);
			for (int[] dest : empty) {
				to = state.getBox(dest[0], dest[1]);
				isKing = (state.getPawn(pawn[0], pawn[1]).equalsPawn("K") ? true : false);
				try {
					Action temp = new Action(from, to, State.Turn.WHITE, isKing);
					// qui ok.

					if (myGame.checkMove(state, temp)) {
						validMoves.add(temp);
					}

				} catch (Exception e) {
					System.out.println("errore:");
					e.printStackTrace();
				}
			}
		}
		pawns.clear();
		empty.clear();
		return validMoves;
	}

}
