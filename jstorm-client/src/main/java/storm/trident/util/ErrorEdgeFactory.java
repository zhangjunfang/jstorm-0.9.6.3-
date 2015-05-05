package storm.trident.util;

import java.io.Serializable;

import org.jgrapht.EdgeFactory;
@SuppressWarnings({"rawtypes"})
public class ErrorEdgeFactory implements EdgeFactory, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3602257221248838482L;

	@Override
    public Object createEdge(Object v, Object v1) {
        throw new RuntimeException("Edges should be made explicitly");
    }        
}
