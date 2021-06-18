public class Cat extends Animal{

    int price;


    Cat(){
        this.price=150;
    }

    @Override
    public Dir move(Map[][] map,int x,int y){

        //finding nearest product
        int X=0;
        int Y=0;
        int min=0;
        Dir direction;
        boolean is_product_on_map=false;
        //initializing min
        for(int i=0 ; i<6 && !is_product_on_map ; i++){
            for(int j=0 ; j<6 && !is_product_on_map ; j++){
                if(!map[i][j].productsInside.isEmpty()) {
                    min=Math.abs(i-x)+Math.abs(j-y);
                    is_product_on_map=true;
                    X=i;
                    Y=j;
                }
            }
        }
        if(is_product_on_map) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    int m = Math.abs(i-x) + Math.abs(j-y);
                    if (m<min && !map[i][j].productsInside.isEmpty()){
                        min=m;
                        X=i;
                        Y=j;
                    }
                }
            }

            //finding direction
            //returns null if current location has product
            //(0,0) location is on top left of screen
            if(X>x && Y>y){
                double d=Math.random();
                if(d<0.5) direction=Dir.RIGHT;
                else direction=Dir.DOWN;
            }
            else if(X>x && Y<y){
                double d=Math.random();
                if(d<0.5) direction=Dir.RIGHT;
                else direction=Dir.UP;
            }
            else if(X<x && Y>y){
                double d=Math.random();
                if(d<0.5) direction=Dir.LEFT;
                else direction=Dir.DOWN;
            }
            else if(X<x && Y<y){
                double d=Math.random();
                if(d<0.5) direction=Dir.LEFT;
                else direction=Dir.UP;
            }
            else if(X==x && Y>y)
                direction=Dir.DOWN;

            else if(X==x && Y<y)
                direction=Dir.UP;

            else if(X>x && Y==y)
                direction=Dir.RIGHT;

            else if(X<x && Y==y)
                direction=Dir.LEFT;

            else //if(X==x && Y==y)
                direction=null;


            return direction;
        }

        //move random if the maps have no grass
        // (0,0) location is on top left of screen
        else return random_move(map,x,y);

    }




    @Override
    public int getPrice() {
        return price;
    }





}
