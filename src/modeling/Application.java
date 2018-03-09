package modeling;

import modeling.generators.distribution.ConstantDistribution;
import modeling.generators.distribution.Distribution;
import modeling.generators.distribution.ExponentialDistribution;
import modeling.generators.distribution.UniformDistribution;

public class Application {

    public static void main(String[] args) {
        // ************************LAB 1*********************************//
        //PVSGenerator pvs = new PVSGenerator(10);
        // ************************LAB 2*********************************//
        /*
        ImmutablePicking ip = new ImmutablePicking(pvs);
        MutablePicking mp = new MutablePicking(pvs);
        pvs.prettyPrint();
        System.out.println("$");
        ip.pick(10000);
        System.out.println("$");
        mp.pick(1, 10000);
        System.out.println("$");
        mp.pick(4, 10000);
        System.out.println("$");
        mp.pick(9, 10000);
        System.out.println("$");*/

        // ************************LAB 3*********************************//
        /*EventTree eventTree = new EventTree(6, 3);
        eventTree.buildTree();
        eventTree.printEdges();
        eventTree.printMaxDegreeVertexes();*/

        // ************************LAB 4*********************************//

        /*EventTreeGenerator etg = new EventTreeGenerator(5, 6, 3);
        etg.generate();
        System.out.println("###################EDGES#####################");
        etg.printEdges();*/

        // ************************LAB 5*********************************//
        /*MarkovChain chain = new MarkovChain(new RowBasedGenerator(5));
        chain.prettyPrint();
        chain.multiply();
        chain.prettyPrint();

        MarkovChain chain2 = new MarkovChain(new TwoStochasticGenerator(4));
        chain2.prettyPrint();
        chain2.multiply();
        chain2.prettyPrint();*/
        // ************************LAB 6*********************************//
        double expectedValue = 1.7;
        Distribution constant = new ConstantDistribution(expectedValue);
        Distribution uniform = new UniformDistribution(expectedValue);
        Distribution exponential = new ExponentialDistribution(expectedValue);
        double expectedValue2 = 1.9;
        Distribution constant2 = new ConstantDistribution(expectedValue2);
        Distribution uniform2 = new UniformDistribution(expectedValue2);
        Distribution exponential2 = new ExponentialDistribution(expectedValue2);

        System.out.println("Const: " + constant.getNextValue());
        System.out.println("Uniform: " + uniform.getNextValue());
        System.out.println("Expo: " + exponential.getNextValue());

        System.out.println("Const: " + constant2.getNextValue());
        System.out.println("Uniform: " + uniform2.getNextValue());
        System.out.println("Expo: " + exponential2.getNextValue());
    }

}
