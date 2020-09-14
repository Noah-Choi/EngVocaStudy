package org.engvoca.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.engvoca.domain.MemberVO;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO 
{
    @Inject
    private SqlSession sqlSession;
   
    //memberMapper.xml의 namespace값
    private static final String namespace = "MemberMapper";
   
    @Override
    public void insertMember(MemberVO vo) throws Exception
    {
        sqlSession.insert(namespace+".insertMember", vo);
    }

	@Override
	public MemberVO login(MemberVO vo) throws Exception 
	{
		return sqlSession.selectOne(namespace + ".login", vo);
	}
}
