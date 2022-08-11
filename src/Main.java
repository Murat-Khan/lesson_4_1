import java.util.Random;

public class Main {
    public static int bossHealth = 2000;    // здаровье//
    public static int bossDamage = 50;    // Наносить ущерб//
    public static String bossDefence;     // защита //
    public static int[] heroesHealth = {280, 270, 250,300 , 500 , 200 , 150 , 180};
    public static int[] heroesDamage = {10, 15, 20 , 0, 5, 7 , 17 ,10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic" , "Medic" , "Golem", "Lucky" , "Berserk" ,"Thor"};
    public static int roundNumber = 0;

   static Random random = new Random();


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }


    public static void playRound() {
        heroesDamage[6] = 17;
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        treatment();
        defenceGolem();
        lucki();
        blockBerserk();
        Thor();
        printStatistics();
        bossDamage = 50;
    }

    private static void Thor( ) {
        boolean putToSleep = random.nextBoolean();
                if (heroesHealth[7] > 0){
                    if (putToSleep){
                        bossDamage = 0;
                        System.out.println("Boss is Sleep");
                    }
                }
    }

    private static void blockBerserk() {
        Random random = new Random();
        int randomBlock = random.nextInt(25);
        if (heroesHealth[6] > 0) {
            heroesHealth[6] += randomBlock;
            heroesDamage[6] += randomBlock;
            System.out.println("Berserk: Block and Damage " + randomBlock);
        }
    }

    private static void lucki() {
        Random random = new Random();
        boolean a = random.nextBoolean();
        if (a){
            heroesHealth[5] += bossDamage;
            System.out.println("Lucki is evaded ");
        }
    }

    private static void defenceGolem() {
        bossDamage -=bossDamage / 5;
        System.out.println("Boss damage " + bossDamage );
    }

    public static void treatment(){
        Random random = new Random();
        int randomHeal  = random.nextInt(100);
        if (heroesHealth[3] > 0 ){
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] != heroesHealth[3] && heroesHealth[i] != 0){
                    heroesHealth[i] = heroesHealth[i] + randomHeal;
                    System.out.println("Medic heal: " + heroesAttackType[i]);
                    System.out.println(randomHeal);
                    break;
                }

            }
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int healthOfCurrentHero : heroesHealth) {
            if (healthOfCurrentHero > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        if (roundNumber == 0) {
            System.out.println("BEFORE START -------------");
        } else {
            System.out.println("ROUND " + roundNumber + " -------------");
        }
        /*String value;
        if (bossDefence == null) {
            value = "No defence";
        } else {
            value = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + "; damage: "
                + bossDamage + "; defence: "
                + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + "; damage: " + heroesDamage[i]);
        }
    }
}
