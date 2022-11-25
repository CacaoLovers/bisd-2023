package servlets;

import dto.MissingForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.UUID;

@WebServlet("/addphoto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 1024,
        maxRequestSize = 1024 * 1024 * 5 * 5 * 5)
public class AddPhotoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String savePath = new File(".").getCanonicalFile().toString() + File.separator + "sem-1" + File.separator +"images";
        String fileName = null;
        String extension = null;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }


        for (Part part : request.getParts()) {
            fileName = extractFileName(part);
            fileName = new File(fileName).getName();
            if (fileName.equals("")){
                request.setAttribute("addStep", "photo");
                request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);
                return;
            }
                extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                UUID uuid = UUID.randomUUID();
                fileName = uuid.toString().substring(0, 8) + extension;
                part.write(savePath + File.separator + fileName);
            break;
        }

        if (session.getAttribute("missingForm") != null){

            MissingForm missingForm = (MissingForm) (session.getAttribute("missingForm"));
            missingForm.setPathImage(fileName);

        }

        request.setAttribute("addStep", "photo");
        request.getRequestDispatcher("/WEB-INF/pages/missing.jsp").forward(request, response);

    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";

    }

}
