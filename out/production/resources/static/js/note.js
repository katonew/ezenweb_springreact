console.log('note js 열림');
// 1. 등록
    // JSON.stringify() : json타입에서 문자열 타입으로 변환
    // json.parse : 문자열 타입에서 json 타입으로 변환
function onwrite(){
    //alert('onwrite 실행');

    // ajax 이용한 @Postmapping에게 요청 응답
    $.ajax({
        url: "/note/write", // 매핑 주소값
        method : "post", // 매핑 HTTP 메소드
        // body 값에 JSON형식의 문자열 타입 // contentType : "application/json"
        data : JSON.stringify({"ncontents" : document.querySelector(".ncontents").value}),
        contentType : "application/json",
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('글쓰기 성공');
                document.querySelector(".ncontents").value = "";
                onget();
            }else{alert('글쓰기 실패')}
        } // scuccess e
    }) // ajax e
}// onwrite e

// 2. 호출
onget();
function onget(){
    $.ajax({
        url: "/note/get",
        method : "get",
        success : (r)=>{
            console.log(r);
            let html = `<tr>
                           <th>번호</th>
                           <th>내용</th>
                           <th>비고</th>
                       </tr>`
            r.forEach((o)=>{
                html += `<tr>
                            <td>${o.nno}</td>
                            <td>${o.ncontents}</td>
                            <td>
                                <button onclick="ondelete(${o.nno})" type="button">삭제</button>
                                <button onclick="onupdate(${o.nno})" type="button">수정</button>
                            </td>
                         <tr>`
            })
            document.querySelector(".noteTable").innerHTML = html;
        }
    })
}

// 3. 삭제
function ondelete(nno){
    $.ajax({
        url: "/note/delete",
        method : "delete",
        data : {"nno" : nno},
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('삭제 성공');
                onget();
            }else{alert('삭제 실패')}
        }
    })
}

// 4. 수정
function onupdate(nno){
    let ncontents = prompt("수정할 내용을 입력하세요");
    $.ajax({
        url: "/note/update",
        method : "put",
        data : JSON.stringify({"nno" : nno, "ncontents" : ncontents}),
        contentType : "application/json",
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('수정 성공');
                onget();
            }else{alert('수정 실패')}
        }
    })
}