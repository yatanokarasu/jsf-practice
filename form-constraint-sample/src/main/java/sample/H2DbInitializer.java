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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 */
public class H2DbInitializer implements ServletContextListener {

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:file:./jsfauth;DATABASE_TO_UPPER=false", "jetty", "jetty");
            Statement statement = connection.createStatement()) {
            statement.execute(
                "create table if not exists users (" +
                "  id int primary key," +
                "  username varchar(50) not null," +
                "  pwd varchar(50) not null" +
                ")"
            );
            statement.execute(
                "create table if not exists user_roles (" +
                "  user_id int not null," +
                "  role_id int not null," +
                "  primary key(user_id, role_id)" +
                ")"
            );
            statement.execute(
                "create table if not exists roles (" +
                "  id int primary key," +
                "  role varchar(100) not null" +
                ")"
            );
            statement.execute(
                "insert into users values (1, 'admin_user', 'adminpass'), (2, 'normal_user', 'normalpass')"
            );
            statement.execute(
                "insert into roles values (1, 'admin'), (2, 'normal')"
            );
            statement.execute(
                "insert into user_roles values (1, 1), (2, 2)"
            );
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}
