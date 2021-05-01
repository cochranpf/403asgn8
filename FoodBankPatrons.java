import java.util.concurrent.ThreadLocalRandom;

public class FoodBankPatrons{
    public static void main(String[] args){
        //creating bank, producer, and consumer
        FoodBank sharedBank = new FoodBank();
        FoodProdcuer prod = new FoodProdcuer(sharedBank);
        FoodConsumer con = new FoodConsumer(sharedBank);

        //starting producer and consumer
        prod.start();
        con.start();
    }
}


class FoodBank{
    private int food;

    //default constructor. sets food to 0
    public FoodBank(){
        this.food = 0;
    }

    //method to give food. returns new total
    public synchronized int giveFood(int foodIn){
        this.food = this.food + foodIn;
        return this.food;
    }

    //method to take food. returns new total
    public synchronized int takeFood(int foodOut){
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
        int foodIn;
        int newTotal;
        while(true){
            foodIn = ThreadLocalRandom.current().nextInt(1, 101);
            //waits
            System.out.println("Waiting to give food...");
            //locks
            newTotal = sharedBank.giveFood(foodIn);
            //unlocks
            System.out.println("Giving " + foodIn + 
                " items of food; the balance is now " + newTotal + ".");
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }

}

class FoodConsumer extends Thread{
    FoodBank sharedBank;

    public FoodConsumer(FoodBank fb){
        this.sharedBank = fb;
    }

    @Override
    public void run(){
        int foodOut;
        int newTotal;
        while(true){
            foodOut = ThreadLocalRandom.current().nextInt(1, 101);
            //waits
            System.out.println("Waiting to take food...");
            //locks
            newTotal = sharedBank.takeFood(foodOut);
            //unlocks
            if(newTotal != -403){
                System.out.println("Taking " + foodOut + 
                " items of food; the balance is now " + newTotal + ".");
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
            else{
                System.out.println("Failed to take " + foodOut + 
                " items of food; the balance was too low.");
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}