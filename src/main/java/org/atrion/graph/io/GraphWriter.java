package org.atrion.graph.io;

import org.atrion.graph.Graph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class GraphWriter {

    public static void writeObject(Graph graph,String fileName) throws IOException {

        File file = new File(fileName);

        FileOutputStream fileOut    =  new FileOutputStream(file);
        ObjectOutputStream out      =  new ObjectOutputStream(fileOut);

        out.writeObject(graph);
        out.close();
        fileOut.close();
    }
}
