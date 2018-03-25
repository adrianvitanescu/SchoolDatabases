package com.main.java;

import com.main.java.ro.sci.PostgresHelper;
import com.main.java.ro.sci.db.DbContract;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        PostgresHelper client = new PostgresHelper(
                DbContract.HOST,
                DbContract.DB_NAME,
                DbContract.USERNAME,
                DbContract.PASSWORD);

        try {
            if (client.connect()) {
                System.out.println("DB connected");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = client.execQuery("SELECT * FROM classes");

        System.out.println("\nDisplay data of all classes:");
        while (rs.next()) {
            System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\t%d\n",
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7));
        }

        ResultSet cs = client.execQuery("SELECT * FROM courses WHERE courseid = 7");

        System.out.println("\nDisplay data for a course with specified id: ");
        while (cs.next()) {
            System.out.printf("%d\t%s\t%s\t%s\t%s\n",
                    cs.getInt(1),
                    cs.getString(2),
                    cs.getString(3),
                    cs.getString(4),
                    cs.getString(5));
        }

        ResultSet ds = client.execQuery("SELECT * FROM classes " +
                "INNER JOIN courses ON classes.courseid = courses.courseid " +
                "WHERE LOWER(title) LIKE 'intro%';");

        System.out.println("\nDisplay data for all classes whose title (when converted to all lowercase letters) begins with \"intro\".");
        while (ds.next()) {
            System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\t%d\t%d\t%s\t%s\t%s\t%s\n",
                    ds.getInt(1),
                    ds.getInt(2),
                    ds.getString(3),
                    ds.getString(4),
                    ds.getString(5),
                    ds.getString(6),
                    ds.getInt(7),
                    ds.getInt(8),
                    ds.getString(9),
                    ds.getString(10),
                    ds.getString(11),
                    ds.getString(12));
        }

        ResultSet es = client.execQuery("SELECT * FROM classes " +
                "FULL JOIN crosslistings ON classes.courseid = crosslistings.courseid " +
                "WHERE LOWER(dept) LIKE '%cos';");

        System.out.println("\nDisplay data for all classes whose dept (when converted to all lowercase letters) is \"cos\" and whose coursenum begins with \"3\"");
        while (es.next()) {
            System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\t%d\t%d\t%s\t%d\n",
                    es.getInt(1),
                    es.getInt(2),
                    es.getString(3),
                    es.getString(4),
                    es.getString(5),
                    es.getString(6),
                    es.getInt(7),
                    es.getInt(8),
                    es.getString(9),
                    es.getInt(10));
        }
    }
}
