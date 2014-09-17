package rapta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Responds to /user/detail with
 * 
 * { username: "<username>", roles { name: "role1", name: "role2" } }
 * 
 */
public class UserTestServlet extends HttpServlet {

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
		List<String> groupNames = Arrays.asList("testrole", "testrole2");

		String groups = "{";
		String sep = "";
		for (String g : groupNames) {
			groups = groups + sep + "name:" + '"' + g + '"';
			sep = ",\n";
		}
		groups += "}";

		return "{ username: " + '"' + "test" + '"' + ",\n roles: " + groups
				+ "}";

	}

}
