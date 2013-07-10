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
        this.list = recommendation(k);
    }

    public List<AtrionEntry> recommendation(int k){
        Collections.sort(list);

        List<AtrionEntry> sortedList = new ArrayList<AtrionEntry>();
        for(int i = list.size()-1;i>=0;i--){
            sortedList.add(list.get(i));
        }

        return sortedList;

    }

    public List<AtrionEntry> getList() {
        return list;
    }

    public int getK() {
        return k;
    }
}
