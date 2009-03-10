package ted;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ProxyAuthenticator extends Authenticator
{
    private String username, password;

    public ProxyAuthenticator(String username, String password)
    {
	this.username = username;
	this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication()
    {
	return new PasswordAuthentication(username, password.toCharArray());
    }
}
