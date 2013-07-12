package org.atrion.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class AtrionRecommendation {

    private final List<AtrionEntry> list;
    private final int k;

    public AtrionRecommendation(List<AtrionEntry> list,int k) {
        this.k = k;
        this.list = recommendation(list,k);
    }

    public List<AtrionEntry> recommendation(List<AtrionEntry> list,int k){
        Collections.sort(list);

        List<AtrionEntry> sortedList = new ArrayList<AtrionEntry>();
        for(int i = 0;i<list.size();i++){
            sortedList.add(list.get(i));
            if(i == k-1){
                break;
            }
        }

        return sortedList;

    }

    public List<AtrionEntry> getList() {
        return list;
    }

    public int getK() {
        return k;
    }

    @Override
    public String toString() {
        return "AtrionRecommendation{" +
                "list=" + list +
                ", k=" + k +
                '}';
    }
}
