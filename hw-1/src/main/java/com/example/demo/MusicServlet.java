package com.example.demo;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Content(contentField = "Музыка", photo = "images/music.gif")
public class MusicServlet extends HomePageServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        class MusicContent{
            private String name;
            private String group;
            private String audioPath;
            private String imagePath;


            public MusicContent(String name, String group, String audioPath, String imagePath) {
                this.name = name;
                this.group = group;
                this.audioPath = audioPath;
                this.imagePath = imagePath;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

            public String getAudioPath() {
                return audioPath;
            }

            public void setAudioPath(String audioPath) {
                this.audioPath = audioPath;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }


        }

        List<MusicContent> musicContentList = new ArrayList<>();
        musicContentList.add(new MusicContent("Vicarious", "Tool", "/audio/audio-1.mp3" ,"/images/audio-image-1.jpg"));
        musicContentList.add(new MusicContent("Sunsetz", "Cigarettes", "/audio/audio-2.mp3" ,"/images/audio-image-2.jpg"));
        musicContentList.add(new MusicContent("Get got", "Death Grips", "/audio/audio-3.mp3" ,"/images/audio-image-3.jpg"));


        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Музыка</title>");
        out.println("<link href = \"/css/music.css\" rel = \"stylesheet\" type = \"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<main>");
        for(MusicContent musicContent: musicContentList) {
            out.println("<div class = 'music-container'>");
            out.println("<image src = '" + musicContent.getImagePath() + "' class = 'music-image'>");
            out.println("<p>" + musicContent.getGroup() + " - " + musicContent.getName() + "</p>");
            out.println("<audio controls = \"controls\">");
            out.println("<source src = '" + musicContent.audioPath + "' type = \"audio/mpeg\">");
            out.println("</audio>");
            out.println("</div>");
        }
        out.println("</main>");
        out.println("</body>");
        out.println("</html>");
    }
}
