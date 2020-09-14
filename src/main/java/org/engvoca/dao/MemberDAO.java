package org.engvoca.dao;

import org.engvoca.domain.MemberVO;

public interface MemberDAO 
{
	public void insertMember(MemberVO vo) throws Exception;
	
	public MemberVO login(MemberVO vo) throws Exception;
}
