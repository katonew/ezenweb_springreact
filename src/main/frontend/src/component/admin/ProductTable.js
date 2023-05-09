import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid'; // npm i mui/x-data-grid

// 데이터 테이블의 필드 설정
const columns = [
    { field: 'id', headerName: '제품번호', width: 150 },
    { field: 'pname', headerName: '제품명', width: 150 },
    { field: 'pprice', headerName: '가격', type : 'number', width: 100 },
    { field: 'pcategory', headerName: '카테고리', width: 100 },
    { field: 'pcomment',headerName: '제품설명',width: 150},
    { field: 'pmanufacturer', headerName: '제조사', width: 70 },
    { field: 'pstate', headerName: '상태', type : 'number' ,width: 60 },
    { field: 'pstock', headerName: '재고수량', type : 'number' , width: 80 },
    { field: 'udate', headerName: '등록일', width: 120 },
    { field: 'cdate', headerName: '수정일', width: 120 }
];




export default function ProductTable(props) {
    // 1. 상태변수
    const [rows,setRows] = useState([]);
    // 4. 데이터 테이블에서 선택된 제품들의 id 리스트
    const [rowSelectionModel, setRowSelectionModel] = useState([]);
    console.log(rowSelectionModel)

    // 2. 제품호출 axios
    const getProduct = () =>
        { axios.get("/product").then( r=> {
            setRows(r.data);
        })
    }
    // 3.컴포넌트 생명주기에 따른 함수 호출
    useEffect( () => { getProduct();} , [])
    //console.log(rows)

    const onDeleteHandler = ()=>{
        let msg = window.confirm("정말 삭제하시겠습니까?")
        if(msg==true){ // 확인버튼을 클릭했을때
            // 선택된 제품 리스트를 하나씩 서버에게 삭제 요청
            rowSelectionModel.forEach( r =>{
                axios.delete("/product",{params : {id : r}}).then( o =>{
                    getProduct();
                })
            })

        }
    }

    return (<>
        <button
            type="button"
            onClick={onDeleteHandler}
            disabled={rowSelectionModel.length == 0 ? true : false}
        >선택삭제</button>
        <div style={{ height: 400, width: '100%' }}>
            <DataGrid
                rows={rows}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 5 },
                    },
                }}
                pageSizeOptions={[5, 10, 15, 20]}

                checkboxSelection
                onRowSelectionModelChange={(newRowSelectionModel) => {
                    setRowSelectionModel(newRowSelectionModel);
                }}
            />
        </div>
    </>)
}