package com.ljyuan.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SeebugDao {

	private Connection conn = null;
	private Statement stmt = null;

	public SeebugDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webmagic?user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

	public int[] add(List<SeebugRepo> seebugRepoList) {
		try {
			String sql = "INSERT INTO `webmagic`.`seebug` "
					+ "(`bugid`,`bugname`,`bugfinddate`,`bugsubmitdate`,`buglevel`,`bugtype`,`cveid`,`cnnvdid`,`cnvdid`,`bugauthor`,`bugsubmitter`,`bugoutline` ,`zoomeyedork`,`affectscomponent`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i=0; i<seebugRepoList.size(); i++){
			SeebugRepo seebugRepo = seebugRepoList.get(i);
			ps.setString(1,seebugRepo.getBugId());
			ps.setString(2,seebugRepo.getBugName());
			ps.setString(3,seebugRepo.getBugFindDate());
			ps.setString(4,seebugRepo.getBugSubmitDate());
			ps.setInt(5,seebugRepo.getBugLevel());
			ps.setString(6,seebugRepo.getBugType() );
			ps.setString(7,seebugRepo.getCveId() );
			ps.setString(8,seebugRepo.getCnnvdId() );
			ps.setString(9,seebugRepo.getCnvdId() );
			ps.setString(10,seebugRepo.getBugAuthor() );
			ps.setString(11,seebugRepo.getBugSubmitter() );
			ps.setString(12,seebugRepo.getBugOutline() );
			ps.setString(13,seebugRepo.getZoomEyeDork() );
			ps.setString(14,seebugRepo.getAffectsComponent() );
			ps.addBatch();
			}
			System.out.println("---------------------------------执行批量插入操作----------------------------");
			return ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[]{-1};
	}

}
