console.log('board js 열림')

// 1. 카테고리 등록
function setCategory() {
    console.log('setCategory 시작')

    let cname = document.querySelector('.cname').value;
    $.ajax({
        url : "board/category/write",
        method : 'POST',
        data : JSON.stringify({"cname":cname}),
        contentType : 'application/json',
        success : (r)=>{
            console.log(r);
            if(r==true){
                document.querySelector('.cname').value = '';
                getCategory();
            } // if e
        } // success e
    }) // ajax e
} // setCategory e
// 2. 카테고리 모두 출력
getCategory()
function getCategory(){
    console.log('getCategory 시작')
    $.ajax({
        url : "board/category/list",
        method : 'GET',
        success : (r)=>{
            // 반환타입이 ArrayList이면 Array로 들어와 forEach가 가능하지만
            // Map으로 반환 받을 시 Object로 들어와 forEach 불가능
            console.log(r);
            let html = '<button onclick="selectorCno(0)" type="button">전체보기</button>';
            for( let cno in r ){
                console.log("필드/키 값 : " + cno);
                html += `<button onclick="selectorCno(${cno})" type="button">${r[cno]}</button>`
            }
            document.querySelector('.categorylistbox').innerHTML = html;
        }
    })
} // getCategory e
// 3. 카테고리 선택
function selectorCno(cno){
    //console.log(cno+'번째 카테고리 선택')
    selectCno = cno; // 이벤트로 선택한 카테고리 번호를 전역변수에 대입
    getBoard(cno)
}

// 4. 게시물 쓰기
let selectCno = 0; // 선택된 카테고리번호 [기본값 : 0 (전체보기)
function setBoard() {
    let btitle = document.querySelector('.btitle').value;
    let bcontent = document.querySelector('.bcontent').value;
    if(selectCno==0){
        alert('작성할 게시물의 카테고리 선택해주세요');
        return;
    }
    $.ajax({
        url : "board/write",
        method : "POST",
        data : JSON.stringify({
            "btitle":btitle,
            "bcontent":bcontent,
            "cno" : selectCno
            }),
        contentType : "application/json",
        success : (r)=>{
            console.log(r);
            if(r==4){
                alert('글쓰기 성공');
                document.querySelector('.btitle').value = '';
                document.querySelector('.bcontent').value = '';
                getBoard(selectCno);
            }
        }
    })
}

// 5. 게시물 출력 [ 선택된 카테고리의 게시물 출력 ]
getBoard(0)
function getBoard(cno){
    selectCno = cno;
    $.ajax({
        url : "/board/list",
        method : "GET",
        data : {"cno":selectCno},
        success : (r)=>{
            //console.log(r);
            let html = `<tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>`;
            r.forEach((o)=>{
                html += `<tr>
                            <td>${o.bno}</td>
                            <td onclick="getboardinfo(${o.bno})">${o.btitle}</td>
                            <td>${o.mname}</td>
                            <td>${o.bdate}</td>
                            <td>${o.bview}</td>
                        </tr>`
            })
            document.querySelector('.boardlistbox').innerHTML = html;
        }
    })
}
// 내가 작성한(로그인 되어있는 가정) 게시물
function myboards(){
    $.ajax({
        url : "/board/myboards",
        method : "get",
        success : (r)=>{
            //console.log(r);
            let html = `<tr>
                            <th>번호</th>
                            <th >제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>`;
            r.forEach((o)=>{
                html += `<tr>
                            <td>${o.bno}</td>
                            <td onclick="getboardinfo(${o.bno})">${o.btitle}</td>
                            <td>${o.mname}</td>
                            <td>${o.bdate}</td>
                            <td>${o.bview}</td>
                        </tr>`
            })
            document.querySelector('.boardlistbox').innerHTML = html;
        }
    })
}
// 선택한 게시물의 정보 가져오기
function getboardinfo(bno){
    $.ajax({
        url : "/board/info",
        method : "get",
        data : {"bno":bno},
        success : (r)=>{
            console.log(r);
            let html = `
                <div><h5>제목 : ${r.btitle}</h5></div>
                <div><span>작성자 : </span><span>${r.mname}</span></div>
                <div><span>카테고리 : </span><span>${r.cname}</span></div>
                <div>내용 : ${r.bcontent}</div>
            `
            let memberinfo =  getminfo();
            console.log(memberinfo)
            if(r.mname==memberinfo.mname){
                html += `<button onclick ="bdelete(${r.bno})" type="button">삭제</button>`
            }
            document.querySelector('.boardbox').innerHTML = html;
        }
    })
}

// 선택한 게시물의 작성자 정보 가져오기
function getminfo(){
    let memberinfo;
    $.ajax({
        url : "/member/info",
            method : "get",
		    async : false,
            success : (r)=>{
                console.log(r)
                memberinfo = r;
            }
    })
    return memberinfo;
}

// 게시글 삭제
function bdelete(bno){
    $.ajax({
        url : "board/delete",
        method : "delete",
        data : {"bno":bno},
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('삭제되었습니다.')
                getBoard(0);
                document.querySelector('.boardbox').innerHTML = '';
            }
        }
    })
}


/*
    해당 변수의 자료형 확인 Prototype
    Array : forEach 가능
    { Object , Object, Object }
    Object : forEach 불가능 --> for( let key in Object ){ } : 객체 내 key를 하나씩 호출
    {
        필드명 : 값,
        필드명 : 값,
        필드명 : 값
    }
    Object[필드명] : 해당 필드의 값 호출
*/