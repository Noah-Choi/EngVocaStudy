package org.engvoca.service;

import javax.inject.Inject;

import org.engvoca.dao.MemberDAO;
import org.engvoca.domain.MemberVO;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService 
{

	@Inject
	MemberDAO dao;
	
	@Override
	public void insertMember(MemberVO vo) throws Exception 
	{
		dao.insertMember(vo);
	}

	@Override
	public MemberVO login(MemberVO vo) throws Exception 
	{
		return dao.login(vo);
	}
	
}
