import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import ProductTable from './ProductTable'
import ProductWrite from './ProductWrite'
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

export default function DashBoard( props ) {

    // 현재 선택한 탭 번호
    const [value, setValue] = React.useState('1');
    //탭 변경
    const handleTabsChange = (event, newValue) => {
        setValue(newValue);
    };

    // 1.카테고리 등록 버튼을 눌렀을때 이벤트
    const setCategory = () => {    console.log('setCategory')
        let cname = document.querySelector(".cname");
        axios.post( '/board/category/write',{ "cname" : cname.value } )
            .then( (r) => { console.log(r)
                if( r.data == true ){ alert('카테고리 등록성공'); cname.value = '' }
            })
    }
    return(<>
        <Container>
            <h3> 관리자 페이지 </h3>
            <Box sx={{ width: '100%', typography: 'body1' }}>
                <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <TabList onChange={handleTabsChange} aria-label="lab API tabs example">
                <Tab label="게시판 카테고리 등록" value="1" />
                <Tab label="제품 등록" value="2" />
                <Tab label="제품 관리" value="3" />
                <Tab label="제품 통계" value="4" />
                </TabList>
                </Box>
                    <TabPanel value="1">
                        <h6> 게시판 카테고리 추가 </h6>
                        <input type="text" className="cname" />
                        <button onClick={ setCategory } type="button"> 카테고리 등록</button>
                    </TabPanel>
                    <TabPanel value="2">
                        <ProductWrite
                            handleTabsChange={handleTabsChange}
                        />
                    </TabPanel>
                    <TabPanel value="3">
                        <h6> 상품테이블 </h6>
                        <ProductTable />
                    </TabPanel>
                    <TabPanel value="4">
                        제품 통계 구역
                    </TabPanel>
                </TabContext>
            </Box>

        </Container>
    </>)
}