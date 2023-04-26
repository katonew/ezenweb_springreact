import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useSearchParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CategoryList from './CategoryList'

export default function Update( props ) {
    const [searchParams, setSearchParams] = useSearchParams();
    console.log(searchParams)
    console.log(searchParams.get("bno"))

    let [ board , setBoard ] = useState({})
    let [ cno, setCno ] = useState(0)

    useEffect(() => {
      axios.get("/board/getboard", { params: { bno: searchParams.get("bno") }})
        .then((r) => {
          console.log(r.data);
          setBoard(r.data);
          setCno(r.data.cno);
        })
    }, []);

    const onUpdate = ()=>{
        axios.put("/board",{"btitle": board.btitle, "bcontent": board.bcontent , "bno" : searchParams.get("bno") , "cno" : cno})
            .then( r => {
                console.log( r.data );
                if(r.data==true){
                    alert('수정 성공')
                    window.location.href = "/board/view/"+searchParams.get("bno")
                }else{
                    alert('수정 실패')
                }
            })
    }
    // 카테고리 변경 함수
    const categoryChange = (cno)=>{ setCno(cno); }

    // 입력이벤트
    const inputTitle = (e)=>{
        //console.log(e.target.value);
        board.btitle = e.target.value;
        setBoard({...board});
    }
    const inputContent = (e)=>{
        //console.log(e.target.value);
        board.bcontent = e.target.value;
        setBoard({...board});
    }

    return (<>
        <Container>
            <CategoryList categoryChange={categoryChange} />
            <TextField fullWidth onChange={inputTitle} value={board.btitle} className="btitle"     id="btitle"  label="제목" variant="standard" />
            <TextField fullWidth onChange={inputContent} value={board.bcontent} className="bcontent"   id="bcontent" label="내용" multiline rows={10} variant="standard" />
            <Button variant="outlined" onClick={  onUpdate }> 수정 </Button>
            <Button variant="outlined"> 취소 </Button>
        </Container>
    </>)
}