package org.engvoca.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.engvoca.domain.DifficultyVO;
import org.engvoca.domain.FormVO;
import org.engvoca.domain.StudyVO;
import org.engvoca.domain.VocaVO;
import org.springframework.stereotype.Repository;

@Repository
public class VocaStudyDAOImpl implements VocaStudyDAO 
{
	@Inject
    private SqlSession sqlSession;
    private static String namespace = "vocaStudyMapper";
	
	@Override
	public int selectVocaCount(String diff) throws Exception 
	{
		return sqlSession.selectOne(namespace + ".selectVocaCount", diff);
	}
	
	@Override
	public String selectStudyingPage(String id, String diff) throws Exception 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("diff", diff);
		
		return sqlSession.selectOne(namespace + ".selectStudyingPage", map);
	}

	@Override
	public void insertVoca(VocaVO vo) throws Exception 
	{
		sqlSession.insert(namespace + ".insertVoca", vo);
	}

	@Override
	public void insertForm(FormVO vo) throws Exception
	{
        sqlSession.insert(namespace + ".insertForm", vo);
	}
	
	@Override
	public void insertDiff(DifficultyVO vo) throws Exception
	{
		sqlSession.insert(namespace + ".insertDiff", vo);
	}
	
	@Override
	public void insertStudy(StudyVO vo) throws Exception 
	{
		sqlSession.insert(namespace + ".insertStudy", vo);
	}
	
	@Override
	public void deleteStudy(StudyVO vo) throws Exception 
	{
		sqlSession.delete(namespace + ".deleteStudy", vo);
	}
	
	@Override
	public List<DifficultyVO> selectDiffList() throws Exception 
	{
		return sqlSession.selectList(namespace + ".selectDiffList");
	}
	
	@Override
	public List<VocaVO> selectVocaList(String diff, int start, int end) throws Exception 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("diff", diff);
		map.put("start", start);
		map.put("end", end);
		
		return sqlSession.selectList(namespace + ".selectVocaList", map);
	}

	@Override
	public List<VocaVO> selectStudyVocaList(StudyVO vo) throws Exception 
	{
		return sqlSession.selectList(namespace + ".selectStudyVocaList", vo);
	}


}
