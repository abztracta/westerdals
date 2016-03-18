package no.wact.pg3100.assignment3.controller;

import no.wact.pg3100.assignment3.model.Album;
import no.wact.pg3100.assignment3.model.MusicHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/MusicChoice")
public class MusicChoiceServlet extends HttpServlet {

    private final String SUCCESS_URL = "MusicChoiceJSP.jsp";
    private final String ERROR_URL = "error.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address;
        String genre = req.getParameter("genre");
        try {
            List<Album> albumList = new MusicHandler().getAlbums(genre);
            req.setAttribute("albumList", albumList);
            req.setAttribute("genre", genre);
            address = SUCCESS_URL;
        } catch (Exception e) {
            req.setAttribute("error_message", e.getMessage());
            address = ERROR_URL;
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }
}
