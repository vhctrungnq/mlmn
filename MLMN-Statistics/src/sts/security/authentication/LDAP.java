package sts.security.authentication;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

public class LDAP {

	public static String authMethod 	= "simple";
	public static String ldapVersion 	= "3";
	public static String ldapHost 		= "10.151.6.248";
	public static String ldapPort 		= "389";

	private static Logger logger = Logger
			.getLogger(LDAP.class);

	public static boolean authentication(String ldapDn, String ldapPw) {
		DirContext ctx = null;
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + ldapHost + ":" + ldapPort);
		env.put(Context.SECURITY_AUTHENTICATION, authMethod);
		env.put(Context.SECURITY_PRINCIPAL, ldapDn);
		env.put(Context.SECURITY_CREDENTIALS, ldapPw);
		env.put("java.naming.ldap.version", ldapVersion);

		try {
			//logger.info("Connecting to host " + ldapHost + " at port " + ldapPort + "...");
			
			ctx = new InitialDirContext(env);
			
			//logger.info("LDAP authentication successful!");

			return true;

		} catch (AuthenticationException authEx) {
			logger.error("LDAP authentication failed: " + authEx.toString());
		} catch (NamingException e) {
			logger.error("LDAP authentication failed: " + e.toString());
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (NamingException e) {
				logger.error("LDAP close failed: " + e.toString());
			}
		}
		return false;
	}

	public static void main(String arg[]) {
		System.out.println(authentication("pakh.tctk", "ktdh12345"));
	}
}
