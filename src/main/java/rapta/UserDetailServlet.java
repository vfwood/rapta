package rapta;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblogic.security.Security;
import weblogic.security.spi.WLSGroup;

/**
 * Responds to /user/detail with
 * 
 * { username: "<username>", roles { name: "role1", name: "role2" } }
 * 
 * @author vfwood
 * 
 */
public class UserDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		try {
			out.println(getUserDetails(request));
		} catch (Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
			out.println("</pre>");
			out.println("Error doing subject stuff: " + e);
		}
	}

	protected String getUserDetails(HttpServletRequest request) {

		Subject subject = Security.getCurrentSubject();
		List<String> groupNames = new ArrayList<String>();

		String groups = "{";
		String sep = "";
		for (Principal p : subject.getPrincipals()) {
			if (p instanceof WLSGroup) {
				groupNames.add(p.getName());
				groups = groups + sep + "name:" + '"' + p.getName() + '"';
				sep = ",\n";
			}

		}
		groups += "}";

		String username = (request.getUserPrincipal() != null ? request
				.getUserPrincipal().getName() : "<no-user-principal>");
		return "{ username: " + '"' + username + '"' + ",\n roles: " + groups
				+ "}";

	}

}
