/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved. NTS PROPRIETARY/CONFIDENTIAL. Use is
 * subject to NTS License Agreement. Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria Homepage:
 * www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.application.map;

import java.io.Serializable;

import com.mg.search.client.application.map.service.AStarSearchService;

/**
 * Class used by {@link AStarSearchService} for communicating via its callback with the class that listens for
 * events from it (e.g. : cell added to the open list, cell added to the closed list, path found, etc.)
 * 
 * @author mga
 * 
 */
public class Square implements Serializable {
    
    private static final long serialVersionUID = -4239558044665071827L;
    
    private int i;
    private int j;
    private int gcost;
    private int hcost;

    private Square parent;

    public Square(){}
    
    public Square(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * @return the i
     */
    public int getI() {
        return i;
    }

    /**
     * @param i the i to set
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * @return the j
     */
    public int getJ() {
        return j;
    }

    /**
     * @param j the j to set
     */
    public void setJ(int j) {
        this.j = j;
    }

    /**
     * @return the gcost
     */
    public int getGcost() {
        return gcost;
    }

    /**
     * @param gcost the gcost to set
     */
    public void setGcost(int gcost) {
        this.gcost = gcost;
    }

    /**
     * @return the hcost
     */
    public int getHcost() {
        return hcost;
    }

    /**
     * @param hcost the hcost to set
     */
    public void setHcost(int hcost) {
        this.hcost = hcost;
    }

    /**
     * @return the parent
     */
    public Square getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Square parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Square [i=").append(i).append(", j=").append(j).append(", gcost=").append(gcost)
                .append(", hcost=").append(hcost);

        if (parent != null) {
            sb.append(", parent[i = ").append(parent.i).append(", j=").append(parent.j).append("]");
        }
        sb.append("]");
        
        return sb.toString();
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + i;
        result = prime * result + j;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square other = (Square) obj;
        if (i != other.i)
            return false;
        if (j != other.j)
            return false;
        return true;
    }
    
    

}
