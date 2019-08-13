package edu.bit.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.bit.ex.dto.BDto;

//Data access object
public class BDao {
	
	DataSource dataSource;
	
	public BDao(){
	
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public ArrayList<BDto> list() {

		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		//3단콤보 셋트
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			String query ="select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, 
						bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();				
			}catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}			
		}
		
		
		return dtos;
	}

	public BDto contentView(String bId) {
		
		//upHit(bId);
		BDto dto = null;
		
		//3단콤보 셋트
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
					
		 	connection = dataSource.getConnection();
			String query ="select * from mvc_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			
			resultSet = preparedStatement.executeQuery();
					
			if(resultSet.next()) {
				int bid = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
						
				dto = new BDto(bid, bName, bTitle, bContent, bDate, 
								bHit, bGroup, bStep, bIndent);
				}			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					if(resultSet != null) resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
		return dto;
	}

	public void write(String bName, String bTitle, String bContent) {
		//3단콤보 셋트
		Connection connection = null;
		PreparedStatement preparedStatement = null;
						
		try {
					
		 	connection = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			//executeUpdate 함수를 사용하는 방법입니다.
			//-> INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
            //-> CREATE / DROP 관련 구문에서는 -1 을 반환합니다.
			
			//리턴결과는 
			//(1) INSERT, DELETE, UPDATE된 행의 수
			//(2) 아무 리턴이 없으면 0
			int rn = preparedStatement.executeUpdate();
			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
	}

	public void delete(String bId) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
						
		try {
			//커넥션풀에서 	Connection 객체 받아옴	
		 	connection = dataSource.getConnection();
			String query = "delete from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);			
			preparedStatement.setInt(1, Integer.parseInt(bId));

			
			//executeUpdate 함수를 사용하는 방법입니다.
			//-> INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
            //-> CREATE / DROP 관련 구문에서는 -1 을 반환합니다.
			
			//리턴결과는 
			//(1) INSERT, DELETE, UPDATE된 행의 수
			//(2) 아무 리턴이 없으면 0
			int rn = preparedStatement.executeUpdate();
			System.out.println(rn);
			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
		
	}

	public void modify(String bId, String bName, String bTitle, String bContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
						
		try {
			//커넥션풀에서 	Connection 객체 받아옴	
		 	connection = dataSource.getConnection();
		 	
		 	//update mvc_board set bName = '홍길동', bTitle = ?, bContent = ? where bId = 12
			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);			
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));

			
			//executeUpdate 함수를 사용하는 방법입니다.
			//-> INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
            //-> CREATE / DROP 관련 구문에서는 -1 을 반환합니다.
			
			//리턴결과는 
			//(1) INSERT, DELETE, UPDATE된 행의 수
			//(2) 아무 리턴이 없으면 0
			int rn = preparedStatement.executeUpdate();
			System.out.println(rn);
			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
	}

	public BDto reply_view(String bId) {
		
		BDto dto = null;
		
		//3단콤보 셋트
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
					
		 	connection = dataSource.getConnection();
			String query ="select * from mvc_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			
			resultSet = preparedStatement.executeQuery();
					
			if(resultSet.next()) {
				int bid = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
						
				dto = new BDto(bid, bName, bTitle, bContent, bDate, 
								bHit, bGroup, bStep, bIndent);
				}			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					if(resultSet != null) resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
		return dto;
	}

	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		
		replyShape(bGroup, bStep);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
						
		try {
				
		 	connection = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query);			

			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			//답변글을 달면 가로,세로를 1씩 증가시켜 저장 
			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);

			int rn = preparedStatement.executeUpdate();
			System.out.println(rn);
			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
	}
	
	private void replyShape(String bGroup, String bStep) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
						
		try {
			//커넥션풀에서 	Connection 객체 받아옴	
		 	connection = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			
			preparedStatement = connection.prepareStatement(query);			
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));

			int rn = preparedStatement.executeUpdate();
			System.out.println(rn);
			
			} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
			} finally {
				try {
					
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null) connection.close();				
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}			
			}
		
		
		
	}
	
	
}
