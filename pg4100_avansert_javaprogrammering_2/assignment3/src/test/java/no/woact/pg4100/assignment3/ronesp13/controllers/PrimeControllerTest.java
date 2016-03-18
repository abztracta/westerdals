package no.woact.pg4100.assignment3.ronesp13.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class PrimeControllerTest {

    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private RequestDispatcher mockDispatch;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testDoGetWithValidPrime() throws Exception {
        testRequest("7", "prime.jsp");
    }

    @Test
    public void testDoGetWithInvalidPrime() throws Exception {
        testRequest("8", "not_a_prime.jsp");
    }

    @Test
    public void testDoGetWithInvalidInput() throws Exception {
        testRequest("e", "invalid_input.jsp");
    }

    public void testRequest(String input, String outputUrl) throws Exception {
        given(mockRequest.getParameter("prime")).willReturn(input);
        mockRequest.setAttribute("value", input);
        when(mockRequest.getRequestDispatcher(outputUrl)).thenReturn(mockDispatch);
        when(mockRequest.getAttribute("value")).thenReturn(input);

        PrimeController controller = new PrimeController();
        controller.doGet(mockRequest, mockResponse);

        assertSame(mockRequest.getAttribute("value"), input);
    }
}