package org.engvoca.service;

import java.util.List;

import javax.inject.Inject;

import org.engvoca.dao.VocaStudyDAO;
import org.engvoca.domain.DifficultyVO;
import org.engvoca.domain.FormVO;
import org.engvoca.domain.StudyVO;
import org.engvoca.domain.VocaVO;
import org.springframework.stereotype.Service;


@Service
public class VocaStudyServiceImpl implements VocaStudyService 
{
	@Inject
    private VocaStudyDAO dao;
	
	@Override
	public int selectVocaCount(String diff) throws Exception 
	{
		return dao.selectVocaCount(diff);
	}
	
	@Override
	public String selectStudyingPage(String id, String diff) throws Exception 
	{
		return dao.selectStudyingPage(id, diff);
	}

	@Override
	public void insertVoca(VocaVO vo) throws Exception 
	{
		dao.insertVoca(vo);
	}

	@Override
	public void insertForm(FormVO vo) throws Exception 
	{
		dao.insertForm(vo);
	}
	
	@Override
	public void insertDiff(DifficultyVO vo) throws Exception
	{
		dao.insertDiff(vo);
	}
	
	@Override
	public void insertStudy(StudyVO vo) throws Exception 
	{
		dao.insertStudy(vo);
	}
	
	@Override
	public void deleteStudy(StudyVO vo) throws Exception 
	{
		dao.deleteStudy(vo);
	}
	
	@Override
	public List<DifficultyVO> selectDiffList() throws Exception 
	{
		return dao.selectDiffList();
	}
	
	@Override
	public List<VocaVO> selectVocaList(String diff, int start, int end) throws Exception 
	{
		return dao.selectVocaList(diff, start, end);
	}

	@Override
	public List<VocaVO> selectStudyVocaList(StudyVO vo) throws Exception 
	{
		return dao.selectStudyVocaList(vo);
	}
}
