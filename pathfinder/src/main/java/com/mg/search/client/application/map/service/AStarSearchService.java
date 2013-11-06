/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved. NTS PROPRIETARY/CONFIDENTIAL. Use is
 * subject to NTS License Agreement. Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria Homepage:
 * www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.application.map.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.mg.search.client.application.map.Square;

/**
 * <p>
 * Service used for finding the path between two points (start, destination) within a matrix in which can
 * exist obstacles.
 * <p>
 * 
 * <p>
 * Since the service is supposed to be used within a GWT application in such a manner so that the user of the
 * application can easily identify with how the algorithm advances, a GWT {@link Timer} will be used for
 * pausing its execution after each iteration. In order to allow the user to adjust the speed at which the
 * algorithm iterations are executed, the service exposes a method for setting this speed.
 * </p>
 * 
 * @author mga
 * 
 */
public class AStarSearchService {

    public static interface FindPathCallback {
        void onSquareAddedToOpenList(Square square);

        void onOpenListSquareUpdated(Square square);

        void onSquareAddedToClosedList(Square square);

        void onPathFound(List<Square> path);

        void onPathNotFound();

    }

    private int speed = 700;

    private static int hcost(int i, int j, int desti, int destj) {
        return Math.abs(desti - i) * 10 + Math.abs(destj - j) * 10;
    }

    private void
            addToOpenList(List<Square> openList, List<Square> closedList, boolean[][] m,
                    Square currentSquare, int i, int j, int distance, int desti, int destj,
                    FindPathCallback callback) {
        if (m[i][j] != true) {
            // only non-obstacle squares are taken into account
            Square square = new Square(i, j);
            if (!closedList.contains(square)) {
                if (openList.contains(square)) {
                    int index = openList.indexOf(square);
                    square = openList.get(index);
                    if (currentSquare.getGcost() + distance < square.getGcost()) {
                        // a shorter path was found
                        square.setParent(currentSquare);
                        square.setGcost(currentSquare.getGcost() + distance);
                        callback.onOpenListSquareUpdated(square);
                    }
                } else {
                    square.setGcost(currentSquare.getGcost() + distance);
                    square.setHcost(hcost(i, j, desti, destj));
                    square.setParent(currentSquare);
                    openList.add(square);
                    if (callback != null) {
                        callback.onSquareAddedToOpenList(square);
                    }
                }
            }
        }
    }

    public Timer findPath(final boolean[][] m, final int starti, final int startj, final int desti,
            final int destj, final FindPathCallback callback) {
        final List<Square> openList = new ArrayList<Square>();
        final List<Square> closedList = new ArrayList<Square>();

        Square startSquare = new Square(starti, startj);
        startSquare.setHcost(hcost(starti, startj, desti, destj));
        openList.add(startSquare);

        final int straightDistance = 10;
        final int diagonalDistance = 14;

        Timer timer = new Timer() {
            @Override
            public void run() {

                if (openList.size() == 0) {
                    // there are no more nodes to investigate, no path to the destination was found
                    callback.onPathNotFound();
                    return;
                }

                // Sort the elements of the open list after the lowest cost of the squares
                Collections.sort(openList, new Comparator<Square>() {

                    @Override
                    public int compare(Square arg0, Square arg1) {
                        int arg0Cost = arg0.getGcost() + arg0.getHcost();
                        int arg1Cost = arg1.getGcost() + arg1.getHcost();

                        if (arg0Cost > arg1Cost) {
                            return 1;
                        } else if (arg0Cost < arg1Cost) {
                            return -1;
                        }
                        return 0;
                    }
                });

                Square currentSquare = openList.get(0);
                if (callback != null) {
                    callback.onSquareAddedToClosedList(currentSquare);
                }

                // switch currentSquare from openList to closeList
                openList.remove(0);
                closedList.add(currentSquare);

                if (currentSquare.getI() == desti && currentSquare.getJ() == destj) {
                    // path to the destination was found
                    callback.onPathFound(buildPath(currentSquare));
                    return;
                }

                // retrieve the adjacent nodes and add them to the open list
                // . . .
                // . x .
                // . . .
                if (currentSquare.getJ() > 0) {
                    if (currentSquare.getI() > 0) {
                        if (m[currentSquare.getI() - 1][currentSquare.getJ()] != true
                                && m[currentSquare.getI()][currentSquare.getJ() - 1] != true) {
                            // avoid doing diagonal steps next to the obtactles (when going upwards-back on
// the
                            // diagonal)
                            // . |
                            // | x
                            addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() - 1,
                                    currentSquare.getJ() - 1, diagonalDistance, desti, destj, callback);
                        }
                    }
                    addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI(),
                            currentSquare.getJ() - 1, straightDistance, desti, destj, callback);
                    if (currentSquare.getI() < m.length - 1) {
                        if (m[currentSquare.getI() + 1][currentSquare.getJ()] != true
                                && m[currentSquare.getI()][currentSquare.getJ() - 1] != true) {
                            addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() + 1,
                                    currentSquare.getJ() - 1, diagonalDistance, desti, destj, callback);
                        }
                    }
                }

                if (currentSquare.getI() > 0) {
                    addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() - 1,
                            currentSquare.getJ(), straightDistance, desti, destj, callback);
                }
                if (currentSquare.getI() < m.length - 1) {
                    addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() + 1,
                            currentSquare.getJ(), straightDistance, desti, destj, callback);
                }

                if (currentSquare.getJ() < m[0].length - 1) {
                    if (currentSquare.getI() > 0) {
                        if (m[currentSquare.getI() - 1][currentSquare.getJ()] != true
                                && m[currentSquare.getI()][currentSquare.getJ() + 1] != true) {
                            addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() - 1,
                                    currentSquare.getJ() + 1, diagonalDistance, desti, destj, callback);
                        }
                    }
                    addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI(),
                            currentSquare.getJ() + 1, straightDistance, desti, destj, callback);
                    if (currentSquare.getI() < m.length - 1) {
                        if (m[currentSquare.getI() + 1][currentSquare.getJ()] != true
                                && m[currentSquare.getI()][currentSquare.getJ() + 1] != true) {
                            addToOpenList(openList, closedList, m, currentSquare, currentSquare.getI() + 1,
                                    currentSquare.getJ() + 1, diagonalDistance, desti, destj, callback);
                        }
                    }
                }

                this.schedule(speed);
            }
        };

        timer.schedule(speed);

        return timer;

    }

    public void setSpeed(int speed) {
        this.speed = Math.abs(700 - speed * 100);
    }

    private List<Square> buildPath(Square destinationSquare) {
        final List<Square> path = new ArrayList<Square>();
        Square currentSquare = destinationSquare;
        do {
            path.add(currentSquare);
            currentSquare = currentSquare.getParent();
        } while (currentSquare != null);
        Collections.reverse(path);

        return path;
    }

}
