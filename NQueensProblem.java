import java.util.*;
public class NQueensProblem 
{   
    public int moveCount = 0;
    public int succesfulMoveCount = 0;
    public int restartCount = 0;
    public int failureIterCount = 0;
    public int failureMoveCount = 0;
    public int succesfulIterCount = 0;
    public int totalRestartsCount = 0;
    public int hc=0;
    boolean restartFlag = false;
// Main method responsible for running the N-Queens problem solving algorithms.
    public static void main(String[] args)
    {   
        int iterCount = 0;
        double totalRestartCount = 0;
        double restartsCount = 0.0f;
        double succesfulMovesCount = 0, succesfulIterCount = 0,succesfulICount =0 , succesfullAvg =0;
        double failureMovesCount = 0,failureItersCount = 0, failureCount =0,failureAvg = 0;
        int option = 1;
        Random random = new Random();
        int iterations = random.nextInt(400) + 100;
        int i=0;
        int[] res = new int[]{0,0,0,0};
        boolean flag = false;

        //Taking the input for the number of queens.
        Scanner obj = new Scanner(System.in);
        System.out.println("Enter the number of queens you want on the board : ");

        //Taking the input for the algorithm type
        int queens = obj.nextInt();
        System.out.println("Select an option from below : ");
        System.out.println("1) Hill climbing search");
        System.out.println("2) Hill climbing search with sideways move");
        System.out.println("3) Random-restart hill-climbing with sideways move");
        System.out.println("4) Random-restart hill climbing without sideways move");

        int selection = obj.nextInt();

        while(i<iterations)
        {
            NQueensProblem algorithm = new NQueensProblem();
            if (i<5){
                flag = true;
            }
            else{
                flag=false;
            }
            if(selection==1){
                res = algorithm.Hill_Climb_Search(queens,flag);
            }
            else if(selection==2){
                res = algorithm.Hill_Climb_Search_With_Sideways_Move(queens,flag);
            }
            else if(selection==3){
                res = algorithm.Hill_Climb_Random_Restart_With_Sideways_Move(queens,flag);
            }
            else if(selection==4){
                res = algorithm.Hill_Climb_Random_Restart_Without_Sideways_Move(queens,flag);
            }
            else{
                System.out.println("Option not available, try again!");
            }
            
            
            succesfulMovesCount  = succesfulMovesCount+res[0];
            succesfulIterCount = succesfulIterCount +res[1];
            failureMovesCount =failureMovesCount +res[2];
            failureItersCount = failureItersCount + res[3];
            i++;
        }

        // Print results and statistics.
        System.out.println("Number Of Queens: " + queens);
        System.out.println("Number of Iterations: " + iterations);
        if(failureItersCount!=0) 
        {
            failureCount = (failureItersCount / iterations) * 100;
            failureAvg = failureMovesCount / failureItersCount;
        }
        if(succesfulIterCount != 0)
        {
            succesfulICount = (succesfulIterCount/iterations)*100;
            succesfullAvg = succesfulMovesCount/succesfulIterCount;
        }
        System.out.println("Success Percentage: " + String.format("%.4f", succesfulICount) + "%");
        System.out.println("Failure Percentage: " + String.format("%.4f", failureCount) + "%");
        System.out.println("No Of steps incase of success: " + String.format("%.4f", succesfullAvg));
        System.out.println("No of steps incase of failure: " + String.format("%.4f", failureAvg));
    }

