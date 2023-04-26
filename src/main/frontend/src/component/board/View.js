import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
import Reply from './Reply'

export default function View( props ) {
   const params = useParams();

   const [ board , setBoard ] = useState( {} );
   useEffect( ()=>{
        axios.get("/board/getboard" , { params : { bno : params.bno }})
            .then( (r) => {
                console.log( r.data );
                setBoard( r.data );
            })
   } , [ ] )

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

   // 1. 현재 로그인된 회원이 들어왔으면
   const btnBox =
                login != null && login.mno == board.mno
                ? <div> <button onClick={ onDelete }>삭제</button>
                        <button onClick={ onUpdate }>수정</button> </div>
                : <div> </div>

   return ( <>
        <div><h6> 카테고리 : {board.cname} </h6> </div>
        <div><h6> 작성자 : {board.mname} </h6> </div>
        <div><h6> 작성일 : {board.bdate} </h6> </div>
        <div><h3> 제목 : {board.btitle} </h3> </div>
        <div><h3> {board.bcontent} </h3> </div>
        <Reply bno = {params.bno} />
        { btnBox }

   </>)
}