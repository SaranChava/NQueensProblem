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
            if(selection==1){
                res = algorithm.Hill_Climb_Search(queens,option);
            }
            else if(selection==2){
                res = algorithm.Hill_Climb_Search_With_Sideways_Move(queens,option);
            }
            else if(selection==3){
                res = algorithm.Hill_Climb_Random_Restart_With_Sideways_Move(queens,option);
            }
            else if(selection==4){
                res = algorithm.Hill_Climb_Random_Restart_Without_Sideways_Move(queens,option);
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
   
    public int[] Hill_Climb_Search(int nqueen,int task_To_Be_Done) 
    {
        NQueenBoard nqb_Successor = new NQueenBoard();
        NQueenBoard board = get_Random_State(nqueen);
        int[] result = new int[4];
        boolean is_Passed = false;
        boolean is_Fail = false;
        ArrayList<NQueenBoard> successor = new ArrayList<NQueenBoard>();
        set_Heuristic(board);
        System.out.println("Starting State");
        print_NQueen_Board(board);
        while (!is_Fail && !is_Passed) 
        {
            successor = find_The_Successors(board);
            int i=0;
            while(i<successor.size())
            {
                set_Heuristic(successor.get(i));
                i++;
            }
            nqb_Successor = get_Next_Best_Successor(successor);
            if (board.get_Heuristic_Cost() == 0) 
            {
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Destination State");
                print_NQueen_Board(board);
                succesfulMoveCount = +moveCount;
                ++succesfulIterCount;
                is_Passed = true;        
            } 
            else if (nqb_Successor.get_Heuristic_Cost() < board.get_Heuristic_Cost()) 
            {
                // if yes update current Node with best Successor
                board = nqb_Successor;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                ++moveCount;
            } 
            else 
            {
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Local Minima State");
                print_NQueen_Board(board);
                ++failureIterCount;
                failureMoveCount = +moveCount;
                is_Fail = true; 
            }
        }
        result[0] = succesfulMoveCount;
        result[1] = succesfulIterCount;
        result[2] = failureMoveCount;
        result[3] = failureIterCount;
        return result;
    }
    /*
        * Heuristic_Cost_Comparator class implementing Comparator interface and we override the compare method present in Comparator interface
        * The compare method returns the integer value based on the parameterss passed
    */
    public class Heuristic_Cost_Comparator implements Comparator<NQueenBoard> 
    {
        @Override
        public int compare(NQueenBoard l1, NQueenBoard l2) 
        {
            if(l1.get_Heuristic_Cost() > l2.get_Heuristic_Cost())
            {
                return 1;
            }
            if(l1.get_Heuristic_Cost() < l2.get_Heuristic_Cost())
            {
                return -1;
            }
            return 0;
        }
    }
    public class NQueenBoard 
    {
        int[][] board;
        int heuristic_Cost;

        public int[][] get_NQueen_Board() 
        {
            return board;
        }
        public void set_NQueen_Board(int[][] board) 
        {
            this.board = board;
        }

        public int get_Heuristic_Cost() 
        {
            return heuristic_Cost;
        }

        public void set_Heuristic_Cost(int heuristicCost) 
        {
            this.heuristic_Cost = heuristicCost;
        }

        @Override
        public String toString() 
        {
            return "NQueenBoard{" + "board=" + Arrays.toString(board) +", heuristicCost=" + heuristic_Cost +'}';
        }
    }
        /*
        *  find_The_Successors function is used for getting possible successors to a given current state it accepts NQueenBoard as Parameter
        *  It returns an arraylist with NQueen board type which contains the possible successors
    */
    public ArrayList<NQueenBoard> find_The_Successors(NQueenBoard board)
    {
        ArrayList<NQueenBoard> succesor_List = new ArrayList<NQueenBoard>();
        int new_Queen_Column,  cur_Queen_Row,cur_Queen_Column, new_Queen_Row;
        for (int column = 0; column < board.get_NQueen_Board().length; column++) 
        {
            cur_Queen_Row = Integer.MAX_VALUE;
            cur_Queen_Column = column;
            new_Queen_Column = column;
            for (int i = 0; i < board.get_NQueen_Board().length; i++) 
            {
                if (board.get_NQueen_Board()[i][cur_Queen_Column] == 1) 
                {
                    cur_Queen_Row = i;
                    break;
                }
            }
            for (int row = 0; row < board.get_NQueen_Board().length; row++) 
            {
                if (row != cur_Queen_Row && cur_Queen_Row != Integer.MAX_VALUE) 
                {
                    int[][] currentState = new int[board.get_NQueen_Board().length][board.get_NQueen_Board().length];
                    get_Copied_State(board.get_NQueen_Board(), currentState);
                    new_Queen_Row = row;
                    // swap
                    currentState[cur_Queen_Row][cur_Queen_Column] = 0;
                    currentState[new_Queen_Row][new_Queen_Column] = 1;
                    NQueenBoard newNQueenBoard = new NQueenBoard();
                    newNQueenBoard.set_NQueen_Board(currentState);
                    succesor_List.add(newNQueenBoard);
                }
            }
        }

        return succesor_List;
    }


    /*
        * This function is used for calculating the heuristic cost value of the given board by accepting single parameter i.e board which gives the current status of the board.
    */
    public void set_Heuristic(NQueenBoard board) 
    {
        int[][] current_NQueen_Board = new int[board.get_NQueen_Board().length][board.get_NQueen_Board().length];
        get_Copied_State(board.get_NQueen_Board(), current_NQueen_Board);
        int length = board.get_NQueen_Board().length;
        int[] row_Index = new int[length];
        int[] col_Index = new int[length];
        int index = 0;
        int hValue =0;
        for (int col =0;col<length;col++)
        {
            for(int row=0;row<length;row++)
            {
                if (current_NQueen_Board[row][col] == 1) 
                {
                    row_Index[index] =row;
                    col_Index[index] = col;
                    index++;
                }
            }
        }
        boolean heuristic_Not_Checked_Flag = true;
        for (int i = 0; i < row_Index.length; i++) 
        {
            for (int j = i + 1; j < row_Index.length; j++) 
            {
                heuristic_Not_Checked_Flag = true;
                if (row_Index[i] == row_Index[j]) 
                {
                    hValue++;
                }
                if (col_Index[i] == col_Index[j]) 
                {
                    hValue++;
                }
                if (Math.abs(row_Index[i] - row_Index[j]) == Math.abs(col_Index[i] - col_Index[j])) 
                {
                    hValue++;
                }
            }
            if(!heuristic_Not_Checked_Flag) 
            {
                hValue = Integer.MAX_VALUE;
            }
            board.set_Heuristic_Cost(hValue);
        }
    }
        /*
        * Hill_Climb_Random_Restart_With_Sideways_Move is used for performing the random restart hill climb algorithm with considering the sideway moves by accepting count of Queens and returns count of passed moves
    */
    public int[]  Hill_Climb_Random_Restart_With_Sideways_Move(int nqueen,int task_To_Be_Done)
    {
        boolean Is_Passed = false, Is_Fail = false, update_Current_Node = false;
        int Side_Moves_Count = 100, Count_Of_Side_Step = 0;
        int[] result = new int[6];
        ArrayList<NQueenBoard> next = new ArrayList<NQueenBoard>();
        NQueenBoard nqb_next = new NQueenBoard();
        NQueenBoard board = get_Random_State(nqueen);
        set_Heuristic(board);
        System.out.println("Starting State");
        print_NQueen_Board(board);
        while (!Is_Passed && !Is_Fail) 
        {
            update_Current_Node = false;
            next = find_The_Successors(board);
            int i=0;
            while(i<next.size())
            {
                set_Heuristic(next.get(i));
                i++;
            }
            nqb_next = get_Next_Best_Successor(next);
            if (nqb_next.get_Heuristic_Cost() == board.get_Heuristic_Cost()) 
            {
                nqb_next = get_Next_Best_Successor(next);
            }
            if (board.get_Heuristic_Cost() == 0) 
            {
                succesfulMoveCount = +moveCount;
                succesfulIterCount++;
                Is_Passed = true;
                 if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Destination State");
                print_NQueen_Board(board);
            } 
            else if (nqb_next.get_Heuristic_Cost() < board.get_Heuristic_Cost()) 
            {
                Count_Of_Side_Step =0;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                moveCount++;
                update_Current_Node = true;
            }  
            else if (nqb_next.get_Heuristic_Cost() == board.get_Heuristic_Cost() && Count_Of_Side_Step < Side_Moves_Count) 
            {
                Count_Of_Side_Step++;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                moveCount++;
                update_Current_Node = true;
            } 
            else 
            {
                board = get_Random_State(nqueen);
                set_Heuristic(board);
                totalRestartsCount++;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    System.out.println("restarted");
                    print_NQueen_Board(board);
                }
                restartFlag = true;
            }
            if (update_Current_Node) 
            {
                board = nqb_next;
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
        * This function get_Next_Best_Successor and is used to return the next possible best successor it accepts board_Array_list as parameter
        * This function returns the NQueen board with next best successor
    */
    public NQueenBoard get_Next_Best_Successor(ArrayList<NQueenBoard> board_Array_List) 
    {
        PriorityQueue<NQueenBoard> pq = new PriorityQueue<NQueenBoard>(board_Array_List.size(), new Heuristic_Cost_Comparator());
        for (int i = 0; i < board_Array_List.size(); i++) 
        {
            pq.add(board_Array_List.get(i));
        }
        List<NQueenBoard> get_Next_Best_Successors_List = new ArrayList<NQueenBoard>();
        NQueenBoard get_next_best_Successor = new NQueenBoard();
        get_next_best_Successor = pq.poll();
        int hcost = get_next_best_Successor.get_Heuristic_Cost();
        get_Next_Best_Successors_List.add(get_next_best_Successor);
        while (pq.peek().get_Heuristic_Cost() == hcost) 
        {
            get_Next_Best_Successors_List.add(pq.poll());
        }
        Random rand = new Random();
        int index = rand.nextInt(get_Next_Best_Successors_List.size());
        get_next_best_Successor = get_Next_Best_Successors_List.get(index);
        pq.clear();
        return get_next_best_Successor;
    }
    
    /*
        * get_Random_State is used for generating the random NQueen board it takes nqueen integer as input 
        * It returns a board with queens that are placed randomly
    */
    public NQueenBoard get_Random_State(int nqueen)
    {
        NQueenBoard board = new NQueenBoard();
        int[][] new_State = new int[nqueen][nqueen];
        Random random = new Random();
        for (int i=0;i<nqueen;i++){
            int rand_Number = random.nextInt(nqueen-1);
            for (int j=0;j<nqueen;j++){
                if(j == rand_Number){
                    new_State[j][i] = 1;
                } else {
                    new_State[j][i] = 0;
                }
            }
        }
        board.set_NQueen_Board(new_State);
        return board;
    }
    
    /*
        * This get_copied_State function is used to copy the current state of the NQueen board and accepts sorurce and destinations of NQueens Board to copy
    */
    public void get_Copied_State(int[][] source_Node_State, int[][] destination_Node_State) 
    {
        for (int i = 0; i < source_Node_State.length; i++)
        {
            for (int j = 0; j < source_Node_State.length; j++)
            {
                destination_Node_State[i][j] = source_Node_State[i][j];
            }
        }
    }

    /*
        * HillClimbrandomRestartWithoutSidewaysMove is used for performing the random restart hill climb algorithm without considering the sideway moves which take count of Queen as parameter and returns count of passed move, failed moves, iterations and no of required iterations
    */
    public int[]  Hill_Climb_Random_Restart_Without_Sideways_Move(int nqueen,int task_To_Be_Done)
    {
        int Count_Of_Side_Step = 0, Side_Moves_Count = 10;;
        int[] result = new int[6];
        ArrayList<NQueenBoard> next = new ArrayList<NQueenBoard>();
        NQueenBoard nqb_next = new NQueenBoard();
        boolean Is_Passed = false, Is_Fail = false, update_Current_Node = false;
        NQueenBoard board = get_Random_State(nqueen);
        set_Heuristic(board);
        System.out.println("Starting State");
        print_NQueen_Board(board);
        while (!Is_Passed) 
        {
            next = find_The_Successors(board);
            int i=0;
            while(i<next.size())
            {
                set_Heuristic(next.get(i));
                i++;
            }
            nqb_next = get_Next_Best_Successor(next);
            if (board.get_Heuristic_Cost() == 0) 
            {
                succesfulMoveCount = +moveCount;
                succesfulIterCount++;
                Is_Passed = true;
                 if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Destination State");
                print_NQueen_Board(board);
            } 
            else if (nqb_next.get_Heuristic_Cost() < board.get_Heuristic_Cost()) 
            {
                board = nqb_next;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                moveCount++;
            } else 
            {
                board = get_Random_State(nqueen);
                set_Heuristic(board);
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    System.out.println("*****restarted******");
                    print_NQueen_Board(board);
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
        * Hill_Climb_Search_With_Sideways_Move is used for performing the hill climb algorithm with considering the sideway moves it accepts nqeen integer as a parameter and returns an array type of passed move.
    */
    public int[]  Hill_Climb_Search_With_Sideways_Move(int nqueen,int task_To_Be_Done)
    {
        int side_Moves_count = 100;
        boolean is_Passed = false,is_Fail = false, update_Current_Node = false;;
        int count_Of_Side_Step = 0;
        ArrayList<NQueenBoard> successor = new ArrayList<NQueenBoard>();
        NQueenBoard nqb_successor = new NQueenBoard();
        int[] result = new int[4];
        NQueenBoard board = get_Random_State(nqueen);
        set_Heuristic(board);
        System.out.println("Starting State");
        print_NQueen_Board(board);
        while (!is_Fail && !is_Passed) 
        {
            update_Current_Node = false;
            successor = find_The_Successors(board);
            int i=0;
            while(i<successor.size())
            {
                set_Heuristic(successor.get(i));
                i++;
            }
            nqb_successor = get_Next_Best_Successor(successor);
            if (nqb_successor.get_Heuristic_Cost() == board.get_Heuristic_Cost()) 
            {
                nqb_successor = get_Next_Best_Successor(successor);
            }
            if (board.get_Heuristic_Cost() == 0) 
            {
                succesfulMoveCount = succesfulMoveCount + moveCount;
                succesfulIterCount++;
                is_Passed = true;
                 if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Destination State");
                print_NQueen_Board(board);
            } 
            else if (board.get_Heuristic_Cost()>nqb_successor.get_Heuristic_Cost()) 
            {
                // updating the current node of the nQueen with best possible successor
                count_Of_Side_Step =0;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                moveCount++;
                update_Current_Node = true;
            }  
            else if (board.get_Heuristic_Cost() == nqb_successor.get_Heuristic_Cost() && side_Moves_count>count_Of_Side_Step) 
            {
                // by moving the sideways 
                count_Of_Side_Step++;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                    print_NQueen_Board(board);
                }
                moveCount++;
                update_Current_Node = true;
            } 
            else 
            {
                failureIterCount++;
                failureMoveCount = +moveCount;
                is_Fail = true;
                if(task_To_Be_Done == 2) {
                    System.out.println("  |   ");
                    System.out.println("  V   ");
                }
                System.out.println("Local Minima State");
                print_NQueen_Board(board);
            }
            if (update_Current_Node) 
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
        * printNQueenBoard is used for printing NQueen board in the console by accepting parameter called b which is representing the NQueen board
    */
    public void print_NQueen_Board(NQueenBoard b)
    {
        for (int i = 0; i < b.get_NQueen_Board().length; i++) 
        {
            for (int j = 0; j < b.get_NQueen_Board().length; j++) 
            {
                String print_Value = "*";
                if ((b.get_NQueen_Board())[i][j] == 1) 
                {
                    print_Value = "Q";
                }
                System.out.print(print_Value+" ");
            }
            System.out.println();
        }
        int collisions = b.get_Heuristic_Cost();
        System.out.println("No.Of Collisions: "+ collisions);
        System.out.println();
    }    
    
}
