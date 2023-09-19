package graphics.vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class VertexLayout implements Iterable<VertexAttribute>{

    private ArrayList<VertexAttribute> elements;
    private int stride;

    public VertexLayout(VertexAttribute... elements) {
        this.elements = new ArrayList<>(Arrays.asList(elements));
        for(VertexAttribute element: this.elements) {
            stride += element.size;
        }
    }

    public ArrayList<VertexAttribute> getElements() {
        return elements;
    }

    public int getStride() {
        return stride;
    }

    @Override
    public Iterator<VertexAttribute> iterator() {
        return elements.iterator();
    }


}
