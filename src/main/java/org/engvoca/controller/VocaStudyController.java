package org.engvoca.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.engvoca.domain.DifficultyVO;
import org.engvoca.domain.FormVO;
import org.engvoca.domain.MemberVO;
import org.engvoca.domain.StudyVO;
import org.engvoca.domain.VocaVO;
import org.engvoca.service.MemberService;
import org.engvoca.service.VocaStudyService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vocastudy/")
public class VocaStudyController 
{
 	private VocaStudyService service;
 	private MemberService memberService; 
 	
 	final int VIEW_VOCA_CNT_DIVISION = 10;		//보유줄 단어 갯수 구분
 	final int REVIEW_DIVISION = 6;				//복습 몇번마다 할지 구분
 	
 	List<DifficultyVO> diffList; 
 	Map<String, List<String>> diffToStudyListMap;
 	Map<String, List<VocaVO>> studyToVocaListMap;
    
    @Autowired
    public VocaStudyController(VocaStudyService service, MemberService memberService) throws Exception
    {
    	this.service = service;
    	this.memberService = memberService;
    	
    	diffList = new ArrayList<DifficultyVO>();
    	diffToStudyListMap = new HashMap<String, List<String>>();
    	studyToVocaListMap = new HashMap<String, List<VocaVO>>();
    	
    	makeDiffList();
    	makeStudyListMap();
    	makeVocaListMap();
    }
    
    @RequestMapping(value="/main",method=RequestMethod.GET)
    public void main() throws Exception
    {
        System.out.println("/vocaStudy/main");
    }
    
    @RequestMapping(value="/diff",method=RequestMethod.GET)
    public void diff(Model model) throws Exception
    {
        System.out.println("/vocaStudy/diff");
        
        model.addAttribute("diffList", diffList);
    }
    
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public void list(@RequestParam("diff") String diff, HttpSession session, Model model) throws Exception
    {
        System.out.println("/vocaStudy/list");
        
        MemberVO memberVO = (MemberVO)session.getAttribute("member");
        if(memberVO != null)
        {
        	String studyingPage = service.selectStudyingPage(memberVO.getId(), diff);
        	if(studyingPage == null)
        	{
        		if(0 < diffToStudyListMap.get(diff).size())
        			studyingPage = diffToStudyListMap.get(diff).get(0);
        	}
        	
        	model.addAttribute("studyingPage", studyingPage);
        }
        
        model.addAttribute("pageList", diffToStudyListMap.get(diff));
        model.addAttribute("diff", diff);
    }
    
    @RequestMapping(value="/study",method=RequestMethod.GET)
    public void study(@RequestParam("diff") String diff, @RequestParam("page") String page, HttpSession session, Model model) throws Exception
    {
        System.out.println("/vocaStudy/study?diff=" + diff + "&page=" + page);
        
        MemberVO memberVO = (MemberVO)session.getAttribute("member");
        if(memberVO != null)
        {
        	StudyVO studyVO = new StudyVO();
        	studyVO.setId(memberVO.getId());
        	studyVO.setDiff(diff);
        	studyVO.setPage(page);
            
        	String key = diff + " " + page;
        	
        	List<VocaVO> vocaList = service.selectStudyVocaList(studyVO);
            List<VocaVO> copyList = new ArrayList<>(studyToVocaListMap.get(key));
            
            for(VocaVO vo : vocaList)
            	copyList.remove(vo);
            
            model.addAttribute("diff", diff);
            model.addAttribute("page", page);
            model.addAttribute("voList", copyList);
        }
    }
    
    @RequestMapping(value="/saveStudy",method=RequestMethod.POST)
    public void saveStudy(HttpSession session, String data) throws Exception 
    {
    	System.out.println("/vocaStudy/saveStudy");
    	
    	MemberVO memberVO = (MemberVO)session.getAttribute("member");
    	if(memberVO != null)
    	{
    		JSONParser jsonParse = new JSONParser(); 
    		JSONObject jsonObj = (JSONObject)jsonParse.parse(data); 
    		JSONArray jsonArray = (JSONArray)jsonObj.get("vocaList");

    		String diff = (String)jsonObj.get("diff");
    		String page = (String)jsonObj.get("page");
    		
    		if(jsonArray != null)
    		{
    			for(int i = 0; i < jsonArray.size(); i++) 
        		{
        			JSONObject object = (JSONObject)jsonArray.get(i);
        			StudyVO vo = new StudyVO();
        			vo.setId(memberVO.getId());
        			vo.setDiff(diff);
        			vo.setPage(page);
        			vo.setEng((String)object.get("eng"));
        			
        			service.insertStudy(vo);
        		}
    		}
    	}
    }
    
