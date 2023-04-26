import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {List, Paper, Container} from '@mui/material';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export default function Reply( props ) {

    let bno = props.bno;
    console.log( bno );
    let [ replyList, setReplyList ] = useState([])
    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )
    useEffect ( () => {
        axios.get("/reply",{params : {"bno": bno}})
        .then(r=>{
            console.log(r.data)
            setReplyList(r.data)
        })
    },[replyList])

    const rdelete = (e)=>{
        let rno = e.target.value;
        axios.delete("/reply",{params : {"rno": rno}}).then(r=>{
            console.log(r)
            if(r.data==true){
                alert('댓글이 삭제되었습니다.')
            }
        })
    }

    let replybox =
        <Paper style={{margin : 16}}>
            <List>
                {
                    replyList.map( (i)=>
                        <div>
                            <span> {i.mname} : {i.rcontent} </span>
                            {login.mno==i.mno ?
                            <button onClick={rdelete} value={i.rno}>삭제</button>
                            : true}
                        </div>
                    )

                }
            </List>
        </Paper>

     const addReply = ()=>{
        console.log(document.querySelector('#rcontent').value)
        console.log(bno)
        console.log(login.mno)
        let info = {
            rcontent : document.querySelector('#rcontent').value,
            rindex : 0,
            bno : bno,
            mno : login.mno
        }

        axios.post("/reply", info)
            .then(r=>{
                console.log(r.data)

            })
     }

    return (<>
        <Container>
            <h3>댓글창</h3>
            {replybox}
            <TextField fullWidth className="rcontent" id="rcontent" label="댓글" variant="standard" />
            <Button onClick={addReply} variant="outlined"> 등록 </Button>
        </Container>
    </>)
}