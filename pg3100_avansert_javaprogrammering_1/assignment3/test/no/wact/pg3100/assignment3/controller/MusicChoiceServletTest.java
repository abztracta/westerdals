package no.wact.pg3100.assignment3.controller;

import no.wact.pg3100.assignment3.config.Config;
import no.wact.pg3100.assignment3.model.Album;
import no.wact.pg3100.assignment3.model.MusicHandler;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static test.helper.TestTableHelper.createTableAndFixtures;
import static test.helper.TestTableHelper.removeTestTable;

@RunWith(EasyMockRunner.class)
public class MusicChoiceServletTest extends EasyMockSupport {

    private static String SUCCESS_URL = "MusicChoiceJSP.jsp";
    private static String ERROR_URL = "error.jsp";

    @Mock
    private HttpServletRequest mockServletRequest;
    @Mock
    private HttpServletResponse mockServletResponse;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setUp() throws Exception {
        mockServletRequest = createMock(HttpServletRequest.class);
        mockServletResponse = createMock(HttpServletResponse.class);
        mockRequestDispatcher = createMock(RequestDispatcher.class);
        createTableAndFixtures();
        Config.DB_TABLE = "albums_test";
        Config.DB_USERNAME = "root";
        Config.DB_PASSWORD = "root";
    }

    @After
    public void tearDown() throws Exception {
        Config.DB_USERNAME = "root";
        Config.DB_PASSWORD = "root";
        removeTestTable();
    }

    @Test
    public void testDoPost() throws Exception {
        trainRequest();
        replay(mockServletRequest);
        new MusicChoiceServlet().doPost(mockServletRequest, mockServletResponse);
        assertEquals(mockServletRequest.getAttribute("albumList"), new MusicHandler().getAlbums("Rock"));
        assertEquals(mockServletRequest.getAttribute("genre"), "Rock");
        verify(mockServletRequest);
    }

    private void trainRequest() {
        String genre = "Rock";
        expect(mockServletRequest.getParameter("genre")).andReturn(genre);
        try {
            List<Album> albumList = new MusicHandler().getAlbums(genre);
            mockServletRequest.setAttribute("albumList", albumList);
            mockServletRequest.setAttribute("genre", genre);
            expect(mockServletRequest.getRequestDispatcher(SUCCESS_URL)).andReturn(mockRequestDispatcher);
            expect(mockServletRequest.getAttribute("albumList")).andReturn(albumList);
            expect(mockServletRequest.getAttribute("genre")).andReturn(genre);
        } catch (Exception e) {
            mockServletRequest.setAttribute("error_message", e.getMessage());
            expect(mockServletRequest.getRequestDispatcher(ERROR_URL)).andReturn(mockRequestDispatcher);
            expect(mockServletRequest.getAttribute("error_message")).andReturn(e.getMessage());
        }
    }

    @Test
    public void testDoPostWithInvalidLoginCredentials() throws Exception {
        Config.DB_USERNAME = "ausernamethatdoesntexist";
        Config.DB_PASSWORD = "ThisWouldProbablyMakeAVerySecurePasswordBecauseItConsistsOfSoManyCharacters";
        trainRequest();
        replay(mockServletRequest);
        new MusicChoiceServlet().doPost(mockServletRequest, mockServletResponse);
        assertEquals("Access denied for user 'ausernamethatdoesntexist'@'localhost' (using password: YES)",
                mockServletRequest.getAttribute("error_message"));
        verify(mockServletRequest);
    }

}