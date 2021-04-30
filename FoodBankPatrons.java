import java.util.concurrent.ThreadLocalRandom;

public class FoodBankPatrons{
    public static void main(String[] args){

    }
}


class FoodBank{
    private int food;

    //default constructor. sets food to 0
    public FoodBank(){
        this.food = 0;
    }

    //method to give food. returns new total
    public int giveFood(int foodIn){
        this.food = this.food + foodIn;
        return this.food;
    }

    //method to take food. returns new total
    public int takeFood(int foodOut){
        if (foodOut <= this.food){ //if there is enough food for request
            this.food = this.food - foodOut;
            return this.food;
        }
        else{ //if there is NOT enough food for request
            return -403;
        }
    }
}

class FoodProdcuer extends Thread{
    FoodBank sharedBank;

    public FoodProdcuer(FoodBank fb){
        this.sharedBank = fb;
    }

    @Override
    public void run(){
        int foodIn = ThreadLocalRandom.current().nextInt(1, 101);
        //Need to use locks, try giving, then unlocking
    }

}

class FoodConsumer extends Thread{
    FoodBank sharedBank;

    public FoodConsumer(FoodBank fb){
        this.sharedBank = fb;
    }

    @Override
    public void run(){
        int foodOut = ThreadLocalRandom.current().nextInt(1, 101);
        //need to use locks, try taking, then unlocking
    }
}