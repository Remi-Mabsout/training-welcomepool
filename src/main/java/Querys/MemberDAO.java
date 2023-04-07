package main.java.Querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;


public class MemberDAO extends DAO<Member> {


    public MemberDAO(String url, String username, String password) {
        super(url, username, password);
    }

    public ArrayList<Member> getAll() throws SQLException {
        ArrayList<Member> members = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT m.id, m.name, email, birthdate, class_id, c.name as class_name FROM members as m LEFT JOIN classes as c ON m.class_id = c.id";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Member member = new Member();
            member.setId(rs.getInt("m.id"));
            member.setName(rs.getString("m.name"));
            member.setEmail(rs.getString("email"));
            member.setBirthdate(rs.getDate("birthdate"));
            int id = rs.getInt("class_id");
            if(rs.wasNull())
            {
                member.setClassId(-1);
                member.setPromotion("");
            }
            else {
                member.setClassId(id);
                member.setPromotion(rs.getString("class_name"));
            }


            members.add(member);

        }
        return members;
    }

    public int add(Member member) throws SQLException {
        int memberId = 0;

        if(member.getClassId() == -1)
        {
            String sql = " insert into members (name,email,birthdate, class_id)"
                    + " values (?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, member.getName());
                stmt.setString(2, member.getEmail());
                stmt.setDate(3, member.getBirthdate());
                stmt.setNull(4,Types.INTEGER);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        memberId = rs.getInt(1);
                    }
                }
            }
        }
        else{
            //String getId;
            String sql = " insert into members (name,email,birthdate, class_id)"
                    + " values (?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, member.getName());
                stmt.setString(2, member.getEmail());
                stmt.setDate(3, member.getBirthdate());
                stmt.setInt(4, member.getClassId());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        memberId = rs.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

    public int countByPromotion(int id) throws SQLException {

        String sql = "SELECT COUNT(class_id) FROM members WHERE class_id="+id;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Méthode pour supprimer un membre de la table "members" par son id
    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM members WHERE id=?";
        int rowsDeleted;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            rowsDeleted = stmt.executeUpdate();
        }
        return rowsDeleted;
    }

    public int update(Member member) throws SQLException {
        System.out.println("Update");
        System.out.println(member.getId());
        System.out.println(member.getName());
        System.out.println(member.getClassId());
        String sql = "UPDATE members SET name=?, email=?, birthdate=?, class_id=? WHERE id=?";
        int rowsUpdated;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setDate(3, member.getBirthdate());
            stmt.setInt(4, member.getClassId());
            stmt.setInt(5, member.getId());
            rowsUpdated = stmt.executeUpdate();
        }
        return rowsUpdated;
    }
}
