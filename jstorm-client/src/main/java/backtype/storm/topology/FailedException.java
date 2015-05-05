package backtype.storm.topology;

public class FailedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1318407909762835892L;

	public FailedException() {
		super();
	}

	public FailedException(String msg) {
		super(msg);
	}

	public FailedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public FailedException(Throwable cause) {
		super(cause);
	}
}
