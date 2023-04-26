console.log('product.js 시작')

// 1. 등록
function onwrite(){
    let pname = document.querySelector('.pname').value;
    let pcontent = document.querySelector('.pcontent').value;
    $.ajax({
        url : "/item/write",
        method : "post",
        data : JSON.stringify({
            "pname" : pname,
            "pcontent" : pcontent
        }),
        contentType : "application/json",
        success : (r)=>{
            console.log(r)
            if(r==true){
                alert('등록 성공');
                getList()
                document.querySelector('.pname').value = "";
                document.querySelector('.pcontent').value = "";
            } //if e
            else{
                alert('등록 실패');
            }
        } // success e
    }) // ajax e
} // onwrite e

// 2. 출력
getList()
function getList(){
    $.ajax({
        url : "/item/get",
        method : "get",
        success : (r)=>{
            console.log(r)
            let html = `<tr>
                            <th>상품번호</th>
                            <th>상품명</th>
                            <th>상품설명</th>
                            <th>비고</th>
                        </tr>`;
            r.forEach((o)=>{
                   html += `
                   <tr>
                       <td>${o.pno}</td>
                       <td>${o.pname}</td>
                       <td>${o.pcontent}</td>
                       <td>
                            <button type="button" onclick="onupdate(${o.pno})">수정</button>
                            <button type="button" onclick="ondelete(${o.pno})">삭제</button>
                       </td>
                   </tr>
                   `
            })
            document.querySelector('.productList').innerHTML = html;
        } // success e
    }) // ajax e
} // getList e

function onupdate(pno){
    let pname = prompt('변경할 상품명 : ')
    let pcontent = prompt('변경할 상품설명 : ')
    $.ajax({
        url : "/item/update",
        method : "put",
        data : JSON.stringify({
            "pno" : pno,
            "pname" : pname,
            "pcontent" : pcontent
        }),
        contentType : "application/json",
        success : (r)=>{
            console.log(r)
            if(r==true){
                alert('수정 성공');
                getList()
            } //if e
            else { alert('수정 실패'); }
        } // success e
    }) // ajax e
}

function ondelete(pno){
    $.ajax({
        url : "/item/delete",
        method : "delete",
        data : { "pno" : pno},
        success : (r)=>{
            console.log(r)
            if(r==true){
                alert('삭제 성공');
                getList();
            }
            else { alert('삭제 실패'); }
        }

    })
}