    @RequestMapping(value="/deleteStudy",method=RequestMethod.POST)
    public void deleteStudy(HttpSession session, String data) throws Exception 
    {
    	System.out.println("/vocaStudy/deleteStudy");
    	
    	MemberVO memberVO = (MemberVO)session.getAttribute("member");
    	if(memberVO != null)
    	{
    		JSONParser jsonParse = new JSONParser(); 
    		JSONObject jsonObj = (JSONObject)jsonParse.parse(data); 

    		String diff = (String)jsonObj.get("diff");
    		String page = (String)jsonObj.get("page");
    		String mode = (String)jsonObj.get("mode");
    		
    		StudyVO vo = new StudyVO();
    		vo.setId(memberVO.getId());
    		vo.setDiff(diff);
    		vo.setPage(page);
    			
    		service.deleteStudy(vo);
    		
    		String nextDiff = "";
			String nextPage = "";
			
    		if(mode.equals("clear") == true)
    		{
    			int index = diffToStudyListMap.get(diff).indexOf(page);
    			if(index != -1 && index + 1 < diffToStudyListMap.get(diff).size())
    			{
    				nextDiff = diff;
    				nextPage = diffToStudyListMap.get(diff).get(index + 1);
    			}
    			else if(index != -1 && index + 1 == diffToStudyListMap.get(diff).size())
    			{
    				nextDiff = diff;
    				nextPage = page;
    			}
    		}
    		else if(mode.equals("out") == true)
    		{
    			nextDiff = diff;
    			nextPage = (String)jsonObj.get("nextPage");
    		}
    		
    		if(nextDiff.equals("") != true && nextPage.equals("") != true)
			{
				vo = new StudyVO();
				vo.setId(memberVO.getId());
				vo.setDiff(nextDiff);
				vo.setPage(nextPage);
				vo.setEng("");
    			
    			service.insertStudy(vo);	
			}
    	}
    }
    
    void makeDiffList() throws Exception
    {
    	diffList.clear();
    	diffList = service.selectDiffList();
    }
    
    void makeStudyListMap() throws Exception
    {
    	diffToStudyListMap.clear();
    	
    	for(DifficultyVO vo: diffList) 
    	{
    		int vocaCount = service.selectVocaCount(vo.getDiff());
    		
    		int mok = vocaCount / VIEW_VOCA_CNT_DIVISION;
        	int rest = vocaCount % VIEW_VOCA_CNT_DIVISION;
        	
        	List<String> strList = new ArrayList<String>();
        	for(int i = 1; i <= mok; i++)
        	{
        		int start = i / REVIEW_DIVISION;
        		
        		if(i % REVIEW_DIVISION == 0)
        			start--;
        		
        		String temp = Integer.toString(start * REVIEW_DIVISION * VIEW_VOCA_CNT_DIVISION);
        		temp += "~" + Integer.toString(i * VIEW_VOCA_CNT_DIVISION);
        		strList.add(temp);
        		
        		//이 다음이 복습이라면 복습 보기 추가
        		if(i % REVIEW_DIVISION == 0)
        		{
        			//첫 번쨰 구분 제외하고 2번째 부터는 0~현재 까지 복습 
        			if(i != REVIEW_DIVISION)
        			{
        	    		temp = "0~" + Integer.toString(i * VIEW_VOCA_CNT_DIVISION);
        	    		strList.add(temp);
        			}
        		}
        	}
        	
        	if(0 < rest)
        	{
        		int start = mok / REVIEW_DIVISION;
        		
        		String temp = Integer.toString(start * REVIEW_DIVISION * VIEW_VOCA_CNT_DIVISION);
        		temp += "~" + Integer.toString(mok * VIEW_VOCA_CNT_DIVISION + rest);
        		strList.add(temp);
        	}
        	
        	diffToStudyListMap.put(vo.getDiff(), strList);
    	}
    }
    
    void makeVocaListMap() throws Exception
    {
    	studyToVocaListMap.clear();
    	
    	Set<Map.Entry<String, List<String>>> entries = diffToStudyListMap.entrySet();
    	for(Map.Entry<String, List<String>> entry : entries) 
    	{ 
    		List<String> studyList = entry.getValue();
    		for(int i = 0; i < studyList.size(); i++)
        	{
        		String temp = studyList.get(i);
        		int pos = temp.indexOf("~");
        		if(pos != -1)
        		{
        			int start = Integer.parseInt(temp.substring(0, pos));
            		int end = Integer.parseInt(temp.substring(pos + 1));
            		
            		String key = entry.getKey() + " " + temp;
            		studyToVocaListMap.put(key, service.selectVocaList(entry.getKey(), start, end));
        		}
        	}
    	}
    }
    
