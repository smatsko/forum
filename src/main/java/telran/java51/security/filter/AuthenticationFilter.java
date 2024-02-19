package telran.java51.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dao.UserRepository;
import telran.java51.accounting.model.User;
import telran.java51.forum.dao.ForumRepository;
import telran.java51.forum.model.Message;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

	final UserRepository userAccountRepository;
    final ForumRepository forumRepository;
	
	final static String[] AccessMatrix = { 
			"/register;Post;;;", 
			"/login;Post;authenticated;",
			"/user/[^/]+;Delete;authenticated;ownerInURI Administrator;", 
			"/user/[^/]+;Put;authenticated;ownerInURI;",
			"/user/[^/]+/role/.+;Put;authenticated;Administrator;", 
			"/user/[^/]+/role/.+;Delete;authenticated;Administrator;",
			"/password;Put;authenticated;ownerOfAccount;", 
			"/user/[^/]+;Get;authenticated;;",
			"/post/[^/]+;Post;authenticated;ownerInURI;", 
			"/post/[^/]+;Get;authenticated;;",
			"/post/[^/]+;Delete;authenticated;postOwner Moderator;", 
			"/post/[^/]+;Put;authenticated;postOwner;",
			"/post/[^/]+/like;Put;authenticated;", 
			"/post/[^/]+/comment/[^/]+;Put;authenticated;ownerInURI4",
			"/posts/.+;Post;;;", 
			"/posts/.+;Get;;;"};


	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		
		String path = request.getServletPath();;
		String method = request.getMethod();
		User userAccount = null;	

		
		try {

			String matrix[] = Arrays.asList(AccessMatrix).stream()
					.filter(t -> path.matches(".+" + t.split(";")[0]))
					.filter(t -> t.toUpperCase().matches("^.+;" + method + ";.+"))
					.findFirst()
					.orElseThrow(RuntimeException::new).split(";");
			
			System.out.println(matrix[0] + matrix[1]);

			if ( (matrix.length > 2) && !matrix[2].toLowerCase().equals("")) {		
				String[] credentials = getCredentials(request.getHeader("Authorization"));
				userAccount = userAccountRepository.findById(credentials[0]).orElseThrow(RuntimeException::new); 
				if (!BCrypt.checkpw(credentials[1], userAccount.getPassword())) { 
					throw new RuntimeException(); 
				}

		
		    if ( (matrix.length > 3) && !checkGroupsOr( matrix[3].split(" "), path.split("/"), userAccount, credentials[0])) {
					      throw new RuntimeException();
				    
				}
				
			}	 
		
		} catch (Exception e) {
			response.sendError(401);
			return;
		} 
		
        if (userAccount != null) {
		    request = new WrappedRequest(request, userAccount.getLogin());
        }
		
        chain.doFilter(request, response);

	}


	private boolean checkGroupsOr(String[] groups, String[] path, User userAccount, String login) {
	
		boolean orflag = false;
		
		for( String s : groups) {
				
			//s=s.toUpperCase();
			
	       
	       if (s.equals("ownerOfAccount")) {
	        
	        	orflag = orflag || userAccount.getLogin().equals(login); 
	      
	       } else if (s.equals("ownerInURI")) {
	       
	    	   orflag = orflag || path[3].equals(login);
	       
	       }  else if (s.equals("ownerInURI4")) {
		       
	    	   orflag = orflag || path[5].equals(login);

	       }  else if (s.equals("postOwner")) {
		       
	    	   Message message = forumRepository.findById(path[3]).orElseThrow(RuntimeException::new);
	    	   orflag = orflag || message.getAuthor().equals(login);
	       
	       } else {
	        	
	    	   orflag = orflag || userAccount.getRoles().contains(s);
	        	
	        };
		}
	
		return orflag;
	}


	private String[] getCredentials(String header) {
		String token = header.split(" ")[1];
		String decode = new String(Base64.getDecoder().decode(token));
		return decode.split(":");
	}

	private class WrappedRequest extends HttpServletRequestWrapper {
		private String login;

		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}

		@Override
		public Principal getUserPrincipal() {
			return () -> login;
		}

	}
}
