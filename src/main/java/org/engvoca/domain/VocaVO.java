package org.engvoca.domain;

public class VocaVO 
{
	private String eng;
	private String kor;
	private String form;
	private String diff;
	private int seq;
	
	public String getEng() 
	{
		return eng;
	}
	
	public void setEng(String eng) 
	{
		this.eng = eng;
	}
	
	public String getKor() 
	{
		return kor;
	}
	
	public void setKor(String kor) 
	{
		this.kor = kor;
	}
	
	public String getForm() 
	{
		return form;
	}
	
	public void setForm(String form) 
	{
		this.form = form;
	}
	
	public String getDiff() 
	{
		return diff;
	}
	
	public void setDiff(String diff) 
	{
		this.diff = diff;
	}
	
	public int getSeq() 
	{
		return seq;
	}
	
	public void setSeq(int order) 
	{
		this.seq = order;
	}
	
	public boolean equals(Object o)
	{
		if(eng.equals(((VocaVO)o).eng))
			return true;
		else
			return false;
	}
}
