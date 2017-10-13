import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Testing
{

    @Test
    public void testRandom()
    {
        int k = 0;
        for (int i = 0 ; i < 1000 ;i ++)
        {
            if(DiceValue.getRandom().equals(DiceValue.SPADE)) k++;
        }
        assertTrue(k>0);
    }

    @Test
    public void  generalTest() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        Dice d1 = new Dice();
        Dice d2 = new Dice();
        Dice d3 = new Dice();

        Player player = new Player("Fred", 100);
        Game game = new Game(d1, d2, d3);
        List<DiceValue> cdv = game.getDiceValues();


            for (int i = 0; i < 100; i++)
            {
                String name = "Fred";
                int balance = 100;
                int limit = 0;
                player = new Player(name, balance);
                player.setLimit(limit);
                int bet = 5;


                System.out.println(String.format("Start Game %d: ", i));
                System.out.println(String.format("%s starts with balance %d, limit %d",
                        player.getName(), player.getBalance(), player.getLimit()));

                int turn = 0;
                while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200)
                {
                    game = new Game(new Dice(), new Dice(), new Dice());
                    turn++;
                    DiceValue pick = DiceValue.getRandom();

                    System.out.printf("Turn %d: %s bet %d on %s\n",
                            turn, player.getName(), bet, pick);

                    int previousBalance = player.getBalance();
                    int winnings = game.playRound(player, pick, bet);
                    cdv = game.getDiceValues();

                    System.out.printf("Rolled %s, %s, %s\n",
                            cdv.get(0), cdv.get(1), cdv.get(2));

                    if (winnings > 0) {
                        System.out.printf("%s won %d, balance now %d\n\n",
                                player.getName(), winnings, player.getBalance());


                        //checking that winning always increase our balance
                        assertTrue(previousBalance < player.getBalance());
                    }
                    else {
                        System.out.printf("%s lost, balance now %d\n\n",
                                player.getName(), player.getBalance());

                        //checking that losing always decrease our balance
                        assertTrue(previousBalance > player.getBalance());
                    }

                } //while

                System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i));
                System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));

            } //for

    }
}

