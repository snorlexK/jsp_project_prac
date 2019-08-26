package Bean;

import java.sql.*;
import Bean.MemberBean;

public class Member {
	private String jdbc_driver = "com.mysql.jdbc.Driver";
	// private String jdbc_url = "jdbc:mysql://plare.cf:3306/web";
	// private String db_id = "admin";
	// private String db_pwd = "asdf1346ma";
	private String jdbc_url = "jdbc:mysql://localhost:3306/basicjsp";
	private String db_id = "jspid";
	private String db_pwd = "jsppass";
	
	public Member() {
		// JDBC 드라이버 연결
		try {
			Class.forName(jdbc_driver);
		} catch(Exception e) {
			System.out.println("Fail to load JDBC DRIVER");
		}
	}
	
	public MemberBean login(MemberBean member) {
		// 로그인 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberBean memberBean = new MemberBean();
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			String sql = "select * from member where email=? and password=?";
			// 파라미터 값으로 넘어온 이메일과 비밀번호에 해당되는 유저의 모든 정보를 불러오는 sql문
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			
			rs = pstmt.executeQuery();
			
			rs.next();
			// 해당되는 result set은 한 개뿐이기 때문에 반복문을 사용하지 않음  
			memberBean.setEmail(rs.getString("email"));
			memberBean.setNickname(rs.getString("nickname"));
			memberBean.setBirthday(rs.getDate("birthday"));
			memberBean.setPhone(rs.getString("phone"));
			memberBean.setAddress(rs.getString("address"));
			memberBean.setReferer(rs.getString("referer"));
			memberBean.setNickname_latest_changed(rs.getString("nickname_latest_changed"));
			// 해당 회원의 모든 정보를 UserBean 객체에 setter로 삽입
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
			if(rs!=null)
				try{rs.close();}catch(SQLException sqle){}
		}
		
		return memberBean;
		
	}
	
	public void register(MemberBean member) {
		// 회원가입 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String referer_email = null;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			if(member.getReferer() != "") {
				referer_email = search_referer(member.getReferer());
			}
			
			String sql = "insert into member(email, password, nickname, birthday, phone, address, referer) values(?, ?, ?, ?, ?, ?, ?)";
			// 받아온 모든 정보를 DB에 저장하는 sql문
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, referer_email);
			
			pstmt.executeUpdate();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
		}
		
	}
	
	public String search_referer(String referer_nickname) {
		// 추천인의 정보를 저장하는 메소드
		// 닉네임은 변경 가능하기 때문에 닉네임에 대응하는 이메일을 검색하여 DB에 저장
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String referer_email = null;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			String sql = "select email from member where nickname = ?";
			// 입력된 닉네임에 대응하는 이메일을 검색
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, referer_nickname);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			referer_email = rs.getString("email");
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
		}
		
		return referer_email;
	}
	
	public boolean email_double_check(String email) {
		// 이메일 중복 확인 메스드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			String sql = "select email from member where email=?";
			// 입력된 이메일과 똑같은 이메일이 이미 존재하는지 검색
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			String email_sql = rs.getString("email");
			
			if(!email_sql.equals(email)) {
				check = true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
		}
		
		return check; 
	}
	
	public boolean nickname_double_check(String nickname) {
		// 이메일 중복 확인 메스드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			String sql = "select nickname from member where nickname=?";
			// 입력된 이메일과 똑같은 이메일이 이미 존재하는지 검색
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			String nickname_sql = rs.getString("nickname");
			
			if(!nickname_sql.equals(nickname)) {
				check = true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
		}
		
		return check; 
	}
	
	public void delete_member(MemberBean member) {
		// 회원탈퇴 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
		
			String sql = "delete from member where email=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			
			pstmt.executeUpdate();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
		}
	}
	
	public String find_email(MemberBean member) {
		// 이메일 찾기 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String email = null;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			String sql = "select email from member where phone=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPhone());
			
			rs = pstmt.executeQuery();
			
			rs.next();
			email = rs.getString("email");
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
			if(rs!=null)
				try{rs.close();}catch(SQLException sqle){}
		}
		
		return email;
	}
	
	public String find_password(MemberBean member) {
		// 비밀번호 찾기 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String password = null;
		
		try {
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			
			String sql = "select password from member where email=? and phone=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPhone());
			
			rs = pstmt.executeQuery();
			
			rs.next();
			password = rs.getString("password");
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstmt!=null)
				try{pstmt.close();}catch(SQLException sqle){}
			if(conn!=null)
				try{conn.close();}catch(SQLException sqle){}
			if(rs!=null)
				try{rs.close();}catch(SQLException sqle){}
		}
		
		return password;
	} 
	
}
