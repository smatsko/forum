package telran.java51.security.filter;

import java.io.IOException;
import java.security.Principal;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dao.UserRepository;
import telran.java51.accounting.model.User;
import telran.java51.forum.dao.ForumRepository;
import telran.java51.forum.model.Message;

@Component
@Order(60)
@RequiredArgsConstructor
public class UpdateAndDeleteMessage implements Filter {

	final ForumRepository forumRepository;
	final UserRepository userRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();
			User user = userRepository.findById(principal.getName()).get();
			String[] arr = request.getServletPath().split("/");
			try {
				Message msg = forumRepository.findById(arr[arr.length - 1]).get();
				if (!principal.getName().equalsIgnoreCase(msg.getAuthor())
						&& (HttpMethod.DELETE.matches(request.getMethod()) && !user.getRoles().contains("MODERATOR"))) {
					response.sendError(403);
					return;
				}

			} catch (Exception e) {
				response.sendError(404);
				return;
			}
		}
		chain.doFilter(request, response);

	}

	private boolean checkEndPoint(String method, String path) {
		return path.matches("/forum/post/\\w+")
				&& (HttpMethod.DELETE.matches(method) || HttpMethod.PUT.matches(method));
	}

}
