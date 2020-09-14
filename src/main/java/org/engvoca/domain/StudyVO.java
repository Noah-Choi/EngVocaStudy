package org.engvoca.domain;

public class StudyVO 
{
	private String id;
	private String diff;
	private String page;
	private String eng;
	private boolean know;
	
	public String getId() 
	{
		return id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getDiff() 
	{
		return diff;
	}
	
	public void setDiff(String diff) 
	{
		this.diff = diff;
	}
	
	public String getPage() 
	{
		return page;
	}
	
	public void setPage(String page) 
	{
		this.page = page;
	}
	
	public String getEng() 
	{
		return eng;
	}
	
	public void setEng(String eng) 
	{
		this.eng = eng;
	}
	
	public boolean isKnow() 
	{
		return know;
	}
	
	public void setKnow(boolean know) 
	{
		this.know = know;
	}
}