    // Hill Climbing Search for the N-Queens problem.
    public int[] Hill_Climb_Search(int nqueen,boolean flag) 
    {
        NQueenBoard currBoard = new NQueenBoard();
        NQueenBoard board = getRandomBoard(nqueen);
        int[] result = new int[4];
        boolean isSolved = false;
        boolean isFailure = false;
        ArrayList<NQueenBoard> child = new ArrayList<NQueenBoard>();

                // Initialize the current board.

        setH(board);
        if(flag==true){
            System.out.println("Starting State");
            showBoard(board);
        }
        while (!isFailure && !isSolved) 
        {
            child = childBoards(board);

// Calculate heuristic for each child.

            int i=0;
            while(i<child.size())
            {
                setH(child.get(i));
                i++;
            }
            // Select the next best child.
            currBoard = nextBestChild(child);
            if (board.getHCost() == 0) 
            {
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if(flag==true){
                    System.out.println("Destination State");
                    showBoard(board);
                }
                succesfulMoveCount = +moveCount;
                ++succesfulIterCount;
                isSolved = true;        
            } 
            else if (currBoard.getHCost() < board.getHCost()) 
            {
                // Update the current board with the best possible child.
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                // if yes update current Node with best Successor
                board = currBoard;
                ++moveCount;
            } 
            else 
            {
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if (flag==true){
                    System.out.println("Local Minima State");
                    showBoard(board);
                }
                ++failureIterCount;
                failureMoveCount = +moveCount;
                isFailure = true; 
            }
        }
        result[0] = succesfulMoveCount;
        result[1] = succesfulIterCount;
        result[2] = failureMoveCount;
        result[3] = failureIterCount;
        return result;
    }

    public class hCost implements Comparator<NQueenBoard> {
    @Override
    public int compare(NQueenBoard l1, NQueenBoard l2) {
        // Compare the heuristic cost of two NQueenBoard instances.
        if (l1.getHCost() > l2.getHCost()) {
            return 1; // l1 has a higher cost, so it comes after l2.
        } else if (l1.getHCost() < l2.getHCost()) {
            return -1; // l1 has a lower cost, so it comes before l2.
        }
        // If the costs are equal, return 0, indicating they are equivalent.
        return 0;
    }
    }

    public class NQueenBoard {
    int[][] board;          // 2D array representing the N-Queens board
    int heuristicCost;      // Heuristic cost associated with the board

    // Getter for the board
    public int[][] getBoard() {
        return board;
    }

    // Setter for the board
    public void setBoard(int[][] board) {
        this.board = board;
    }

    // Getter for the heuristic cost
    public int getHCost() {
        return heuristicCost;
    }

    // Setter for the heuristic cost
    public void setHCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    @Override
    public String toString() {
        // Return a string representation of the NQueenBoard
        return "NQueenBoard{" +
               "board=" + Arrays.toString(board) +
               ", heuristicCost=" + heuristicCost +
               '}';
    }
}


        /*
    * childBoards function is used for getting possible successors to a given current state.
    * It accepts an NQueenBoard as a parameter, representing the current state of the board.
    * It returns an ArrayList of NQueenBoard objects, each representing a possible successor state.
    */
    public ArrayList<NQueenBoard> childBoards(NQueenBoard board) {
        ArrayList<NQueenBoard> successorList = new ArrayList<NQueenBoard>();

        for (int c = 0; c < board.getBoard().length; c++) {
            int cQR = Integer.MAX_VALUE; // Current Queen's row position
            int cQC = c; // Current Queen's column position
            int nQC = c; // New Queen's column position

            // Find the current Queen's row position
            for (int i = 0; i < board.getBoard().length; i++) {
                if (board.getBoard()[i][cQC] == 1) {
                    cQR = i;
                    break;
                }
            }

            for (int row = 0; row < board.getBoard().length; row++) {
                if (row != cQR && cQR != Integer.MAX_VALUE) {
                    int[][] currBoard = new int[board.getBoard().length][board.getBoard().length];

                    // Create a copy of the current board configuration
                    copyBoard(board.getBoard(), currBoard);

                    int nQR = row; // New Queen's row position

                    // Swap the positions of the current Queen and the new Queen
                    currBoard[cQR][cQC] = 0;
                    currBoard[nQR][nQC] = 1;

                    NQueenBoard newNQueenBoard = new NQueenBoard();
                    newNQueenBoard.setBoard(currBoard);
                    successorList.add(newNQueenBoard);
                }
            }
        }

        return successorList;
    }