    @RequestMapping(value="/test",method=RequestMethod.GET)
    public String test() throws Exception
    {
        System.out.println("/vocaStudy/test");
        
//        String id[] = {"dnjswns4568", "dmswl4568", "dydtjr4568"};
//        for(int i = 0; i < id.length; i++)
//        {
//        	MemberVO vo = new MemberVO();
//        	vo.setId(id[i]);
//        	vo.setPw("1234");
//        	vo.setName("아무개");
//        	
//            memberService.insertMember(vo);	
//        }
        
        String form[] = {"noum", "verb", "adjective", "adverb"};
        String kor[] = {"명사", "동사", "형용사", "부사"};
        for(int i = 0; i < form.length; i++)
        {
        	FormVO vo = new FormVO();
        	vo.setForm(form[i]);
        	vo.setKor(kor[i]);
        
        	service.insertForm(vo);
        }
		
        String diff[] = {"easy", "normal", "hard"};
        String kor2[] = {"쉬움", "보통", "어려움"};
        for(int i = 0; i < diff.length; i++)
        {
        	DifficultyVO vo = new DifficultyVO();
        	vo.setDiff(diff[i]);
        	vo.setKor(kor2[i]);
        	vo.setSeq(i);
        	
        	service.insertDiff(vo);
        }
        
        String eng[] = {"culture", "experience", "education", "symbol", "effect", "liberty", "affair", "comfort", "tradition", "subject", "object", "source", "revolution", "pollution", "system", "triumph", "respect", "communication", "foundation", "glory", "situation", "competition", "prairie", "effort", "section", "rein", "solution","hono(u)r", "unity", "population", "direction", "dialog(ue)", "republic", "method", "increase", "decrease", "amount", "ancestor", "voyage", "sculpture", "instrument", "figure", "activity", "cause", "worth", "accident", "adventure", "view", "relative", "superstition", "habit", "wealth", "treasure", "universe", "adult", "feast", "resources", "ruin", "monument", "information", "appetite", "stethoscope", "mystery", "thermometer", "burden", "series", "oath", "appointment", "clue", "debt", "hydrogen", "control", "uniform", "design", "damage", "custom", "traffic", "sophomore", "temperature", "limit", "statue", "furniture", "parade", "public", "pilgrim", "greeting", "language", "opinion", "athlete", "supply", "surface", "electricity", "order", "spirit", "purpose", "promise", "project", "government", "exercise", "comparison"};
        String kor3[] = {"문화, 교양", "경험", "교육", "상징", "결과, 영향, 효과", "자유", "사건, 일", "안락, 위안", "전통, 전설", "학과, 주제, 주어", "사물, 목적, (동)반대하다", "출처, 근원", "혁명", "오염", "조직, 체계, 지도", "승리", "존경 동 존경하다", "전달, 교통", "기초", "영광", "위치, 사태", "경쟁", "대초원", "노력", "부분, 구역", "고삐", "해결, 용해", "명예/경의, 동 존경하다", "통일/일치", "인구", "방향, 지시", "대화", "공화국", "방법", "증가, [동]증가하다", "감소, [동]감소하다", "양, 액수, 총계", "조상, 선조", "항해", "조각(품)", "기계, 기구, 도구", "숫자, 계산, 모습, 인물", "활동", "원인, 이유", "가치, ~에 가치가 있는", "사고, 뜻밖의 사건", "모험", "경치, 의견", "친척, 관계가 있는", "미신", "습관, 버릇", "재산, 부", "보물", "우주, 전 세계", "성인, 성인의", "향연, 잔치", "자원, 수단", "파멸", "기념비, 기념물", "정보, 지식, 통지", "식욕", "청진기", "신비, 불가사의", "온도계", "무거운 짐, 짐을 지우다", "연속, 시리즈", "맹세, 선서", "임명, 약속", "실마리, 단서", "은혜, 빚", "수소", "통제, 지배, 통제하다, 지배하다", "제복, 한 모양의", "계획, 설계, 계획하다, 디자인 하다", "손해, 손상", "습관, 풍습", "교통", "학년생", "온도, 체온", "제한하다, 한계, 제한", "조각상", "가구", "행렬", "공중(사회), 공공의, 공중의", "순례자", "인사, 축하", "언어", "의견", "운동선수", "공급, 공급하다", "표면", "전기", "순서, 명령", "정신", "목적", "가망, 전망, 약속", "계획, 계획하다", "정부, 정치의", "운동, 연습하다", "비교"};
        
        for(int i = 0; i < eng.length; i++)
        {
        	VocaVO vo = new VocaVO();
        	vo.setEng(eng[i]);
        	vo.setKor(kor3[i]);
        	vo.setDiff("easy");
        	vo.setSeq(i);
        	
        	service.insertVoca(vo);
        }
        
        String eng1[] = {"account", "account for", "benefit from", "condense", "condense into", "better", "conduct", "accrue", "conductor", "confirmation", "be aware of", "biannual","accumulation", "confirmed", "beneficial", "beneficiary", "confiscate", "binding", "bill", "accurately", "achievement", "irretrievable", "issue", "be laid off","landfill", "landslide", "itinerary", "agenda", "mutually", "natural disaster", "needlessly", "jet lag", "keynote speech", "laboratory", "lack", "municipal", "mutual", "largely", "lastingly", "lavatory", "liberal", "reject", "substitute", "occur", "despise", "contempt", "gain", "avail", "educate", "resemble", "grasp", "reconsider", "support","irritate", "generous", "tolerant", "prove", "illustrate", "exemplify", "conservative", "progressive", "fortify", "strengthen", "reinforce", "multiply", "reproduce", "elicit", "extract", "implore", "appoint", "disgust", "preach", "worship", "yawn", "hiccup", "quarrel", "pillar", "servitude", "recreation", "punctuality","punctual", "fascination", "attraction", "vicious", "distrust", "suspicion", "endeavor", "devote", "pride", "proud", "discontinue", "ignore", "disregard", "neglect", "depend"};
        String kor4[] = {"계산", "~를 설명하다", "~로부터 혜택을 받다", "압축하다", "~로 압축하다", "~를 개선하다", "실시, 시행하다", "생기다, 얻어지다", "지휘자 , 차장", "확정서", "~를조심하다", "반년마다의", "축적, 축적물", "확인된", "혜택이 많은", "수혜자", "압수, 몰수하다", "구속력 있는, 의무적인", "계산서, 청구서, 법안", "정확히", "업적, 성과", "돌이킬 수 없는", "발행하다, 발급, 문제", "해고되다", "매립지", "사태, 산사태", "여행스케줄", "의사일정", "서로", "재해", "불필요한", "시차 부적응중", "기조연설", "실험실", "부족하다", "시의", "상호의", "주로, 대량으로", "영속적인, 영구적인", "기차, 비행기의 화장실", "자유주의자, 자유당원", "거절하다", "대리의", "일어나다, 나타나다", "경멸하다", "경멸(disdain), 멸시", "이익, 증가, 진보", "도움이 되다", "교육하다, 양성하다", "닮다", "쥠, 지배(력)", "재고하다", "지지, 지주, 지지물", "짜증나게(초조하게)하다", "관대한, 마음이 넓은", "관대한, 아량이 있는", "입증하다", "설명하다", "예시하다, 예증하다", "보수적인;전통적인", "전진하는, 누진적인", "견고하게 하다", "강하게하다, 강해지다", "보강하다", "늘리다, 번식시키다", "재생시키다;재현하다", "끌어내다", "끌어내다, 빼어내다", "간청(탄원, 애원)하다", "임명하다", "역겹게하다", "설교하다, 전도하다", "숭배(존경)하다", "하품하다", "딸꾹질", "네모진 촉이 달린 화살", "기둥, 주석", "노예 상태", "휴양, 원기회복", "시간엄수", "시간을 엄수하는", "매혹, 눈독들임", "끄는 힘, 인력, 흡인(력)", "사악한, 부도덕한, 타락한", "불신, 의혹, 의심하다", "느낌, 낌새챔, 의심, 협의", "노력하다", "바치다, ~에 몰두하다", "자만, 자부, 거만", "자랑하고 있는", "중지하다, 정지하다", "무시하다, 모르는체하다", "무시, 경시, 무시하다", "태만, 소홀", "의지하다, ~나름이다"};
        
        for(int i = 0; i < eng1.length; i++)
        {
        	VocaVO vo = new VocaVO();
        	vo.setEng(eng1[i]);
        	vo.setKor(kor4[i]);
        	vo.setDiff("normal");
        	vo.setSeq(i + 100);
        	
        	service.insertVoca(vo);
        }
        
        return "redirect:/vocastudy/main";
    }
}
