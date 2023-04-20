import React, {useState, useEffect} from 'react';

export default function Hook2(props){

    let [value, setValue] = useState(0);
    console.log('value : ' + value)



    useEffect ( ()=>{
        console.log('[] 없는 useEffect1 실행')
        return ()=>{
            console.log('[] 없는 useEffect1 종료되면서 실행')
        }
    } )
    useEffect ( ()=>{
        console.log('[] 있는 useEffect2 실행')
        return ()=>{
            console.log('[] 있는 useEffect2 종료되면서 실행')
        }
    }, [])
    useEffect ( ()=>{
        console.log('[value] 있는 useEffect3 실행')
        return ()=>{
            console.log('[value] 있는 useEffect3 종료되면서 실행')
        }
    }, [value])

    return (<>
        <p>{value}</p>
        <button onClick={()=>{
            setValue(value+1);
        }}>+</button>
    </>)
}