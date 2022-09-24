package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Content(contentField = "Фоточки", photo = "images/photo.gif")
public class ImagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        class Publish{
            private String path;
            private String name;
            private String description;

            public Publish(String path, String name, String description) {
                this.path = path;
                this.name = name;
                this.description = description;
            }

            public String getPath() {
                return path;
            }



            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDescription(String description) {
                this.description = description;
            }



        }

        List<Publish> publishList = new ArrayList<>();
        publishList.add(new Publish("/images/image-1.jpg", "it's wonder", "the greater miracle"));
        publishList.add(new Publish("/images/image-2.jpg", "find cat", "hardly^^"));
        publishList.add(new Publish("/images/image-3.jpg", "where are you guys??", "empty on the battle field("));


        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Фоточки</title>");
        out.println("<link href = \"/css/images.css\" rel = \"stylesheet\" type = \"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<main>");
        for(Publish publish: publishList){
            out.println("<div class = \"container\">");
            out.println("<image src = \" "+ publish.getPath() + "\" class = \"image\">");
            out.println("<p class = \"text-name\">" + publish.getName() + "</p>");
            out.println("<p class = \"text-description\">" + publish.getDescription() + "</p>");
            out.println("</div>");
        }
        out.println("</main>");
        out.println("</body>");
        out.println("</html>");
    }
}
