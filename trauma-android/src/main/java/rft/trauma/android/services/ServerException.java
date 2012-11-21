package rft.trauma.android.services;

public class ServerException extends RuntimeException
{
	private static final long serialVersionUID = -6329490780483827414L;
	
	public ServerException(String message)
	{
		super(message);
	}
}
