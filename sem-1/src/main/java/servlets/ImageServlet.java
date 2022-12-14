package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = new File(".").getCanonicalFile() + File.separator + "sem-1" + File.separator + "images" + File.separator;
        String imageName = request.getPathInfo().substring(1);

        File file = new File(path + imageName);
        byte[] content = Files.readAllBytes(file.toPath());

        response.setContentType(getServletContext().getMimeType(imageName));
        response.setContentLength(content.length);
        response.getOutputStream().write(content);

    }
}
