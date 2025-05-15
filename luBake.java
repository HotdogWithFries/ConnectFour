public class luBake{
    
    public static int jorker(int[][]g, int m){
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

        for(int w=0; w<7; w++){
            int broDude = (int)(Math.random()*7);
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
                if(game.drop(m,broDude)){
                    if(game.drop(m%2+1, k) && game.determineWin() != m%2+1){
                        return broDude;
                    }
                }
                
                
            }
        }
        ConnectFour gamer = new ConnectFour();
        for(int k=0; k<7; k++){
            for(int i=g.length-1; i>=0; i--){
                for(int j=g[i].length-1; j>=0; j--){
                    if(g[i][j] == 1){
                        gamer.drop(1,j);
                    }else if(g[i][j] == 2){
                        gamer.drop(2,j);
                    }
                }
            }
        }
        int skibba = (int) (Math.random()*7);
        while(!gamer.drop(m,skibba)){
            skibba = (int) (Math.random()*7);
        }
        return skibba;
    }
}
