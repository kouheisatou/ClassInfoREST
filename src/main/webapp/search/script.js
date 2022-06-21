'use strict'
/*
document.getElementById('form').onsubmit = function() {
    //検索フォームに入力した内容とプルダウンの情報を含んだURLをhtmlのhrefに代入する
    location.href = document.getElementById('form').select.value;
    console.log('クリックされました')
};*/
/*
let classname
let classCode
*/
let department_field
let grade
let unitDivision
let semester
let campas
let urlList=[]

/*
document.getElementById('name').onchange = function(){
    classname=document.getElementById('name').value;
    console.log(classname);
    urlList.push(classname);
    console.log(urlList);
}
document.getElementById('classCode').onchange = function(){
    classCode=document.getElementById('classCode').value;
    console.log(classCode);
    urlList.push(classCode);
    console.log(urlList);
}*/

document.getElementById('department_field').onchange = function(){
    department_field=document.getElementById('department_field').value;
    console.log(department_field);
    urlList.push("department_field="+department_field);
    console.log(urlList);
}

document.getElementById('grade').onchange = function(){
    grade=document.getElementById('grade').value;
    console.log(grade);
    urlList.push("grade="+grade);
    console.log(urlList);
}

document.getElementById('unitDivision').onchange = function(){
    unitDivision=document.getElementById('unitDivision').value;
    console.log(unitDivision);
    urlList.push("uniDivision="+unitDivision);
    console.log(urlList);
}

document.getElementById('semester').onchange = function(){
    semester=document.getElementById('semester').value;
    console.log(semester);
    urlList.push("semester="+semester);
    console.log(urlList);
}

document.getElementById('campas').onchange = function(){
    campas=document.getElementById('campas').value;
    console.log(campas);
    urlList.push("campas="+campas);
    console.log(urlList);
}

function search(){
    //検索フォームに入力した内容とプルダウンの情報を含んだurlListをhtmlのhrefに代入する
    for(let i=0;i<urlList.length;i++){
        if(urlList[i]==""){
            urlList.splice(i);
        }
    }
    let URL='';
    let link="https://tonarinosibahaao.iobb.net:8181/ClassInfoREST/class-info/class/get/?";
    for(let i=0;i<urlList.length;i++){
        URL+=urlList[i];
        if(i==urlList.length-1)break;
        URL+='&';
    }
    link+=URL;
    console.log(URL);
    location.href=link;
    // location.replace(URL);
    console.log('検索がクリックされました')
}

document.getElementById('form').onsubmit = function() {
    

}
