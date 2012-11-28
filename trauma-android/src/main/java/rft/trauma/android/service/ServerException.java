package rft.trauma.android.service;

public class ServerException extends RuntimeException
{
	private static final long serialVersionUID = -6329490780483827414L;
	
	public ServerException(String message)
	{
		super(message);
	}
	
	public ServerException(Exception ex)
	{
		super(ex);
	}
	
	public ServerException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
