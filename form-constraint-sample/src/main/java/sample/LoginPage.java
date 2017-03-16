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


import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 *
 */
@ManagedBean(name = "login")
public class LoginPage {

    private String id = "admin_user";

    private String password = "adminpass";


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }


    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }


    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }


    public void login() {
        final ExternalContext exContext = FacesContext.getCurrentInstance().getExternalContext();
        final HttpServletRequest request = (HttpServletRequest) exContext.getRequest();
        try {
            request.login(id, password);
        } catch (final ServletException exception) {
            exception.printStackTrace();
        }

        System.out.println(request.getUserPrincipal());
        System.out.println(request.getRemoteUser());

        try {
            exContext.redirect("/constraint/auth.html");
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

}
