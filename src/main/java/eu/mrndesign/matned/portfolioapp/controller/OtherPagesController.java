package eu.mrndesign.matned.portfolioapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class OtherPagesController {

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/accessDenied")
    public String getAccessDenied(){
        return "accessDenied";
    }

    @GetMapping("/cv_pdf")
    public void showPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        InputStream inputStream = new FileInputStream(new File("src/main/resources/static/file/cv.pdf"));
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }

}
