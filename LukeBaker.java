public class LukeBaker{
    
    public static int move(int[][]g, int m){
        if(g[5][3] == m%2+1 && g[5][4] == 0){
            return 4;
        }
        if(g[5][3] == 0){
            return 3;
        }
        for(int k=0; k<7; k++){
            ConnectFour game = new ConnectFour();
            for(int i=g.length-1; i>=0; i--){
                for(int j=g[i].length-1; j>=0; j--){
                    if(g[i][j] == 1){
                        game.drop(1,j);
                    }else if(g[i][j] == 2){
                        game.drop(2,j);
                    }
                }
            }
            game.drop(m,k);
            if(game.determineWin() == m){
                return k;
            }
        }
        for(int k=0; k<7; k++){
            ConnectFour game = new ConnectFour();
            for(int i=g.length-1; i>=0; i--){
                for(int j=g[i].length-1; j>=0; j--){
                    if(g[i][j] == 1){
                        game.drop(1,j);
                    }else if(g[i][j] == 2){
                        game.drop(2,j);
                    }
                }
            }
            game.drop(m%2+1,k);
            if(game.determineWin() == m%2+1){
                return k;
            }
        }
        int bestMove = -1;
        int highPoints = 0;
        for(int w=0; w<7; w++){
            int points = 0;
            for(int k=0; k<7; k++){
                ConnectFour game = new ConnectFour();
                for(int i=g.length-1; i>=0; i--){
                    for(int j=g[i].length-1; j>=0; j--){
                        if(g[i][j] == 1){
                            game.drop(1,j);
                        }else if(g[i][j] == 2){
                            game.drop(2,j);
                        }
                    }
                }
                if(game.drop(m,w)){
                    points++;
                    if(game.drop(m%2+1, k) && game.determineWin() == m%2+1){
                        points--;
                    }else if(game.drop(m%2+1, k) && game.determineWin() != m%2+1){
                        points++;
                    }
                    if(points > highPoints){
                        highPoints = points;
                        bestMove = w;
                    }
                }
            }
        }
        return bestMove;
        // ConnectFour gamer = new ConnectFour();
        // for(int k=0; k<7; k++){
        //     for(int i=g.length-1; i>=0; i--){
        //         for(int j=g[i].length-1; j>=0; j--){
        //             if(g[i][j] == 1){
        //                 gamer.drop(1,j);
        //             }else if(g[i][j] == 2){
        //                 gamer.drop(2,j);
        //             }
        //         }
        //     }
        // }
        
        
        // for(int i=0; i<7; i++){ 
        //     System.out.println("hey gamer");
        //     if(gamer.drop(m,i)){
        //         if(gamer.drop(m%2+1,j) && gamer.determineWin() != m%2+1){

        //         return i;
        //     }
        // }
        // return (int) (Math.random()*7);
        // }
    }
}

