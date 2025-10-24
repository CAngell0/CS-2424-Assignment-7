package assign07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timing.TimingExperiment;

public class ArraySortedSetAddAllRandomOrderTimingExperiment extends TimingExperiment {
    protected ArraySortedSet<Integer> sortedSet = new ArraySortedSet<Integer>();
    protected List<Integer> elementsToAdd = new ArrayList<Integer>();

    public static void main(String[] args) {
        int iterationCount = 50;
        int warmup = 20;
        List<Integer> problemSizes = buildProblemSizes(10000, 1000, 200);
        // List<Integer> problemSizes = buildProblemSizes(10000, 1000, 40);

        TimingExperiment experiment = new ArraySortedSetAddAllRandomOrderTimingExperiment("setSize", problemSizes, iterationCount);

        experiment.warmup(warmup);
        experiment.run();
        experiment.print();
        experiment.writeToCSV("timing.csv");
    }

    public ArraySortedSetAddAllRandomOrderTimingExperiment(String problemSizeName, List<Integer> problemSizes, int iterationCount) {
        super(problemSizeName, problemSizes, iterationCount);
    }

    @Override
    protected void setupExperiment(int problemSize) {
        sortedSet.clear();
        elementsToAdd.clear();

        Random rand = new Random();
        ArrayList<Integer> numberPool = new ArrayList<>();
        for (int number = 0; number < problemSize; number++){
            numberPool.add(number);
            elementsToAdd.add(null);
        }
        for (int index = 0; index < elementsToAdd.size(); index++){
            int targetPoolIndex = rand.nextInt(numberPool.size());
            elementsToAdd.set(index, numberPool.get(targetPoolIndex));
            numberPool.remove(targetPoolIndex);
        }
    }

    @Override
    protected void runComputation() {
        sortedSet.addAll(elementsToAdd);
    }
}
