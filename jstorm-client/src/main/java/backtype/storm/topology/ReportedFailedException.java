package backtype.storm.topology;

public class ReportedFailedException extends FailedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7244058034931081665L;

	public ReportedFailedException() {
		super();
	}

	public ReportedFailedException(String msg) {
		super(msg);
	}

	public ReportedFailedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ReportedFailedException(Throwable cause) {
		super(cause);
	}
}
