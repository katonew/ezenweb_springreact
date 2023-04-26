console.log('update js 시작')

getInfo()
function getInfo() {
    $.ajax({
        url: '/member/info',
        type: 'get',
        dataType: 'json',
        success: (r)=> {
            console.log(r)
            document.querySelector('.mname').value = r.mname;
            document.querySelector('.mphone').value = r.mphone;
        }
    })
}

function onUpdate() {
    let info = {
            mname : document.querySelector('.mname').value,
            mphone : document.querySelector('.mphone').value,
        }

        $.ajax({
            url : "/member/info",
            method : "put",
            contentType : "application/json",
            data : JSON.stringify(info),
            success : (r)=>{
                console.log(r);
                if(r==true){
                    alert('수정 성공');
                    location.href = "/";
                }
            }
        })
}