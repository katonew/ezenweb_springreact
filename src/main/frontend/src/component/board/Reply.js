import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import {List, Paper, Container} from '@mui/material';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export default function Reply( props ) {

    //const bno = props.bno;
    //const [ replyList, setReplyList ] = useState([props.replyList])

    /*useEffect ( () => {
        axios.get("/reply",{params : {"bno": bno}})
            .then(r=>{
                console.log(r.data)
                setReplyList(r.data)
            })
    },[])*/
    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )

    // 댓글 작성 핸들러
    const addReplyHandler = ()=>{
        props.addReply( document.querySelector('#rcontent').value )
        }
    // 댓글 삭제 핸들러
    const rdeleteHandler = (e,rno)=>{
        console.log(rno)
        props.rdelete(rno)
    }
    // 댓글 수정 핸들러
    const rupdateHandler = (e,rno,rcontent)=>{
        let updatecontent = prompt('수정내용',rcontent)
        props.rupdate(rno,updatecontent)
    }
    // 대댓글 추가 핸들러
    const addReReplyHandler = (e,rno)=>{
        console.log(rno)
        let recontent = document.querySelector('#rereply'+rno).value
        console.log(recontent)
        props.addReReply(rno,recontent);
    }

    // 댓글 출력 조정
    const PrintReply = (list) =>{
        const tree = [];
        console.log('qwwewqew')
        list.forEach((o)=>{
            if(o.rindex===0){
                tree[o.rno]={
                    item : {
                        id : o.rindex,
                        rno : o.rno,
                        mname : o.mname,
                        rdate : o.rdate,
                        rcontent : o.rcontent
                    },
                    children : []
                }
            } else if(o.rindex!==0){
                {console.log(tree[o.rindex])}
                tree[o.rindex].children = {
                    item : {
                        id : o.rindex,
                        rno : o.rno,
                        mname : o.mname,
                        rdate : o.rdate,
                        rcontent : o.rcontent
                    }
                }
            } // if e
        }) // forEach e
        console.log(tree)
        const printList = tree.map((i) => {
          <TreeItem
              nodeId={i.item.rno}
              label={
                <div>
                  <h5> {i.item.mname} / {i.item.rdate}</h5>
                  <div> {i.item.rcontent} </div>
                  <div>
                    {login.mno == i.item.mno ?
                      <Button onClick={(e) => rupdateHandler(e, i.item.rno, i.item.rcontent)} variant="outlined">수정</Button>
                      : <></>}
                    {login.mno == i.item.mno ?
                      <Button onClick={(e) => rdeleteHandler(e, i.item.rno)} variant="outlined">삭제</Button>
                      : <></>}
                    <TextField fullWidth id={`rereply${i.item.rno}`} label="대댓글" variant="standard" />
                    <Button onClick={(e) => addReReplyHandler(e, i.item.rno)} variant="outlined">댓글달기</Button>
                  </div>
                </div>
              }
            />
        });
        return printList;
    }


    let replybox =
        <Paper style={{margin : 16}}>
            <TextField fullWidth className="rcontent" id="rcontent" label="댓글" variant="standard" />
            <Button onClick={addReplyHandler} variant="outlined"> 등록 </Button>
            <h3>댓글창</h3>
            {<TreeView>
                {console.log(PrintReply(props.replyList))}
            </TreeView>}
        </Paper>



    return (<>
        <Container>
            {replybox}
        </Container>
    </>)
}