        /*
    * setH function is used for calculating the heuristic cost value of the given board.
    * It accepts an NQueenBoard object as a parameter representing the current state of the board.
    * It calculates the heuristic cost based on the number of conflicts (queens attacking each other) and updates the heuristic cost in the NQueenBoard object.
    */
    public void setH(NQueenBoard board) {
        int[][] currBoard = new int[board.getBoard().length][board.getBoard().length];
        copyBoard(board.getBoard(), currBoard);

        int length = board.getBoard().length;
        int[] rIndex = new int[length];
        int[] cIndex = new int[length];
        int index = 0;
        int hValue = 0;

        // Find the row and column indices of the Queens
        for (int col = 0; col < length; col++) {
            for (int row = 0; row < length; row++) {
                if (currBoard[row][col] == 1) {
                    rIndex[index] = row;
                    cIndex[index] = col;
                    index++;
                }
            }
        }

        boolean hncFlag = true;

        // Check for conflicts (Queens attacking each other)
        for (int i = 0; i < rIndex.length; i++) {
            for (int j = i + 1; j < rIndex.length; j++) {
                hncFlag = true;

                // Check for conflicts in the same row
                if (rIndex[i] == rIndex[j]) {
                    hValue++;
                }

                // Check for conflicts in the same column
                if (cIndex[i] == cIndex[j]) {
                    hValue++;
                }

                // Check for conflicts in diagonals
                if (Math.abs(rIndex[i] - rIndex[j]) == Math.abs(cIndex[i] - cIndex[j])) {
                    hValue++;
                }
            }

            if (!hncFlag) {
                hValue = Integer.MAX_VALUE;
            }

            board.setHCost(hValue); // Update the heuristic cost in the NQueenBoard object
        }
    }

    /*
    * Hill_Climb_Random_Restart_With_Sideways_Move is used for performing the random restart hill climb algorithm
    * with considering sideways moves. It accepts the number of queens (nqueen) and a flag indicating whether
    * to display intermediate states (flag).
    * It returns an array of integers containing the number of successful moves, iterations, failed moves, restart count, and restarts with failure.
    */

