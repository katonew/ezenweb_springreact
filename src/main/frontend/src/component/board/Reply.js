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
    const PrintReply = (list) => {
      const tree = {};

      list.forEach((o) => {
        if (o.rindex === 0) {
          tree[o.rno] = {
            item: {
              rno: o.rno,
              mno : o.mno,
              mname: o.mname,
              rdate: o.rdate,
              rcontent: o.rcontent,
            },
            children: [],
          };
        } else {
          const parentNode = tree[o.rindex];

          if (parentNode) {
            if (!parentNode.children) {
              parentNode.children = [];
            }

            parentNode.children.push({
              item: {
                rno: o.rno,
                mno : o.mno,
                mname: o.mname,
                rdate: o.rdate,
                rcontent: o.rcontent,
              }
            });
          }
        }
      });

      const printList = [];
      console.log(tree);
      for (const key in tree) {
        const node = tree[key];
        const treeItems = []
        const children = node.children.length ? node.children : null;
        treeItems.push(<>
            <div style={{
                       display: 'flex',
                       justifyContent : 'space-between'
                     }}>
               <TextField
                    fullWidth
                    id={`rereply${node.item.rno}`}
                    label="대댓글"
                    variant="standard"
                  />
              <Button
                style={{width:'10%'}}
                onClick={(e) =>
                  addReReplyHandler(e, node.item.rno)
                }
                variant="outlined"
              >
                댓글달기
              </Button>
          </div>
          </>)
        for (const key in children){
            const child = children[key];
            treeItems.push(
                <TreeItem
                    style={{
                           border: '1px solid black',
                           borderRadius: '4px',
                           padding: '4px',
                           margin: '4px',
                           width: '70%',
                           position: 'relative',
                           left: '250px',
                         }}
                    key={child.item.rno}
                    nodeId={child.item.rno}
                    label={
                        <div>
                            <h5> {child.item.mname} / {child.item.rdate} </h5>
                            <div style={{
                                       display: 'flex',
                                       justifyContent : 'space-between'
                                     }}>
                                <div>{child.item.rcontent}</div>
                                <div style={{marginRight : '10px'}}>
                                    {login.mno == child.item.mno ? (
                                        <Button onClick={(e) => rupdateHandler(e, child.item.rno, child.item.rcontent)} variant="outlined">수정</Button>
                                    ) : (<></>)}
                                    {login.mno == child.item.mno ? (
                                        <Button onClick={(e) => rdeleteHandler(e, child.item.rno)}  variant="outlined" >삭제</Button>
                                    ) : (<></>)}
                                </div>
                            </div>
                        </div>
                    }
                />
            );
        }
        printList.push(
          <TreeItem
            style={{
                   border: '1px solid black',
                   borderRadius: '4px',
                   padding: '4px',
                   margin: '4px',
                 }}
            key={node.item.rno}
            nodeId={node.item.rno}
            label={
              <div>
                <h5> {node.item.mname} / {node.item.rdate} </h5>
                <div>

                    <div style={{
                            display: 'flex',
                            justifyContent : 'space-between'
                          }}>
                      <div>{node.item.rcontent}</div>
                      <div style={{marginRight : '10px'}}>
                          {login.mno == node.item.mno ? (
                            <Button onClick={(e) => rupdateHandler(e, node.item.rno, node.item.rcontent)} variant="outlined">수정</Button>
                          ) : (<></>)}
                          {login.mno == node.item.mno ? (
                            <Button onClick={(e) => rdeleteHandler(e, node.item.rno)}  variant="outlined" >삭제</Button>
                          ) : (<></>)}
                      </div>
                    </div>
                </div>
              </div>
            }
          >
          {treeItems}
          </TreeItem>
        )};
        return [printList];
    }


    let replybox =
        <Paper style={{margin : 16}}>
            <h3>댓글창</h3>
            {<TreeView>
                {PrintReply(props.replyList)}
            </TreeView>}
            <TextField fullWidth className="rcontent" id="rcontent" label="댓글" variant="standard" />
            <Button onClick={addReplyHandler} variant="outlined"> 등록 </Button>
        </Paper>



    return (<>
        <Container>
            {replybox}
        </Container>
    </>)
}