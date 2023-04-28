import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
import Reply from './Reply'


export default function View( props ) {
   const params = useParams();

   const getboard = () =>{
        axios.get("/board/getboard" , { params : { bno : params.bno }})
            .then( (r) => {
                console.log( r.data );
                setBoard( r.data );
            })
   }

   const [ board , setBoard ] = useState( {
        replyDtoList : []
   } );
   useEffect( ()=>{
        getboard();
   } , [] )


    // 삭제 함수
     const onDelete = () =>{
           axios.delete("/board" , { params : { bno : params.bno }})
               .then( r => {
                   console.log( r.data );
                   if( r.data == true ){
                       alert('삭제 성공 ');
                       window.location.href="/board/list";
                   }else{ alert('삭제 실패')}
               })
      }
   const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )

   const onUpdate = ()=>{
        window.location.href="/board/update?bno="+board.bno;
   }
    // 댓글 작성 함수
       const addReply = (rcontent)=>{
                   console.log(login.mno)
                   let info = {
                       rcontent : rcontent,
                       rindex : 0,
                       bno : board.bno,
                       mno : login.mno
                   }
                   axios.post("/reply", info)
                       .then(r=>{
                           if(r.data==true){
                               alert('댓글 작성 완료');
                               document.querySelector('#rcontent').value = '';
                               getboard();
                           }else{
                               alert('댓글 작성 실패');
                           }

                       })
                };
    // 댓글 삭제 함수
    const rdelete = (rno)=>{
        axios.delete("/reply",{params : {"rno": rno}}).then(r=>{
            console.log(r)
            if(r.data==true){
                alert('댓글이 삭제되었습니다.')
                getboard();
            }
        })
    }
    // 댓글 수정 함수
    const rupdate = (rno, rcontent)=>{
        if(!rcontent){
            alert('수정 내용이 없습니다.')
            return;
        }
        let info = {
           rcontent : rcontent,
           rno : rno
        }
        axios.put("/reply",info).then(r=>{
            console.log(r)
            if(r.data==true){
                alert('댓글이 수정되었습니다.')
                getboard();
            }
        })
    }
    //대댓글 작성함수
    const addReReply = (rno,rcontent)=>{
        let info = {
           rcontent : rcontent,
           rindex : rno,
           bno : board.bno,
           mno : login.mno
       }
       axios.post("/reply", info)
           .then(r=>{
               if(r.data==true){
                   alert('대댓글 작성 완료');
                   document.querySelector('#rereply'+rno).value = '';
                   getboard();
               }else{
                   alert('대댓글 작성 실패');
               }

           })
    }

   // 1. 현재 로그인된 회원이 들어왔으면
   const btnBox =
        login != null && login.mno == board.mno
        ? <div> <Button onClick={ onDelete } variant="outlined">삭제</Button>
                <Button onClick={ onUpdate } variant="outlined">수정</Button> </div>
        : <div> </div>



   return ( <>
        <Container>
            <div style={{
                display:'flex',
                justifyContent : 'space-between',
                border : '1px solid black'
            }}>
                <div><h1> 제목 : {board.btitle} </h1> </div>
                <div style={{border : '1px solid black'}}>
                    <div><h6> 카테고리 : {board.cname} </h6> </div>
                    <div><h6> 작성자 : {board.mname} </h6> </div>
                    <div><h6> 작성일 : {board.bdate} </h6> </div>
                </div>
            </div>
            <div style={{
                border : '1px solid black',
                width : '100%',
                height : '500px'
            }}><h3> {board.bcontent} </h3> </div>
            <Reply
                replyList={board.replyDtoList}
                addReply={addReply}
                rdelete={rdelete}
                rupdate={rupdate}
                addReReply={addReReply}
                />
            { btnBox }
        </Container>

   </>)
}