    public int[]  Hill_Climb_Random_Restart_With_Sideways_Move(int nqueen, boolean flag)
    {
        boolean IsSolved = false, IsFailed = false, updateCurrBoard = false;
        int sideMovesCount = 100, sideStepCount = 0;
        int[] result = new int[6];
        ArrayList<NQueenBoard> next = new ArrayList<NQueenBoard>();
        NQueenBoard currBoard = new NQueenBoard();
        NQueenBoard board = getRandomBoard(nqueen);
        setH(board);
        if(flag==true){
            System.out.println("Starting State");
            showBoard(board);
        }
        while (!IsSolved && !IsFailed) 
        {
            updateCurrBoard = false;
            next = childBoards(board);
            int i=0;
            while(i<next.size())
            {
                setH(next.get(i));
                i++;
            }
            currBoard = nextBestChild(next);
            if (currBoard.getHCost() == board.getHCost()) 
            {
                currBoard = nextBestChild(next);
            }
            if (board.getHCost() == 0) 
            {
                succesfulMoveCount = +moveCount;
                succesfulIterCount++;
                IsSolved = true;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if(flag==true){
                    System.out.println("Destination State");
                    showBoard(board);
                }
            } 
            else if (currBoard.getHCost() < board.getHCost()) 
            {
                sideStepCount =0;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                moveCount++;
                updateCurrBoard = true;
            }  
            else if (currBoard.getHCost() == board.getHCost() && sideStepCount < sideMovesCount) 
            {
                sideStepCount++;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                moveCount++;
                updateCurrBoard = true;
            } 
            else 
            {
                board = getRandomBoard(nqueen);
                setH(board);
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                totalRestartsCount++;
                restartFlag = true;
            }
            if (updateCurrBoard) 
            {
                board = currBoard;
            }
        }
        if (restartFlag) 
        {
            restartCount++;
        }
        result[0] = succesfulMoveCount;
        result[1] = succesfulIterCount;
        result[2] = failureMoveCount;
        result[3] = failureIterCount;
        result[4] = totalRestartsCount;
        result[5] = restartCount;
        return result;
    }
            /*
    * nextBestChild function is used to return the next possible best child among a list of successor boards.
    * It accepts an ArrayList of NQueenBoard objects (board_Array_List) representing possible successor states.
    * The function returns the NQueenBoard object representing the next best child state.
    */
    public NQueenBoard nextBestChild(ArrayList<NQueenBoard> board_Array_List) {
        PriorityQueue<NQueenBoard> pq = new PriorityQueue<NQueenBoard>(board_Array_List.size(), new hCost());

        // Add all successor boards to a priority queue based on their heuristic cost
        for (int i = 0; i < board_Array_List.size(); i++) {
            pq.add(board_Array_List.get(i));
        }

        List<NQueenBoard> nextBestChildList = new ArrayList<NQueenBoard>();
        NQueenBoard nextBestChild = new NQueenBoard();
        nextBestChild = pq.poll();
        int hcost = nextBestChild.getHCost();
        nextBestChildList.add(nextBestChild);

        // Retrieve all boards with the same heuristic cost as the first board
        while (pq.peek().getHCost() == hcost) {
            nextBestChildList.add(pq.poll());
        }

        // Select a random board from the list of boards with the same heuristic cost
        Random rand = new Random();
        int index = rand.nextInt(nextBestChildList.size());
        nextBestChild = nextBestChildList.get(index);

        pq.clear();
        return nextBestChild;
    }

    
        /*
    * getRandomBoard function generates a random NQueenBoard with queens placed randomly on the board.
    * It accepts an integer nqueen, representing the number of queens to be placed on the board.
    * The function returns an NQueenBoard object with queens placed randomly.
    */
    public NQueenBoard getRandomBoard(int nqueen) {
        NQueenBoard board = new NQueenBoard();
        int[][] new_State = new int[nqueen][nqueen];
        Random random = new Random();

        for (int i = 0; i < nqueen; i++) {
            int rand_Number = random.nextInt(nqueen - 1);

            for (int j = 0; j < nqueen; j++) {
                if (j == rand_Number) {
                    new_State[j][i] = 1;
                } else {
                    new_State[j][i] = 0;
                }
            }
        }

        board.setBoard(new_State);
        return board;
    }

    
        /*
    * copyBoard function is used to copy the state of the source NQueenBoard to the destination NQueenBoard.
    * It accepts two 2D integer arrays (src and dst) representing the source and destination boards, respectively.
    * The function copies the state of the source board to the destination board.
    */
    public void copyBoard(int[][] src, int[][] dst) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                dst[i][j] = src[i][j];
            }
        }
    }


    /*
    * Hill_Climb_Random_Restart_Without_Sideways_Move is used to perform the random restart hill climb algorithm without considering sideways moves.
    * It accepts an integer nqueen representing the number of queens and a boolean flag indicating whether to display intermediate states.
    * The function returns an array of integers representing successful move count, successful iteration count, failure move count, failure iteration count,
    * total restart count, and restart count.
    */
    public int[]  Hill_Climb_Random_Restart_Without_Sideways_Move(int nqueen, boolean flag)
    {
        int sideStepCount = 0, sideMovesCount = 10;;
        int[] result = new int[6];
        ArrayList<NQueenBoard> next = new ArrayList<NQueenBoard>();
        NQueenBoard currBoard = new NQueenBoard();
        boolean IsSolved = false, IsFailed = false, updateCurrBoard = false;
        NQueenBoard board = getRandomBoard(nqueen);
        setH(board);
        if(flag==true){
            System.out.println("Starting State");
            showBoard(board);
        }
        while (!IsSolved) 
        {
            next = childBoards(board);
            int i=0;
            while(i<next.size())
            {
                setH(next.get(i));
                i++;
            }
            currBoard = nextBestChild(next);
            if (board.getHCost() == 0) 
            {
                succesfulMoveCount = +moveCount;
                succesfulIterCount++;
                IsSolved = true;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if(flag==true){
                    System.out.println("Destination State");
                    showBoard(board);
                }
            } 
            else if (currBoard.getHCost() < board.getHCost()) 
            {
                board = currBoard;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                moveCount++;
            } else 
            {
                board = getRandomBoard(nqueen);
                setH(board);
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                totalRestartsCount++;
                restartFlag = true;
            }
        }
        if (restartFlag) 
        {
            restartCount++;
        }
        result[0] = succesfulMoveCount;
        result[1] = succesfulIterCount;
        result[2] = failureMoveCount;
        result[3] = failureIterCount;
        result[4] = totalRestartsCount;
        result[5] = restartCount;
        return result;
    }

    
    /*
    * Hill_Climb_Search_With_Sideways_Move is used to perform the hill climb algorithm with considering sideways moves.
    * It accepts an integer nqueen representing the number of queens and a boolean flag indicating whether to display intermediate states.
    * The function returns an array of integers representing successful move count, successful iteration count, failure move count, and failure iteration count.
    */
    public int[]  Hill_Climb_Search_With_Sideways_Move(int nqueen, boolean flag)
    {
        int side_Moves_count = 100;
        boolean isSolved = false,isFailure = false, updateCurrBoard = false;;
        int count_Of_Side_Step = 0;
        ArrayList<NQueenBoard> child = new ArrayList<NQueenBoard>();
        NQueenBoard nqb_successor = new NQueenBoard();
        int[] result = new int[4];
        NQueenBoard board = getRandomBoard(nqueen);
        setH(board);
        if(flag==true){
            System.out.println("Starting State");
            showBoard(board);
        }
        while (!isFailure && !isSolved) 
        {
            updateCurrBoard = false;
            child = childBoards(board);
            int i=0;
            while(i<child.size())
            {
                setH(child.get(i));
                i++;
            }
            nqb_successor = nextBestChild(child);
            if (nqb_successor.getHCost() == board.getHCost()) 
            {
                nqb_successor = nextBestChild(child);
            }
            if (board.getHCost() == 0) 
            {
                succesfulMoveCount = succesfulMoveCount + moveCount;
                succesfulIterCount++;
                isSolved = true;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if(flag==true){
                    System.out.println("Destination State");
                    showBoard(board);
                }
            } 
            else if (board.getHCost()>nqb_successor.getHCost()) 
            {
                // updating the current node of the nQueen with best possible child
                count_Of_Side_Step =0;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    showBoard(board);
                }
                moveCount++;
                updateCurrBoard = true;
            }  
            else if (board.getHCost() == nqb_successor.getHCost() && side_Moves_count>count_Of_Side_Step) 
            {
                // by moving the sideways 
                count_Of_Side_Step++;
                moveCount++;
                updateCurrBoard = true;
            } 
            else 
            {
                failureIterCount++;
                failureMoveCount = +moveCount;
                isFailure = true;
                if(flag == true) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                if (flag==true){
                    System.out.println("Local Minima State");
                    showBoard(board);
                }
            }
            if (updateCurrBoard) 
            {
                board = nqb_successor;
            }
        }
        result[0] = succesfulMoveCount;
        result[1] = succesfulIterCount;
        result[2] = failureMoveCount;
        result[3] = failureIterCount;
        return result;
    }
        /*
    * showBoard is used to display the NQueen board on the console.
    * It accepts a parameter b of type NQueenBoard, which represents the board to be displayed.
    * The function prints the board with queens represented as 'Q' and empty cells as '*'.
    * It also displays the number of collisions (heuristic cost).
    */
    public void showBoard(NQueenBoard b) {
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                String val = "*";
                if ((b.getBoard())[i][j] == 1) {
                    val = "Q";
                }
                System.out.print(val + " ");
            }
            System.out.println();
        }
        int col = b.getHCost();
    }
   
    
}
