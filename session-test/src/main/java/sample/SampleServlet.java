/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2017 Yusuke TAKEI.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package sample;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 */
public class SampleServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset='UTF-8'");

        final String name = req.getParameter("name");
        final String newpass = req.getParameter("password");

        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        writer.append("<html lang=\"ja\"><head><meta charset=\"UTF-8\"/></head><body>");

        HttpSession session = req.getSession(false);

        if (session == null) {
            session = req.getSession();
            final Cookie cookie = new Cookie("count", "1");
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);

            session.setAttribute("pass", newpass);

            writer.append("<p>ようこそ" + name + "さん");
            writer.append("<p>セッション情報がなかったので、初めての訪問ですね");
            writer.append("<p>" + session.toString());
        } else {
            final String oldpass = (String) session.getAttribute("pass");
            final Cookie cookie = getCounterCookie(req);
            final int newCounter = Integer.parseInt(cookie.getValue()) + 1;
            cookie.setValue(String.valueOf(newCounter));
            resp.addCookie(cookie);

            writer.append("<p>ようこそ" + name + "さん");
            writer.append("<p>また訪れてくれましたね。" + newCounter + "回目の訪問です。");
            writer.append("<p>前回のパスワードは" + oldpass + "でしたね。");
            writer.append("<p>今回のパスワードは" + newpass + "でしたね。");
            writer.append("<p>" + session.toString());
        }

        writer.append("</body></html>");
        writer.flush();
        writer.close();
    }


    private Cookie getCounterCookie(final HttpServletRequest request) {
        for (final Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("count")) {
                return cookie;
            }
        }

        return new Cookie("counter", "0");
    }

}
