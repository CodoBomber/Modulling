import generators.EventTreeGenerator;

public class Application {

    public static void main(String[] args) {
        /*PVSGenerator pvs = new PVSGenerator(10);
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

        /*EventTree eventTree = new EventTree(6, 3);
        eventTree.buildTree();
        eventTree.printEdges();
        eventTree.printMaxDegreeVertexes();*/

        EventTreeGenerator etg = new EventTreeGenerator(4, 6, 2);
        etg.generate();
        System.out.println("###################EDGES#####################");
        etg.printEdges();
    }

}
