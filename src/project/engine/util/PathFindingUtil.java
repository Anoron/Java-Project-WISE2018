package project.engine.util;

import project.engine.misc.Misc;

import java.util.*;

public class PathFindingUtil {

    private PathFindingUtil(){}


    /*
    Breadth First Search Algorithm
    Rather inefficient but always provides the optimal solution.
    Should be replaced with A*
     */

    private static HashMap<Point2, Point2> searchPath(Point2 pos, Point2 target){

        /*
        Stores our points to visit.
         */
        Queue<Point2> queue = new LinkedList<>();

        /*
        Makes sure we don't explore the same point twice.
         */
        ArrayList<Point2> explored = new ArrayList<>();

        /*
        Stores the previous point for each new point searched.
        Required to reconstruct the path later on.
         */
        HashMap<Point2, Point2> prev = new HashMap<>();

        /*
        First we add the current pos to the queue.
         */
        queue.add(pos);

        if(Misc.debugLevel > 3) {
        System.out.println("Path Start: " + pos);
        System.out.println("Path Target: " + target);
        }
        /*
        We loop over the queue until it is empty.
        If it finds the target before that, a path is possible.
        If it runs empty, path is not possible.
         */
        while(!queue.isEmpty()){

            Point2 current = queue.remove();
            explored.add(current);

            if(current.equals(target)){
                return prev;

            }else{

                for(Point2 p : current.getNeighbours()){
                    if(!explored.contains(p) && BoardUtil.isMoveInGrid(p) && (BoardUtil.isEmpty(p) || p.equals(target)) && !queue.contains(p)){
                        queue.add(p);
                        prev.put(p, current);
                        if(Misc.debugLevel > 3) {
                        System.out.println(p + " Size: " + queue.size());
                        }
                    }
                }
            }
        }
        return prev;
    }


    /*
    Returns a list of moves required to reach the target.
    Reverses the order of our searchPath using previousList
     */

    public static List<Point2> getPathList(Point2 pos, Point2 target){

        HashMap<Point2, Point2> prev =  searchPath(pos, target);
        ArrayList<Point2> way = new ArrayList<>();

        if(prev != null && !prev.isEmpty()) {

            way.add(target);
            Point2 prevPos = prev.get(target);

            if(Misc.debugLevel > 3) {
                System.out.println("Previous Movepath: " + prevPos);
            }

            if (prevPos != null) {
                way.add(prevPos);

                Point2 pre = prev.get(way.get(way.size()-1));
                while(pre != null){
                    way.add(pre);
                    pre  = prev.get(way.get(way.size()-1));
                }

            }

            Collections.reverse(way);

            if(Misc.debugLevel > 3) {
                System.out.println("MovePathList: " + way);
            }

            return way;
        }
        return way;
    }

}
