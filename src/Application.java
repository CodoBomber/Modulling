import events.MarkovChain;
import generators.markov.RowBasedGenerator;
import generators.markov.TwoStochasticGenerator;

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

     /*   EventTreeGenerator etg = new EventTreeGenerator(4, 6, 2);
        etg.generate();
        System.out.println("###################EDGES#####################");
        etg.printEdges();*/

        // ************************LAB 5*********************************//
        MarkovChain chain = new MarkovChain(new RowBasedGenerator(5));
        chain.multiply();
        chain.prettyPrint();

        MarkovChain chain2 = new MarkovChain(new TwoStochasticGenerator(4));
        chain2.multiply();
        chain2.prettyPrint();

    }

}
