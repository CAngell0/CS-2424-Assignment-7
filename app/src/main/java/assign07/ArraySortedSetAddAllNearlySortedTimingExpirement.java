package assign07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timing.TimingExperiment;

public class ArraySortedSetAddAllNearlySortedTimingExpirement extends TimingExperiment {
    protected ArraySortedSet<Integer> sortedSet = new ArraySortedSet<Integer>();
    protected List<Integer> elementsToAdd = new ArrayList<Integer>();

    public static void main(String[] args) {
        int iterationCount = 50;
        int warmup = 20;
        List<Integer> problemSizes = buildProblemSizes(10000, 1000, 200);
        // List<Integer> problemSizes = buildProblemSizes(10000, 1000, 40);

        TimingExperiment experiment = new ArraySortedSetAddAllNearlySortedTimingExpirement("setSize", problemSizes, iterationCount);

        experiment.warmup(warmup);
        experiment.run();
        experiment.print();
        experiment.writeToCSV("timing.csv");
    }

    public ArraySortedSetAddAllNearlySortedTimingExpirement(String problemSizeName, List<Integer> problemSizes, int iterationCount) {
        super(problemSizeName, problemSizes, iterationCount);
    }

    @Override
    protected void setupExperiment(int problemSize) {
        sortedSet.clear();
        elementsToAdd.clear();
        for (int number = 0; number < problemSize; number++) elementsToAdd.add(number);
        
        Random rand = new Random();
        for (int i = 0; i < (int) (problemSize / 4); i++){
            int index1 = rand.nextInt(problemSize);
            int index2 = rand.nextInt(problemSize);

            Integer temp = elementsToAdd.get(index1);
            elementsToAdd.set(index1, elementsToAdd.get(index2));
            elementsToAdd.set(index2, temp);
        }
    }

    @Override
    protected void runComputation() {
        sortedSet.addAll(elementsToAdd);
    }
}
