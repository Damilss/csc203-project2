/**
 * filename: Tests.java
 * description: test cases for all major methods
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

public class Tests {

    public static void main(String[] args) {

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // addEntity()
        System.out.println("--- Testing addEntity() ---");
        System.out.println("Testing addEntity() - adding a Rock at (0,0)");
        System.out.println("Expected: map[0][0] = R");
        World testWorld = new World(5, 5, 0);
        testWorld.addEntity("R", 0, 0);
        System.out.println("Actual: map[0][0] = " + testWorld.getCell(0, 0));
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // removeEntity()
        System.out.println("--- Testing removeEntity() ---");
        System.out.println("Testing removeEntity() - removing Rock at (0,0)");
        System.out.println("Expected: map[0][0] = null");
        World removeWorld = new World(5, 5, 0);
        removeWorld.addEntity("R", 0, 0);
        removeWorld.removeEntity(0, 0);
        System.out.println("Actual: map[0][0] = " + removeWorld.getCell(0, 0));
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // printWorld()
        System.out.println("--- Testing printWorld() ---");
        System.out.println("Testing printWorld() - 5x5 world with R at (0,0) and S at (1,1)");
        System.out.println("Expected: R at top-left, S one cell diagonal, rest dots");
        World printTestWorld = new World(5, 5, 0);
        printTestWorld.addEntity("R", 0, 0);
        printTestWorld.addEntity("S", 1, 1);
        System.out.println("Actual:");
        printTestWorld.printWorld();
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // playRound()
        System.out.println("--- Testing playRound() ---");
        System.out.println("Testing playRound() - entities should move after one round");
        World roundWorld = new World(5, 5, 0);
        roundWorld.addEntity("R", 2, 2);
        roundWorld.addEntity("S", 4, 4);
        System.out.println("Expected: R and S have moved from their starting positions (may vary)");
        System.out.println("Before:");
        roundWorld.printWorld();
        roundWorld.playRound();
        System.out.println("Actual (after one round):");
        roundWorld.printWorld();
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // moveRock()
        System.out.println("--- Testing moveRock() ---");
        System.out.println("Testing moveRock() - Rock at (2,2), expected to move to a neighbor of (2,2)");
        System.out.println("Expected: one of (1,2) (3,2) (2,1) (2,3)");
        World rockMoveWorld = new World(5, 5, 0);
        rockMoveWorld.addEntity("R", 2, 2);
        rockMoveWorld.playRound();
        System.out.println("Actual:");
        rockMoveWorld.printWorld();
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // rockAttack()
        System.out.println("--- Testing rockAttack() ---");
        System.out.println("Testing rockAttack() - Rock at (1,1), Scissors at (1,2) on 3x3 grid");
        System.out.println("Expected: scissorsCount decreases by 1 after attack");
        World rockAttackWorld = new World(3, 3, 0);
        rockAttackWorld.addEntity("R", 1, 1);
        rockAttackWorld.addEntity("S", 1, 2);
        int scissorsBefore = entities.Scissors.scissorsCount;
        System.out.println("Scissors count before: " + scissorsBefore);
        for(int i = 0; i < 10; i++){
            rockAttackWorld.playRound();
        }
        System.out.println("Actual scissors count after: " + entities.Scissors.scissorsCount);
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // movePaper()
        System.out.println("--- Testing movePaper() ---");
        System.out.println("Testing movePaper() - Paper at (2,2), expected to move to a neighbor of (2,2)");
        System.out.println("Expected: one of (1,2) (3,2) (2,1) (2,3)");
        World paperMoveWorld = new World(5, 5, 0);
        paperMoveWorld.addEntity("P", 2, 2);
        paperMoveWorld.playRound();
        System.out.println("Actual:");
        paperMoveWorld.printWorld();
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // paperAttack()
        System.out.println("--- Testing paperAttack() ---");
        System.out.println("Testing paperAttack() - Paper at (1,1), Rock at (1,2) on 3x3 grid");
        System.out.println("Expected: rockCount decreases by 1 after attack");
        World paperAttackWorld = new World(3, 3, 0);
        paperAttackWorld.addEntity("P", 1, 1);
        paperAttackWorld.addEntity("R", 1, 2);
        int rockBefore = entities.Rock.rockCount;
        System.out.println("Rock count before: " + rockBefore);
        for(int i = 0; i < 10; i++){
            paperAttackWorld.playRound();
        }
        System.out.println("Actual rock count after: " + entities.Rock.rockCount);
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // moveScissors()
        System.out.println("--- Testing moveScissors() ---");
        System.out.println("Testing moveScissors() - Scissors at (2,2), expected to move to a neighbor of (2,2)");
        System.out.println("Expected: one of (1,2) (3,2) (2,1) (2,3)");
        World scissorsMoveWorld = new World(5, 5, 0);
        scissorsMoveWorld.addEntity("S", 2, 2);
        scissorsMoveWorld.playRound();
        System.out.println("Actual:");
        scissorsMoveWorld.printWorld();
        System.out.println();

        // reset
        entities.Rock.positionById.clear();
        entities.Rock.idByPosition.clear();
        entities.Paper.positionById.clear();
        entities.Paper.idByPosition.clear();
        entities.Scissors.positionById.clear();
        entities.Scissors.IdByPosition.clear();
        entities.Rock.rockCount = 0;
        entities.Rock.nextRockId = 0;
        entities.Paper.paperCount = 0;
        entities.Paper.nextPaperId = 0;
        entities.Scissors.scissorsCount = 0;
        entities.Scissors.nextScissorsId = 0;

        // scissorAttack()
        System.out.println("--- Testing scissorAttack() ---");
        System.out.println("Testing scissorAttack() - Scissors at (1,1), Paper at (1,2) on 3x3 grid");
        System.out.println("Expected: paperCount decreases by 1 after attack");
        World scissorsAttackWorld = new World(3, 3, 0);
        scissorsAttackWorld.addEntity("S", 1, 1);
        scissorsAttackWorld.addEntity("P", 1, 2);
        int paperBefore = entities.Paper.paperCount;
        System.out.println("Paper count before: " + paperBefore);
        for(int i = 0; i < 10; i++){
            scissorsAttackWorld.playRound();
        }
        System.out.println("Actual paper count after: " + entities.Paper.paperCount);
        System.out.println();

    }
}