package org.engvoca.service;

import java.util.List;

import org.engvoca.domain.DifficultyVO;
import org.engvoca.domain.FormVO;
import org.engvoca.domain.StudyVO;
import org.engvoca.domain.VocaVO;

public interface VocaStudyService 
{
	public int selectVocaCount(String diff) throws Exception;
	
	public String selectStudyingPage(String id, String diff) throws Exception;
	
	public void insertVoca(VocaVO vo) throws Exception;
	
	public void insertForm(FormVO vo) throws Exception;
	
	public void insertDiff(DifficultyVO vo) throws Exception;
	
	public void insertStudy(StudyVO vo) throws Exception;
	
	public void deleteStudy(StudyVO vo) throws Exception;
	
	public List<DifficultyVO> selectDiffList() throws Exception;
	
	public List<VocaVO> selectVocaList(String diff, int start, int end) throws Exception;
	
	public List<VocaVO> selectStudyVocaList(StudyVO vo) throws Exception;
}
