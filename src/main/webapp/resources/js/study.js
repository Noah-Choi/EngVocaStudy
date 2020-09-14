let viewCount = 0;
let nowView = 0;
let iKnowFlag = true;
let goOut = false;

$(document).ready(function() 
{
	setting();
	bindBtn();
	
	exitPage();
	doNotRefresh();
});

function exitPage()
{
	$(window).bind("beforeunload", function (e)
	{
		if(goOut == false)
			sendJsonPost("save");
	});
}

function doNotRefresh()
{
    $(document).keydown(function(event) 
    {
    	//새로고침 방지
    	if((event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116))
    	{
    		console.log("새로고침 방지 실시!");
    		return false;
    	}
    });
}

function setting()
{
	iKnowFlag = true;
	nowView = 0;
	
	viewCount = voArr.length;
	$('.allView').text(viewCount);
	
	vocaMix();
	nextVoca()
}

function sendJsonPost(mode)
{
	let url = "";
	let data = "";
	
	if(mode == "save")
	{
		goOut = true;
		url = "/web/vocastudy/saveStudy";
		let sendVocaList = new Array() ;
		
		let listLen = voArr.length;
		for(let i = 0; i < listLen; i++)
		{
			if(voArr[i].iKnow == true)
				sendVocaList.push(voArr[i]);
	    };
	     
	    if(sendVocaList.length == 0)
	    	return;
	    
	 	data = 
	 	{
	 		diff: diff,
	 		page: page,
	 		vocaList: sendVocaList,
	 	};
	}
	else if(mode == "delete")
	{
		goOut = true;
		url  ="/web/vocastudy/deleteStudy";
		data = 
	 	{
	 		diff: diff,
	 		page: page,
	 		mode: "clear",
	 	};
	}
	
	let jsonData = JSON.stringify(data);
    $.ajax({
        url: url,
        type: "POST",
        data: {"data": jsonData},
    });
}

//단어 순서 섞기
function vocaMix()
{
	for(let i = 0; i < viewCount * 3; i++)
	{
		let random = Math.floor(Math.random() * viewCount);
		
		let temp = {};
		temp.eng = voArr[random].eng;
		temp.kor = voArr[random].kor;
		temp.iKnow = voArr[random].iKnow;
		
		voArr.splice(random, 1);
		voArr.push(temp);
	}
}

function nextStage()
{
	 let listLen = voArr.length;
     for(let i = 0; i < listLen; i++)
     {
         if(voArr[i].iKnow == true)
         {
        	 voArr.splice(i, 1);
        	 i--;
        	 listLen--;
         }
     };
  
     setting();
}

function nextVoca()
{
	nowView++;
	if(nowView - 1 == viewCount)
	{
		if(iKnowFlag == false)
		{
			alert("모르는 단어가 존재합니다.\n모르는 단어를 섞어 다시 표시합니다.");
			nextStage();
		}
		else
		{
			sendJsonPost("delete");
			
			alert("다음 항목으로 넘어가주시기 바랍니다.");
			location.href="/web/vocastudy/list?diff=" + diff;
		}
	}
	else
	{
		$('.nowView').text(nowView);
		
		$('.eng').text(voArr[nowView - 1].eng);
		$('.kor').text(voArr[nowView - 1].kor);
		
		$(".kor").css("visibility", "hidden");
		$("#showKorean").css("visibility", "visible");
		$("#addVoca").css("visibility", "visible");
	}
}

function bindBtn()
{
	clickShowKoreanBtn();
	clickAddVocaBtn();
	clickkeepGoingBtn();
	clickIknowBtn();
	clickgoOutBtn()
	clickSpeaker();
}

function clickShowKoreanBtn()
{
    $("#showKorean").on("click", function()
    {
    	
    	$(".kor").css("visibility", "visible");
    	$("#showKorean").css("visibility", "hidden");
    });
}

function clickAddVocaBtn()
{
    $("#addVoca").on("click", function()
    {
    	$("#addVoca").css("visibility", "hidden");
    });
}

function clickkeepGoingBtn()
{
    $("#keepGoing").on("click", function()
    {
    	iKnowFlag = false;
    	voArr[nowView - 1].iKnow = false;
    	
    	nextVoca();
    });
}

function clickIknowBtn()
{
    $("#iKnow").on("click", function()
    {
    	voArr[nowView - 1].iKnow = true;
    	
    	nextVoca();
    });
}

function clickgoOutBtn()
{
    $("#goOut").on("click", function()
    {
    	let result = confirm("나가시겠습니까?");
    	if(result == true)
    	{
    		sendJsonPost("save");
    		location.href="/web/vocastudy/list?diff=" + diff;
    	}
    });
}

function clickSpeaker()
{
    $(".speaker").on("click", function()
    {
        let text = $('.eng').text();
        startTTS(text);
    });
}

//TTS(음성출력) 함수
function startTTS(text)
{
    let message = new SpeechSynthesisUtterance(text);
    let voices = speechSynthesis.getVoices();

    message["volume"] = "1";        //사운드 크기
    message["rate"] = "1";          //말 속도
    message["pitch"] = "1";
    message.voice = voices["6"];
    
    speechSynthesis.speak(message);
}
