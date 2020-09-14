package org.engvoca.service;

import org.engvoca.domain.MemberVO;

public interface MemberService 
{
	public void insertMember(MemberVO vo) throws Exception;
	
	public MemberVO login(MemberVO vo) throws Exception;
}
