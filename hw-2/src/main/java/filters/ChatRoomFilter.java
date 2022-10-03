package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/room")
public class ChatRoomFilter extends HttpFilter {


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (request.getSession() == null
                || session.getAttribute("action") == null) {

            session.setAttribute("messageError", "Ошибка инициализации сессии");
            response.sendRedirect("/error");

        } else {

            chain.doFilter(request, response);

        }
    }
}
