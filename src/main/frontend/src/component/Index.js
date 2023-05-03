import React from 'react'
import {BrowserRouter, Routes , Route} from 'react-router-dom'
import Login from './member/Login'
import Signup from './member/Signup'
import Header from './Header'
import Footer from './Footer'
import Main from './Main'
import Find from "./member/Find"
import List from "./board/List"
import Write from "./board/Write"
import View from "./board/View"
import Chatting from "./board/Chatting"
import Update from "./board/Update"
import DashBoard from "./admin/DashBoard"
import AppTodo from "../example/exbook/AppTodo"


/*
    react-router-dom 다양한 라우터 컴포넌트 제공
    1. BrowserRouter : 가상 URL 관리[ URL 동기화 ]
    2. Routes : 가장 적합한 <Route> 컴포넌트를 검토하여 찾는다
        요청 된 path에 적합한 Route 찾아서 Routes 범위 내 렌더링
    3. Route : 실제 URL 경로 지정해주는 컴포넌트
*/


export default function Index(props){
    return (<>
        <BrowserRouter>
            <Header />
            <Routes>
                <Route path="/" element={<Main /> } />

                <Route path="/member/login" element = { <Login/> } />
                <Route path="/member/signup" element = { <Signup/> } />
                <Route path="/member/find" element = { <Find/> } />

                <Route path="/board/list" element = { <List/> } />
                <Route path="/board/view/:bno" element = { <View/> } />
                <Route path="/board/write" element = { <Write/> } />
                <Route path="/board/update" element = { <Update/> } />

                <Route path="/admin/dashboard" element = { <DashBoard/> } />

                <Route path="/AppTodo" element = { <AppTodo/> } />

                <Route path="/chatting/home" element = { <Chatting/> } />
            </Routes>
            <Footer />
        </BrowserRouter>
    </>)
}